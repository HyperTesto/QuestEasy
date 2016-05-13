package me.hypertesto.questeasy.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import android.support.v7.app.ActionBar;
import android.os.Handler;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
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

public class EditDecActivity extends AppCompatActivity {

	private FloatingActionMenu gotoSelectCategory;
	private ListView listView;
	private FrameLayout frameLayout;
	private FloatingActionMenu fabMenu;
	private boolean stateMenu;
	private FloatingActionButton guestForm;
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

		inflater.inflate(R.menu.search_bar, menu);
		return super.onCreateOptionsMenu(menu);
	}

	private void defineSettings() {
		frameLayout = (FrameLayout) findViewById(R.id.frameButtonCategory);
		fabMenu = (FloatingActionMenu) findViewById
				(R.id.categoryGuestGo);
		guestForm = (FloatingActionButton) findViewById(R.id.categoryGuestSingleGo);

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
		/*new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				fabMenu.showMenuButton(true);
				fabMenu.setMenuButtonShowAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.show_from_bottom));
				fabMenu.setMenuButtonHideAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.hide_to_bottom));
			}
		}, 300);*/
		new FabAnimation(fabMenu, getApplicationContext());


		listView.setOnScrollListener(new AbsListView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				if (firstVisibleItem > mPreviousVisibleItem) {
					fabMenu.hideMenuButton(true);
				} else if (firstVisibleItem < mPreviousVisibleItem) {
					fabMenu.showMenuButton(true);
				}
				mPreviousVisibleItem = firstVisibleItem;
			}
		});

		try{
			frameLayout.getBackground().setAlpha(0);

			/*fabMenu.on(new FloatingActionMenu.
					OnFloatingActionMenuUpdateListener() {
				@Override
				public void onMenuExpanded() {
					stateMenu = fabMenu.isExpanded();
					frameLayout.getBackground().setAlpha(170);
					frameLayout.setOnTouchListener(new View.OnTouchListener() {
						@Override
						public boolean onTouch(View v, MotionEvent event) {
							fabMenu.collapse();
							stateMenu = fabMenu.isExpanded();
							return true;
						}
					});
				}

				@Override
				public void onMenuCollapsed() {
					frameLayout.getBackground().setAlpha(0);
					stateMenu = fabMenu.isExpanded();
					frameLayout.setOnTouchListener(null);
				}
			});*/
		} catch (NullPointerException e) {
			Log.e("ERROR", "error null field");
		}

		guestForm.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (fabMenu.isOpened()){
					fabMenu.close(false);
				}

				startActivity(new Intent(EditDecActivity.this, FormGuestActivity.class));
			}
		});
	}


}
