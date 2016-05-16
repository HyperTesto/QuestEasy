package me.hypertesto.questeasy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
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
import me.hypertesto.questeasy.model.SingleGuestCard;
import me.hypertesto.questeasy.model.adapters.CardListAdapter;
import me.hypertesto.questeasy.utils.FabAnimation;
import me.hypertesto.questeasy.utils.ListScrollListener;

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
		if (intent.hasExtra("DEC")){
			ArrayList d = (ArrayList) intent.getSerializableExtra("DEC");
			items.addAll(d);
		} else {
			SingleGuest s = new SingleGuest();
			s.setName("Tizio");
			FamilyHeadGuest f = new FamilyHeadGuest();
			f.setName("Caio");
			GroupHeadGuest g = new GroupHeadGuest();
			g.setName("Semprogno");
			items.add(new SingleGuestCard(s, new Date(), 5, true));
			items.add(new FamilyCard(f, new ArrayList<FamilyMemberGuest>(), new Date(), 12, true));
			items.add(new GroupCard(g, new ArrayList<GroupMemberGuest>(), new Date(), 7, true));
			items.add(new SingleGuestCard(s, new Date(), 5, false));
			items.add(new FamilyCard(f, new ArrayList<FamilyMemberGuest>(), new Date(), 12, false));
			items.add(new GroupCard(g, new ArrayList<GroupMemberGuest>(), new Date(), 7, false));
			items.add(new SingleGuestCard(s, new Date(), 5, true));
			items.add(new FamilyCard(f, new ArrayList<FamilyMemberGuest>(), new Date(), 12, true));
			items.add(new GroupCard(g, new ArrayList<GroupMemberGuest>(), new Date(), 7, true));

			System.out.println("Added stub guests");

		}

		CardListAdapter adapter = new CardListAdapter(this,R.layout.card_list_item,items);
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
		sendFormRequest(groupFab,1);
		sendFormRequest(familyFab,2);
	}

	public void sendFormRequest (FloatingActionButton formFab, Integer typeGuest){
		final Intent intentForm = new Intent(EditDecActivity.this, FormGuestActivity.class);
		switch(typeGuest){
			case 0 :
				intentForm.putExtra("me.hypertesto.questeasy.activities.category", "Singolo");
				break;
			case 1 :
				intentForm.putExtra("me.hypertesto.questeasy.activities.category", "Capogruppo");
				break;
			case 2 :
				intentForm.putExtra("me.hypertesto.questeasy.activities.category", "Capofamiglia");
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
