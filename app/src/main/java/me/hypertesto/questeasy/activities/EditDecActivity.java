package me.hypertesto.questeasy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.Date;

import me.hypertesto.questeasy.R;
import me.hypertesto.questeasy.model.Card;
import me.hypertesto.questeasy.model.FamilyCard;
import me.hypertesto.questeasy.model.FamilyHeadGuest;
import me.hypertesto.questeasy.model.FamilyMemberGuest;
import me.hypertesto.questeasy.model.GroupCard;
import me.hypertesto.questeasy.model.GroupHeadGuest;
import me.hypertesto.questeasy.model.GroupMemberGuest;
import me.hypertesto.questeasy.model.SingleGuest;
import me.hypertesto.questeasy.model.adapters.CardListAdapter;
import me.hypertesto.questeasy.model.SingleGuestCard;
import me.hypertesto.questeasy.utils.FabAnimation;
import me.hypertesto.questeasy.utils.ListScrollListener;
import me.hypertesto.questeasy.utils.StaticGlobals;

public class EditDecActivity extends AppCompatActivity {

	private FloatingActionMenu gotoSelectCategory;
	private ListView listView;
	private FrameLayout frameLayout;
	private FloatingActionMenu fabMenu;
	private boolean stateMenu;
	private FloatingActionButton singlefab;
	private FloatingActionButton groupFab;
	private FloatingActionButton familyFab;
	private int mPreviousVisibleItem;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_dec);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		defineSettings();
	}

	/*@Override
	public void onConfigurationChanged(Configuration config) {
		super.onConfigurationChanged(config);

		// Checks the orientation
		if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			setContentView(R.layout.activity_edit_dec_o);

		} else if (config.orientation == Configuration.ORIENTATION_PORTRAIT) {
			setContentView(R.layout.activity_edit_dec);
		}
		defineSettings();
		/*if (stateMenu) {
			fabMenu.expand();
			frameLayout.getBackground().setAlpha(170);
		}


	}
	*/



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();

		inflater.inflate(R.menu.edit_dec_bar, menu);
		return super.onCreateOptionsMenu(menu);
	}

	private void defineSettings() {
		frameLayout = (FrameLayout) findViewById(R.id.frameButtonCategory);
		fabMenu = (FloatingActionMenu) findViewById
				(R.id.categoryGuestGo);
		singlefab = (FloatingActionButton) findViewById(R.id.categoryGuestSingleGo);
		groupFab = (FloatingActionButton) findViewById(R.id.categoryGuestGroupGo);
		familyFab = (FloatingActionButton) findViewById(R.id.categoryGuestFamilyGo);

		ArrayList<Card> items = new ArrayList<>();

		Intent intent = getIntent();
		if (intent.hasExtra(StaticGlobals.intentExtras.DECLARATION)){
			ArrayList d = (ArrayList) intent.getSerializableExtra(StaticGlobals.intentExtras.DECLARATION);
			items.addAll(d);
		} else {
			SingleGuest g = new SingleGuest();
			g.setName("tizio");
			items.add(new SingleGuestCard(g, new Date(), 5));

			System.out.println("Added stub guests");
		}

		final CardListAdapter adapter = new CardListAdapter(this,R.layout.card_list_item,items);
		listView = (ListView)findViewById(R.id.cardlistView);
		listView.setAdapter(adapter);

		fabMenu.hideMenuButton(false);

		new FabAnimation(fabMenu, getApplicationContext());


		listView.setOnScrollListener(new ListScrollListener(fabMenu));

		try{
			frameLayout.getBackground().setAlpha(0);

		} catch (NullPointerException e) {
			Log.e("ERROR", "error null field");
		}

		/*Intent intentForm = new Intent();
		singlefab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (fabMenu.isOpened()){
					fabMenu.close(false);
				}
				startActivity(new Intent(EditDecActivity.this, FormGuestActivity.class));
			}

		});
		*/
		sendFormRequest(singlefab,0);
		sendFormRequest(groupFab, 1);
		sendFormRequest(familyFab, 2);

		final Intent intentToEditCard = new Intent(EditDecActivity.this, EditCardActivity.class);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Object o = parent.getItemAtPosition(position);

				if (o instanceof SingleGuestCard) {
					SingleGuestCard sgc = (SingleGuestCard) o;
					intentToEditCard.putExtra(StaticGlobals.intentExtras.CARD, sgc);
				} else if (o instanceof FamilyCard) {
					FamilyCard fc = (FamilyCard) o;
					intentToEditCard.putExtra(StaticGlobals.intentExtras.CARD, fc);
				} else if (o instanceof GroupCard) {
					GroupCard gc = (GroupCard) o;
					intentToEditCard.putExtra(StaticGlobals.intentExtras.CARD, gc);
				} else {
					throw new RuntimeException("Dafuq??");
				}

				startActivity(intentToEditCard);
			}
		});

		//This is setted to enable multi selection on items
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

		//Methods to manage item's selection
		listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
			@Override
			public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
				final int checkedCount = listView.getCheckedItemCount();
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
								Card selectedItem = adapter.getItem(selected.keyAt(i));
								adapter.remove(selectedItem);
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

	public void sendFormRequest (FloatingActionButton formFab, Integer typeGuest){
		final Intent intentForm = new Intent(EditDecActivity.this, EditCardActivity.class);
		switch(typeGuest){
			case 0 :
				intentForm.putExtra(StaticGlobals.intentExtras.CARD, new SingleGuestCard());
				break;
			case 1 :
				intentForm.putExtra(StaticGlobals.intentExtras.CARD, new GroupCard());
				break;
			case 2 :
				intentForm.putExtra(StaticGlobals.intentExtras.CARD, new FamilyCard());
				break;
			default:
				break;
		}

		formFab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (fabMenu.isOpened()){
					fabMenu.close(false);
				}
				startActivity(intentForm);

			}
		});

	}


}
