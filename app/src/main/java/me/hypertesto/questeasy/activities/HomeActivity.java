package me.hypertesto.questeasy.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
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
import android.widget.ListView;
import android.widget.TextView;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ActionViewTarget;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import me.hypertesto.questeasy.R;
import me.hypertesto.questeasy.model.Declaration;
import me.hypertesto.questeasy.model.User;
import me.hypertesto.questeasy.model.adapters.DeclarationListAdapter;
import me.hypertesto.questeasy.model.dao.fs.FSDeclarationDao;
import me.hypertesto.questeasy.notifications.NotificationEventReceiver;
import me.hypertesto.questeasy.utils.DateUtils;
import me.hypertesto.questeasy.utils.FabAnimation;
import me.hypertesto.questeasy.utils.ListScrollListener;
import me.hypertesto.questeasy.utils.StaticGlobals;
import me.hypertesto.questeasy.utils.WordsCapitalizer;

public class HomeActivity extends AppCompatActivity implements Target{

	private ListView lv;
	private FloatingActionButton insertNewDcard;
	private DeclarationListAdapter adapter;
	private NavigationView mNavigationView;
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private ActionMode myMode;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		int readStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
		int writeStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
		int internet = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);

		if ( readStorage != PackageManager.PERMISSION_GRANTED
			|| writeStorage != PackageManager.PERMISSION_GRANTED
			|| internet != PackageManager.PERMISSION_GRANTED) {

			/*// Should we show an explanation?
			if (ActivityCompat.shouldShowRequestPermissionRationale(this,
					Manifest.permission.READ_CONTACTS)) {

				// Show an expanation to the user *asynchronously* -- don't block
				// this thread waiting for the user's response! After the user
				// sees the explanation, try again to request the permission.

			} else {
			*/
				// No explanation needed, we can request the permission.

				ActivityCompat.requestPermissions(this,
						new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
									Manifest.permission.WRITE_EXTERNAL_STORAGE,
									Manifest.permission.INTERNET},
						StaticGlobals.permissions.REQUEST_MANDATORY);

				// MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
				// app-defined int constant. The callback method gets the
				// result of the request.
			//}
		}

		NotificationEventReceiver.setUpAlarm(this.getApplicationContext());

		mNavigationView = (NavigationView)findViewById(R.id.nav_view_main);
		mDrawerLayout = (DrawerLayout)findViewById(R.id.nav_drawer_layout);


		setupDrawer();
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
		//String syncConnPref = sharedPref.getString(SettingsActivity.KEY_PREF_SYNC_CONN, "");

		Log.d(StaticGlobals.logTags.DEBUG, "pref_user" + sharedPref.getString("pref_user", ""));

		View temp = mNavigationView.getHeaderView(0);
		TextView userName = (TextView)temp.findViewById(R.id.subInfo);

		userName.setText(WordsCapitalizer.capitalizeEveryWord(sharedPref.getString("pref_user", "Utente")));

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


								builder.
										setTitle(R.string.app_name).
										setMessage(R.string.developInfo)
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

								ArrayList<Declaration> decs = fsd.getAllDeclarations();
								Collections.sort(decs);

								fsd.close();

								adapter = new DeclarationListAdapter(getApplicationContext(), R.layout.dec_list_item, decs);
								lv.setAdapter(adapter);
								break;
							case R.id.nav_settings:
								startActivity(new Intent(HomeActivity.this, SettingsActivity.class));
								break;
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
				myMode = mode;
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
								FSDeclarationDao fsd = new FSDeclarationDao(getApplicationContext());
								fsd.open();
								fsd.deleteDeclaration(selectedItem);
								fsd.close();
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
					intent.putExtra(StaticGlobals.intentExtras.DECLARATION_OWNER, target.getOwner());

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

				Date date = DateUtils.today();

				FSDeclarationDao fsd = new FSDeclarationDao(getApplicationContext());
				fsd.open();

				//TODO replace with actual user
				User currentUser = new User("Current", "User");
				Declaration dec = fsd.getDeclarationByOwnerDate(currentUser, date);

				if (dec == null){
					dec = new Declaration(date, currentUser);
				}

				fsd.insertDeclaration(dec);
				fsd.close();

				System.out.println(dec);

				//newDecIntent.putExtra(StaticGlobals.intentExtras.DECLARATION, dec);
				newDecIntent.putExtra(StaticGlobals.intentExtras.DECLARATION_DATE, dec.getDate());
				newDecIntent.putExtra(StaticGlobals.intentExtras.DECLARATION_OWNER, dec.getOwner());

				checkAndDeleteModeAction();
				startActivity(newDecIntent);
			}
		});

		insertNewDcard.hide(false);

		new FabAnimation(insertNewDcard, getApplicationContext());

		lv.setOnScrollListener(new ListScrollListener(insertNewDcard));

		new ShowcaseView.Builder(this)
				.withMaterialShowcase()
				.setTarget(this)
				.setContentTitle("Titolo")
				.setContentText("Test")
				.build();

	}

	@Override
	protected void onResume() {
		super.onResume();

		FSDeclarationDao fsd = new FSDeclarationDao(this.getApplicationContext());

		//fsd.clear();
		//fsd.populate();

		fsd.open();

		//TODO replace with getDeclarationsByOwner();
		ArrayList<Declaration> decs = fsd.getAllDeclarations();
		Collections.sort(decs);

		fsd.close();

		adapter = new DeclarationListAdapter(this, R.layout.dec_list_item, decs);
		lv.setAdapter(adapter);
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

	/**
	 * This method delete actionMode when swtiching between activities
	 */
	private void checkAndDeleteModeAction(){
		if (myMode != null){
			myMode.finish();
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode,
										   String permissions[], int[] grantResults) {
		switch (requestCode) {
			case StaticGlobals.permissions.REQUEST_MANDATORY: {
				// If request is cancelled, the result arrays are empty.
				if (grantResults.length > 0
						&& grantResults[0] == PackageManager.PERMISSION_GRANTED
						&& grantResults[1] == PackageManager.PERMISSION_GRANTED
						&& grantResults[2] == PackageManager.PERMISSION_GRANTED) {

					// permission was granted, yay! Do the
					// contacts-related task you need to do.

				} else {


					Log.i("INFO", "l'applicazione ha bisogno almeno di questi permessi per funzionare correttamente!");
				}
				return;
			}

			// other 'case' lines to check for other
			// permissions this app might request
		}
	}

	@Override
	public Point getPoint() {
		int[] coord = new int[2];
		insertNewDcard.getLocationInWindow(coord);
		return new Point(coord[0] + (int) insertNewDcard.getPivotX(),
						coord[1] + (int) insertNewDcard.getPivotY());
	}
}
