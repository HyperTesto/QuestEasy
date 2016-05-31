package me.hypertesto.questeasy.activities;

import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
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
import me.hypertesto.questeasy.utils.UnknownGuestTypeException;

public class EditCardActivity extends AppCompatActivity {
	private Card card;
	private ListView listView;
	private GroupListAdapter adapter;
	private RelativeLayout itemContainer;
	private ImageView letterImage;
	private Animation flipAnim;
	private Animation flipAnimReverse;
	private TextDrawable textDrawable;
	private int colorSelected;

	private int indexClicked;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_card);
		this.listView = (ListView) findViewById(R.id.lvMembers);
		flipAnim = AnimationUtils.loadAnimation(EditCardActivity.this, R.anim.flip_anim);
		flipAnimReverse = AnimationUtils.loadAnimation(EditCardActivity.this,R.anim.flip_anim);

		flipAnim.setAnimationListener(new Animation.AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {

				textDrawable = (TextDrawable) letterImage.getDrawable();

				if (Build.VERSION.SDK_INT >= 23) {
					colorSelected = ContextCompat.getColor(EditCardActivity.this, R.color.background_bar);
				} else {
					colorSelected = getResources().getColor(R.color.background_bar);
				}

				DrawableCompat.setTint(textDrawable, colorSelected);
				letterImage.setImageDrawable(textDrawable);

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}
		});

		flipAnimReverse.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {

				textDrawable = (TextDrawable) letterImage.getDrawable();
				DrawableCompat.setTint(textDrawable,textDrawable.getPaint().getColor());
				letterImage.setImageDrawable(textDrawable);

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}
		});

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

		if ((card instanceof  FamilyCard) || (card instanceof GroupCard)){
			fab.setImageResource(R.drawable.ic_person_add);
		}

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

		this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Object o = parent.getItemAtPosition(position);
				indexClicked = position;
				Intent editGuestIntent = new Intent(EditCardActivity.this, FormGuestActivity.class);

				if (o instanceof SingleGuest) {
					SingleGuest sg = (SingleGuest) o;
					editGuestIntent.putExtra(StaticGlobals.intentExtras.GUEST_TO_EDIT, sg);
					editGuestIntent.putExtra(StaticGlobals.intentExtras.GUEST_TYPE, Guest.type.SINGLE_GUEST);
					editGuestIntent.putExtra(StaticGlobals.intentExtras.PERMANENZA, card.getPermanenza());
					startActivityForResult(editGuestIntent, StaticGlobals.requestCodes.EDIT_SINGLE_GUEST);

				} else if (o instanceof FamilyHeadGuest) {
					FamilyHeadGuest fhg = (FamilyHeadGuest) o;
					editGuestIntent.putExtra(StaticGlobals.intentExtras.GUEST_TO_EDIT, fhg);
					editGuestIntent.putExtra(StaticGlobals.intentExtras.GUEST_TYPE, Guest.type.FAMILY_HEAD);
					editGuestIntent.putExtra(StaticGlobals.intentExtras.PERMANENZA, card.getPermanenza());
					startActivityForResult(editGuestIntent, StaticGlobals.requestCodes.EDIT_FAMILY_HEAD);

				} else if (o instanceof FamilyMemberGuest) {
					FamilyMemberGuest fmg = (FamilyMemberGuest) o;
					editGuestIntent.putExtra(StaticGlobals.intentExtras.GUEST_TO_EDIT, fmg);
					editGuestIntent.putExtra(StaticGlobals.intentExtras.GUEST_TYPE, Guest.type.FAMILY_MEMBER);
					editGuestIntent.putExtra(StaticGlobals.intentExtras.PERMANENZA, card.getPermanenza());
					startActivityForResult(editGuestIntent, StaticGlobals.requestCodes.EDIT_FAMILY_MEMBER);

				} else if (o instanceof GroupHeadGuest) {
					GroupHeadGuest ghg = (GroupHeadGuest) o;
					editGuestIntent.putExtra(StaticGlobals.intentExtras.GUEST_TO_EDIT, ghg);
					editGuestIntent.putExtra(StaticGlobals.intentExtras.GUEST_TYPE, Guest.type.GROUP_HEAD);
					editGuestIntent.putExtra(StaticGlobals.intentExtras.PERMANENZA, card.getPermanenza());
					startActivityForResult(editGuestIntent, StaticGlobals.requestCodes.EDIT_GROUP_HEAD);

				} else if (o instanceof GroupMemberGuest) {
					GroupMemberGuest gmg = (GroupMemberGuest) o;
					editGuestIntent.putExtra(StaticGlobals.intentExtras.GUEST_TO_EDIT, gmg);
					editGuestIntent.putExtra(StaticGlobals.intentExtras.GUEST_TYPE, Guest.type.GROUP_MEMBER);
					editGuestIntent.putExtra(StaticGlobals.intentExtras.PERMANENZA, card.getPermanenza());
					startActivityForResult(editGuestIntent, StaticGlobals.requestCodes.EDIT_GROUP_MEMBER);

				} else {
					throw new UnknownGuestTypeException();
				}

			}
		});


		//This is setted to enable multi selection on items
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

		//Methods to manage item's selection
		listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
			@Override
			public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
				final int checkedCount = listView.getCheckedItemCount();

				itemContainer = (RelativeLayout) getViewByPosition(position, listView);
				letterImage = (ImageView) itemContainer.findViewById(R.id.guestTypeImg);
				if (checked) {
					letterImage.startAnimation(flipAnim);
				} else {
					letterImage.startAnimation(flipAnimReverse);
				}
				mode.setTitle(checkedCount + " Selezionati");
				adapter.toggleSelection(position);

			}

			@Override
			public boolean onCreateActionMode(ActionMode mode, Menu menu) {
				mode.getMenuInflater().inflate(R.menu.delete_item_ba2v2, menu);
				return true;
			}

			@Override
			public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
				return false;
			}

			@Override
			public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
				switch (item.getItemId()) {
					case R.id.delete:
						SparseBooleanArray selected = adapter.getSelectedIds();
						Log.e("selected", String.valueOf(selected));
						for (int i = (selected.size() - 1); i >= 0; i--) {
							if (selected.valueAt(i)) {
								Guest selectedItem = adapter.getItem(selected.keyAt(i));

								if (card instanceof FamilyCard){
									if (selectedItem instanceof FamilyMemberGuest){
										FamilyCard fc = (FamilyCard) card;
										fc.getFamiliari().remove(selectedItem);
										adapter.remove(selectedItem);
									} else {
										Toast.makeText(getApplicationContext(), "Cannot delete Family Head Guest", Toast.LENGTH_LONG).show();
									}

								} else if (card instanceof GroupCard){
									if (selectedItem instanceof GroupMemberGuest){
										GroupCard gc = (GroupCard) card;
										gc.getAltri().remove(selectedItem);
										adapter.remove(selectedItem);
									} else {
										Toast.makeText(getApplicationContext(), "Cannot delete Group Head Guest", Toast.LENGTH_LONG).show();
									}
								} else {
									Toast.makeText(getApplicationContext(), "Cannot delete Single Guest", Toast.LENGTH_LONG).show();
								}
							}
						}
						mode.finish();
						return true;
					default:
						return false;
				}
			}

			@Override
			public void onDestroyActionMode(ActionMode mode) {
				adapter.removeSelection();
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode){
			case StaticGlobals.requestCodes.NEW_SINGLE_GUEST:
			case StaticGlobals.requestCodes.EDIT_SINGLE_GUEST:
				if (resultCode == StaticGlobals.resultCodes.GUEST_FORM_SUCCESS){
					Serializable s = data.getSerializableExtra(StaticGlobals.intentExtras.FORM_OUTPUT_GUEST);

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
				((FamilyCard) card).setFamiliari(new ArrayList<FamilyMemberGuest>());
			case StaticGlobals.requestCodes.EDIT_FAMILY_HEAD:
				if (resultCode == StaticGlobals.resultCodes.GUEST_FORM_SUCCESS){
					Serializable s = data.getSerializableExtra(StaticGlobals.intentExtras.FORM_OUTPUT_GUEST);

					if (s instanceof FamilyHeadGuest){
						FamilyHeadGuest fhg = (FamilyHeadGuest) s;
						((FamilyCard) card).setCapoFamiglia(fhg);
						card.setPermanenza(data.getIntExtra(StaticGlobals.intentExtras.PERMANENZA,100));

						System.out.println(((FamilyCard) card).getCapoFamiglia().getName());
					}
				}

				this.updateListView();
				break;

			case StaticGlobals.requestCodes.NEW_FAMILY_MEMBER:
				if (resultCode == StaticGlobals.resultCodes.GUEST_FORM_SUCCESS){
					Serializable s = data.getSerializableExtra(StaticGlobals.intentExtras.FORM_OUTPUT_GUEST);

					if (s instanceof FamilyMemberGuest){
						FamilyMemberGuest fmg = (FamilyMemberGuest) s;
						((FamilyCard) card).addFamilyMember(fmg);
					}
				}

				this.updateListView();
				break;

			case StaticGlobals.requestCodes.NEW_GROUP_HEAD:
				((GroupCard) card).setAltri(new ArrayList<GroupMemberGuest>());
			case StaticGlobals.requestCodes.EDIT_GROUP_HEAD:
				if (resultCode == StaticGlobals.resultCodes.GUEST_FORM_SUCCESS){
					Serializable s = data.getSerializableExtra(StaticGlobals.intentExtras.FORM_OUTPUT_GUEST);

					if (s instanceof GroupHeadGuest){
						GroupHeadGuest ghg = (GroupHeadGuest) s;
						((GroupCard) card).setCapoGruppo(ghg);
						card.setPermanenza(data.getIntExtra(StaticGlobals.intentExtras.PERMANENZA,100));

						System.out.println(((GroupCard) card).getCapoGruppo().getName());
					}
				}

				this.updateListView();
				break;

			case StaticGlobals.requestCodes.NEW_GROUP_MEMBER:
				if (resultCode == StaticGlobals.resultCodes.GUEST_FORM_SUCCESS){
					Serializable s = data.getSerializableExtra(StaticGlobals.intentExtras.FORM_OUTPUT_GUEST);

					if (s instanceof GroupMemberGuest){
						GroupMemberGuest gmg = (GroupMemberGuest) s;
						((GroupCard) card).addGroupMember(gmg);
					}
				}

				this.updateListView();
				break;

			case StaticGlobals.requestCodes.EDIT_FAMILY_MEMBER:
				if (resultCode == StaticGlobals.resultCodes.GUEST_FORM_SUCCESS){
					Serializable s = data.getSerializableExtra(StaticGlobals.intentExtras.FORM_OUTPUT_GUEST);

					if (s instanceof FamilyMemberGuest){
						FamilyMemberGuest fmg = (FamilyMemberGuest) s;
						ArrayList<FamilyMemberGuest> fmgs = ((FamilyCard) card).getFamiliari();
						((FamilyCard) card).setFamiliari(new ArrayList<FamilyMemberGuest>());
						fmgs.remove(indexClicked - 1);
						fmgs.add(fmg);

						for (FamilyMemberGuest g : fmgs){
							((FamilyCard) card).addFamilyMember(g);
						}
					}
				}

				this.updateListView();
				break;

			case StaticGlobals.requestCodes.EDIT_GROUP_MEMBER:
				if (resultCode == StaticGlobals.resultCodes.GUEST_FORM_SUCCESS){
					Serializable s = data.getSerializableExtra(StaticGlobals.intentExtras.FORM_OUTPUT_GUEST);

					if (s instanceof GroupMemberGuest){
						GroupMemberGuest gmg = (GroupMemberGuest) s;
						ArrayList<GroupMemberGuest> gmgs = ((GroupCard) card).getAltri();
						((GroupCard) card).setAltri(new ArrayList<GroupMemberGuest>());
						gmgs.remove(indexClicked - 1);
						gmgs.add(gmg);

						for (GroupMemberGuest g : gmgs){
							((GroupCard) card).addGroupMember(g);
						}
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

	/*
		This method return the correct selected view in the given listview
	 */
	public View getViewByPosition(int position, ListView listView) {
		final int firstListItemPosition = listView.getFirstVisiblePosition();
		final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

		if (position < firstListItemPosition || position > lastListItemPosition ) {
			return listView.getAdapter().getView(position, listView.getChildAt(position), listView);
		} else {
			final int childIndex = position - firstListItemPosition;
			return listView.getChildAt(childIndex);
		}
	}


	public void saveCard(){
		// Write your code here
		Intent resultIntent = new Intent();
		resultIntent.putExtra(StaticGlobals.intentExtras.CARD, card);
		setResult(StaticGlobals.resultCodes.EDIT_CARD_SUCCESS, resultIntent);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		int id = item.getItemId();
		if (id == android.R.id.home){
			saveCard();
			finish();
			return true;
		}
		else{
			return super.onOptionsItemSelected(item);
		}
	}


	@Override
	public void onBackPressed() {
		saveCard();
		super.onBackPressed();
	}

}
