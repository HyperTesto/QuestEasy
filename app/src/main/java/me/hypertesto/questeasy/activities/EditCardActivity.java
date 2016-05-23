package me.hypertesto.questeasy.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.github.clans.fab.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;

import me.hypertesto.questeasy.R;
import me.hypertesto.questeasy.model.Card;
import me.hypertesto.questeasy.model.FamilyCard;
import me.hypertesto.questeasy.model.FamilyHeadGuest;
import me.hypertesto.questeasy.model.FamilyMemberGuest;
import me.hypertesto.questeasy.model.GroupCard;
import me.hypertesto.questeasy.model.GroupHeadGuest;
import me.hypertesto.questeasy.model.GroupMemberGuest;
import me.hypertesto.questeasy.model.Guest;
import me.hypertesto.questeasy.model.SingleGuest;
import me.hypertesto.questeasy.model.SingleGuestCard;
import me.hypertesto.questeasy.model.adapters.GroupListAdapter;
import me.hypertesto.questeasy.utils.StaticGlobals;

public class EditCardActivity extends AppCompatActivity {
	private Card card;
	private ListView listView;
	private GroupListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_card);
		this.listView = (ListView) findViewById(R.id.lvMembers);

		Intent intent = getIntent();

		Serializable tmp = intent.getSerializableExtra(StaticGlobals.intentExtras.CARD);

		final Intent intentToForm = new Intent(EditCardActivity.this, FormGuestActivity.class);

		if (tmp instanceof SingleGuestCard){
			SingleGuestCard sgCard = (SingleGuestCard) tmp;
			card = sgCard;
			//TODO: maybe hide add fab?
			if (sgCard.getGuest() == null){
				//START FORM
				intentToForm.putExtra(StaticGlobals.intentExtras.GUEST_TYPE, Guest.type.SINGLE_GUEST);
				intentToForm.putExtra(StaticGlobals.intentExtras.GUEST_TO_EDIT, (Serializable) null);
				startActivityForResult(intentToForm, StaticGlobals.requestCodes.NEW_SINGLE_GUEST);
			} else {
				this.updateListView();
			}

		} else if (tmp instanceof FamilyCard){
			FamilyCard fCard = (FamilyCard) tmp;
			card = fCard;

			if (fCard.getCapoFamiglia() == null){
				//Start form
				intentToForm.putExtra(StaticGlobals.intentExtras.GUEST_TYPE, Guest.type.FAMILY_HEAD);
				intentToForm.putExtra(StaticGlobals.intentExtras.GUEST_TO_EDIT, (Serializable) null);
				startActivityForResult(intentToForm, StaticGlobals.requestCodes.NEW_FAMILY_HEAD);
			} else {
				this.updateListView();
			}

		} else if (tmp instanceof GroupCard){
			GroupCard gCard = (GroupCard) tmp;
			card = gCard;

			if (gCard.getCapoGruppo() == null){
				intentToForm.putExtra(StaticGlobals.intentExtras.GUEST_TYPE, Guest.type.GROUP_HEAD);
				intentToForm.putExtra(StaticGlobals.intentExtras.GUEST_TO_EDIT, (Serializable) null);
				startActivityForResult(intentToForm, StaticGlobals.requestCodes.NEW_GROUP_HEAD);
			} else {
				this.updateListView();
			}

		} else {
			throw new RuntimeException("cacca");
		}

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_edit_card);

		if (fab != null) {
			fab.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {

					if (card instanceof SingleGuestCard){
						//Fuffa
					} else if (card instanceof FamilyCard) {
						intentToForm.putExtra(StaticGlobals.intentExtras.GUEST_TYPE, Guest.type.FAMILY_MEMBER);
						intentToForm.putExtra(StaticGlobals.intentExtras.GUEST_TO_EDIT, (Serializable) null);
						startActivityForResult(intentToForm, StaticGlobals.requestCodes.NEW_FAMILY_MEMBER);
					} else if (card instanceof GroupCard){
						intentToForm.putExtra(StaticGlobals.intentExtras.GUEST_TYPE, Guest.type.GROUP_MEMBER);
						intentToForm.putExtra(StaticGlobals.intentExtras.GUEST_TO_EDIT, (Serializable) null);
						startActivityForResult(intentToForm, StaticGlobals.requestCodes.NEW_GROUP_MEMBER);
					}
				}
			});
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode){
			case StaticGlobals.requestCodes.NEW_SINGLE_GUEST:
				if (resultCode == StaticGlobals.resultCodes.GUEST_FORM_SUCCESS){
					Serializable s = data.getSerializableExtra(StaticGlobals.intentExtras.CREATED_GUEST);

					if (s instanceof SingleGuest){
						SingleGuest sg = (SingleGuest) s;
						((SingleGuestCard) card).setGuest(sg);
						card.setPermanenza(data.getIntExtra(StaticGlobals.intentExtras.PERMANENZA,100));

						System.out.println(((SingleGuestCard) card).getGuest().getName());
					}
				}

				this.updateListView();
				break;

			case StaticGlobals.requestCodes.NEW_FAMILY_HEAD:
				if (resultCode == StaticGlobals.resultCodes.GUEST_FORM_SUCCESS){
					Serializable s = data.getSerializableExtra(StaticGlobals.intentExtras.CREATED_GUEST);

					if (s instanceof FamilyHeadGuest){
						FamilyHeadGuest fhg = (FamilyHeadGuest) s;
						((FamilyCard) card).setCapoFamiglia(fhg);
						((FamilyCard) card).setFamiliari(new ArrayList<FamilyMemberGuest>());
						card.setPermanenza(data.getIntExtra(StaticGlobals.intentExtras.PERMANENZA,100));

						System.out.println(((FamilyCard) card).getCapoFamiglia().getName());
					}
				}

				this.updateListView();
				break;

			case StaticGlobals.requestCodes.NEW_FAMILY_MEMBER:
				if (resultCode == StaticGlobals.resultCodes.GUEST_FORM_SUCCESS){
					Serializable s = data.getSerializableExtra(StaticGlobals.intentExtras.CREATED_GUEST);

					if (s instanceof FamilyMemberGuest){
						FamilyMemberGuest fmg = (FamilyMemberGuest) s;
						((FamilyCard) card).addFamilyMember(fmg);
					}
				}

				this.updateListView();
				break;

			case StaticGlobals.requestCodes.NEW_GROUP_HEAD:
				if (resultCode == StaticGlobals.resultCodes.GUEST_FORM_SUCCESS){
					Serializable s = data.getSerializableExtra(StaticGlobals.intentExtras.CREATED_GUEST);

					if (s instanceof GroupHeadGuest){
						GroupHeadGuest ghg = (GroupHeadGuest) s;
						((GroupCard) card).setCapoGruppo(ghg);
						((GroupCard) card).setAltri(new ArrayList<GroupMemberGuest>());
						card.setPermanenza(data.getIntExtra(StaticGlobals.intentExtras.PERMANENZA,100));

						System.out.println(((GroupCard) card).getCapoGruppo().getName());
					}
				}

				this.updateListView();
				break;

			case StaticGlobals.requestCodes.NEW_GROUP_MEMBER:
				if (resultCode == StaticGlobals.resultCodes.GUEST_FORM_SUCCESS){
					Serializable s = data.getSerializableExtra(StaticGlobals.intentExtras.CREATED_GUEST);

					if (s instanceof GroupMemberGuest){
						GroupMemberGuest gmg = (GroupMemberGuest) s;
						((GroupCard) card).addGroupMember(gmg);
					}
				}

				this.updateListView();
				break;

			default:
				break;
		}
	}

	private void updateListView(){
		ArrayList<Guest> guests = new ArrayList<>();

		if (this.card instanceof SingleGuestCard){
			SingleGuestCard sgc = (SingleGuestCard) this.card;
			//TODO forse fare item dedicato?
			guests.add(sgc.getGuest());

		} else if (this.card instanceof FamilyCard){
			FamilyCard fc = (FamilyCard) this.card;
			guests.add(fc.getCapoFamiglia());
			guests.addAll(fc.getFamiliari());

		} else if (this.card instanceof GroupCard){
			GroupCard gc = (GroupCard) this.card;
			guests.add(gc.getCapoGruppo());
			guests.addAll(gc.getAltri());

		} else {
			throw new RuntimeException("WTF");
		}

		adapter = new GroupListAdapter(this, R.layout.guest_list_item, guests);
		listView.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();

		inflater.inflate(R.menu.edit_card_bar, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
			case R.id.btnSaveGuestsCard:
				Intent resultIntent = new Intent();
				resultIntent.putExtra(StaticGlobals.intentExtras.CARD, card);
				setResult(StaticGlobals.resultCodes.EDIT_CARD_SUCCESS, resultIntent);
				finish();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}
