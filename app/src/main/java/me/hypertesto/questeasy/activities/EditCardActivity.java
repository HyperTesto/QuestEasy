package me.hypertesto.questeasy.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

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

		Intent intentToForm = new Intent(EditCardActivity.this, FormGuestActivity.class);

		if (tmp instanceof SingleGuestCard){
			SingleGuestCard sgCard = (SingleGuestCard) tmp;
			card = sgCard;
			//TODO: maybe hide add fab?
			if (sgCard.getGuest() == null){
				//START FORM
				intentToForm.putExtra(StaticGlobals.intentExtras.GUEST_TYPE, Guest.type.SINGLE_GUEST);
				startActivityForResult(intentToForm, StaticGlobals.requestCodes.NEW_SINGLE_GUEST);
			}

		} else if (tmp instanceof FamilyCard){
			FamilyCard fCard = (FamilyCard) tmp;
			card = fCard;

			if (fCard.getCapoFamiglia() == null){
				//Start form
				intentToForm.putExtra(StaticGlobals.intentExtras.GUEST_TYPE, Guest.type.FAMILY_HEAD);
				startActivityForResult(intentToForm, StaticGlobals.requestCodes.NEW_FAMILY_HEAD);
			}

		} else if (tmp instanceof GroupCard){
			GroupCard gCard = (GroupCard) tmp;
			card = gCard;

			if (gCard.getCapoGruppo() == null){
				intentToForm.putExtra(StaticGlobals.intentExtras.GUEST_TYPE, Guest.type.GROUP_HEAD);
				startActivityForResult(intentToForm, StaticGlobals.requestCodes.NEW_GROUP_HEAD);
			}

		} else {
			throw new RuntimeException("cacca");
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode){
			case StaticGlobals.requestCodes.NEW_SINGLE_GUEST:
				if (resultCode == StaticGlobals.resultCodes.NEW_GUEST_SUCCESS){
					Serializable s = data.getSerializableExtra(StaticGlobals.intentExtras.CREATED_GUEST);

					if (s instanceof SingleGuest){
						SingleGuest sg = (SingleGuest) s;
						((SingleGuestCard) card).setGuest(sg);

						System.out.println(((SingleGuestCard) card).getGuest().getName());
					}
				}

				this.updateListView();
				break;

			case StaticGlobals.requestCodes.NEW_FAMILY_HEAD:
				if (resultCode == StaticGlobals.resultCodes.NEW_GUEST_SUCCESS){
					Serializable s = data.getSerializableExtra(StaticGlobals.intentExtras.CREATED_GUEST);

					if (s instanceof FamilyHeadGuest){
						FamilyHeadGuest fhg = (FamilyHeadGuest) s;
						((FamilyCard) card).setCapoFamiglia(fhg);
						((FamilyCard) card).setFamiliari(new ArrayList<FamilyMemberGuest>());

						System.out.println(((FamilyCard) card).getCapoFamiglia().getName());
					}
				}

				this.updateListView();
				break;

			case StaticGlobals.requestCodes.NEW_FAMILY_MEMBER:
				if (resultCode == StaticGlobals.resultCodes.NEW_GUEST_SUCCESS){
					Serializable s = data.getSerializableExtra(StaticGlobals.intentExtras.CREATED_GUEST);

					if (s instanceof FamilyMemberGuest){
						FamilyMemberGuest fmg = (FamilyMemberGuest) s;
						((FamilyCard) card).addFamilyMember(fmg);
					}
				}
				break;

			case StaticGlobals.requestCodes.NEW_GROUP_HEAD:
				if (resultCode == StaticGlobals.resultCodes.NEW_GUEST_SUCCESS){
					Serializable s = data.getSerializableExtra(StaticGlobals.intentExtras.CREATED_GUEST);

					if (s instanceof GroupHeadGuest){
						GroupHeadGuest ghg = (GroupHeadGuest) s;
						((GroupCard) card).setCapoGruppo(ghg);
						((GroupCard) card).setAltri(new ArrayList<GroupMemberGuest>());

						System.out.println(((GroupCard) card).getCapoGruppo().getName());
					}
				}

				this.updateListView();
				break;

			case StaticGlobals.requestCodes.NEW_GROUP_MEMBER:
				if (resultCode == StaticGlobals.resultCodes.NEW_GUEST_SUCCESS){
					Serializable s = data.getSerializableExtra(StaticGlobals.intentExtras.CREATED_GUEST);

					if (s instanceof GroupMemberGuest){
						GroupMemberGuest gmg = (GroupMemberGuest) s;
						((GroupCard) card).addGroupMember(gmg);
					}
				}
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
}
