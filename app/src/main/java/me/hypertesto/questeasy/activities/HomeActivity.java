package me.hypertesto.questeasy.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import me.hypertesto.questeasy.R;
import me.hypertesto.questeasy.model.Declaration;
import me.hypertesto.questeasy.model.adapters.DeclarationListAdapter;
import me.hypertesto.questeasy.model.dao.fs.FSDeclarationDao;
import me.hypertesto.questeasy.utils.DateUtils;
import me.hypertesto.questeasy.utils.FabAnimation;
import me.hypertesto.questeasy.utils.ListScrollListener;
import me.hypertesto.questeasy.utils.StaticGlobals;

public class HomeActivity extends AppCompatActivity {

	private ListView lv;
	private FloatingActionButton insertNewDcard;
	private DeclarationListAdapter adapter;
	private NavigationView mNavigationView;
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);


		mNavigationView = (NavigationView)findViewById(R.id.nav_view_main);
		mDrawerLayout = (DrawerLayout)findViewById(R.id.nav_drawer_layout);


		setupDrawer();
		mNavigationView.setNavigationItemSelectedListener(
				new NavigationView.OnNavigationItemSelectedListener() {
					@Override
					public boolean onNavigationItemSelected(MenuItem menuItem) {

						if (menuItem.isChecked())
							menuItem.setChecked(false);
						else
							menuItem.setChecked(true);

						//menuItem.setChecked(true);
						mDrawerLayout.closeDrawers();

						switch (menuItem.getItemId()) {
							case R.id.nav_about:
								// 1. Instantiate an AlertDialog.Builder with its constructor
								AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);

								final TextView messagge = new TextView(HomeActivity.this);
								messagge.setText(R.string.developInfo);
								messagge.setMovementMethod(LinkMovementMethod.getInstance());
								builder.
										setTitle(R.string.app_name).
										setView(messagge)
										.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
											@Override
											public void onClick(DialogInterface dialog, int which) {
												dialog.cancel();
											}
										});


								// 3. Get the AlertDialog from create()
								AlertDialog dialog = builder.create();
								dialog.show();
								break;
							case R.id.nav_exam_mod:
								FSDeclarationDao fsd = new FSDeclarationDao(getApplicationContext());
								fsd.clear();
								fsd.populate();
								fsd.open();

								HashMap<Date, Declaration> decs = fsd.getAllDeclarations();

								ArrayList<Declaration> items = new ArrayList<>();

								for (Date k : decs.keySet()){
									items.add(decs.get(k));
								}

								fsd.close();

								adapter = new DeclarationListAdapter(getApplicationContext(), R.layout.dec_list_item, items);
								lv.setAdapter(adapter);
							default:
								break;
						}
						return true;
					}
				});


		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		lv = (ListView) findViewById(R.id.lvDichiarazioni);
		lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

		lv.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
			@Override
			public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
				final int checkedCount = lv.getCheckedItemCount();
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
								Declaration selectedItem = adapter.getItem(selected.keyAt(i));
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

		//ModelTestMethods.testWriteReadSomeCrap(this.getApplicationContext());

		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Object o = parent.getItemAtPosition(position);

				if (o instanceof Declaration) {
					Declaration target = (Declaration) o;
					System.out.println(target.getDate());

					Intent intent = new Intent(HomeActivity.this, EditDecActivity.class);
					//intent.putExtra(StaticGlobals.intentExtras.DECLARATION, target);
					intent.putExtra(StaticGlobals.intentExtras.DECLARATION_DATE, target.getDate());

					startActivity(intent);
				}
			}
		});

		lv.setTextFilterEnabled(true);

		insertNewDcard = (FloatingActionButton) findViewById(R.id.fab);

		insertNewDcard.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				Intent newDecIntent = new Intent(HomeActivity.this, EditDecActivity.class);
				//TODO fare meglio: settare solo giorno/mese/anno o che ne so
				//Date date = new Date();

				Date date = DateUtils.today();

				FSDeclarationDao fsd = new FSDeclarationDao(getApplicationContext());
				fsd.open();
				Declaration dec = fsd.getDeclarationByDate(date);

				if (dec == null){
					dec = new Declaration(date);
				}

				fsd.insertDeclaration(dec);
				fsd.close();

				System.out.println(dec);

				//newDecIntent.putExtra(StaticGlobals.intentExtras.DECLARATION, dec);
				newDecIntent.putExtra(StaticGlobals.intentExtras.DECLARATION_DATE, dec.getDate());

				startActivity(newDecIntent);
			}
		});

		insertNewDcard.hide(false);

		new FabAnimation(insertNewDcard, getApplicationContext());

		lv.setOnScrollListener(new ListScrollListener(insertNewDcard));

	}

	@Override
	protected void onResume() {
		super.onResume();

		FSDeclarationDao fsd = new FSDeclarationDao(this.getApplicationContext());

		//fsd.clear();
		//fsd.populate();

		fsd.open();

		HashMap<Date, Declaration> decs = fsd.getAllDeclarations();

		ArrayList<Declaration> items = new ArrayList<>();

		for (Date k : decs.keySet()){
			items.add(decs.get(k));
		}

		fsd.close();

		adapter = new DeclarationListAdapter(this, R.layout.dec_list_item, items);
		lv.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();

		inflater.inflate(R.menu.activity_main_bar, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.


		// Activate the navigation drawer toggle
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	private void setupDrawer(){
		mDrawerToggle = new ActionBarDrawerToggle(
				this,
				mDrawerLayout,         /* DrawerLayout object */
				R.string.aboutNav,  /* "open drawer" description */
				R.string.aboutNav
		){
			/** Called when a drawer has settled in a completely closed state. */
			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
				//getSupportActionBar().setTitle("");
				invalidateOptionsMenu();
			}

			/** Called when a drawer has settled in a completely open state. */
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				//getSupportActionBar().setTitle("");
				invalidateOptionsMenu();
			}
		};


		mDrawerToggle.setDrawerIndicatorEnabled(true);
		mDrawerLayout.setDrawerListener(mDrawerToggle);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	protected void onSaveInstanceState(Bundle icicle) {
		super.onSaveInstanceState(icicle);

	}

}
