package me.hypertesto.questeasy.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.AsyncTask;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.amlcurran.showcaseview.ShowcaseView;
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
import me.hypertesto.questeasy.showcase.ButtonLayoutParams;
import me.hypertesto.questeasy.showcase.ShowcaseTarget;
import me.hypertesto.questeasy.utils.DateUtils;
import me.hypertesto.questeasy.utils.FabAnimation;
import me.hypertesto.questeasy.utils.ListScrollListener;
import me.hypertesto.questeasy.utils.StaticGlobals;
import me.hypertesto.questeasy.utils.WordsCapitalizer;

public class HomeActivity extends AppCompatActivity{

	private ListView lv;
	private FloatingActionButton insertNewDcard;
	private DeclarationListAdapter adapter;
	private NavigationView mNavigationView;
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private ActionMode myMode;
	private SharedPreferences sharedPref;
	private ShowcaseView scv;

	public static final String TUTORIAL_FIRST_SHOWN = "tutorialFirstShown";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

		// Android guidelines
		int readStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
		int writeStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
		int internet = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
		int camera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
		int phoneState = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
		int wakeLock = ContextCompat.checkSelfPermission(this, Manifest.permission.WAKE_LOCK);
		int recordAudio = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);

		if ( readStorage != PackageManager.PERMISSION_GRANTED
			|| writeStorage != PackageManager.PERMISSION_GRANTED
			|| internet != PackageManager.PERMISSION_GRANTED
			|| camera != PackageManager.PERMISSION_GRANTED
			|| phoneState != PackageManager.PERMISSION_GRANTED
			|| wakeLock != PackageManager.PERMISSION_GRANTED
			|| recordAudio != PackageManager.PERMISSION_GRANTED) {

			ActivityCompat.requestPermissions(this,
					new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
							Manifest.permission.WRITE_EXTERNAL_STORAGE,
							Manifest.permission.INTERNET,
							Manifest.permission.CAMERA,
							Manifest.permission.READ_PHONE_STATE,
							Manifest.permission.WAKE_LOCK,
							Manifest.permission.RECORD_AUDIO
					},
					StaticGlobals.permissions.REQUEST_MANDATORY);

		}

		NotificationEventReceiver.setUpAlarm(this.getApplicationContext());

		mNavigationView = (NavigationView)findViewById(R.id.nav_view_main);
		mDrawerLayout = (DrawerLayout)findViewById(R.id.nav_drawer_layout);


		setupDrawer();

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
								adapter.clear();
								adapter.addAll(decs);
								//adapter = new DeclarationListAdapter(getApplicationContext(), R.layout.dec_list_item, decs);
								//lv.setAdapter(adapter);
								break;
							case R.id.nav_settings:
								startActivity(new Intent(HomeActivity.this, SettingsActivity.class));
								break;
							case R.id.nav_api_guide:
								startActivity(new Intent(HomeActivity.this, VocalGuideActivity.class));
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
				if (scv != null){
					scv.hide();	//hide the showcase on fab click
				}
				startActivity(newDecIntent);
			}
		});

		insertNewDcard.hide(false);

		new FabAnimation(insertNewDcard, getApplicationContext());

		lv.setOnScrollListener(new ListScrollListener(insertNewDcard));

		if (sharedPref.getBoolean(TUTORIAL_FIRST_SHOWN, true)){
			System.out.println("Building showcase...");
			new AsyncTask<String, Integer, String>(){

				@Override
				protected String doInBackground(String... params) {
					try {
						Thread.sleep(1500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("End sleep");
					return null;
				}
				// This runs in UI when background thread finishes
				@Override
				protected void onPostExecute(String result) {
					super.onPostExecute(result);
					System.out.println("onPostExecute");
					scv = new ShowcaseView.Builder(HomeActivity.this)
							.withMaterialShowcase()
							.setTarget(new ShowcaseTarget.Fab(insertNewDcard))
							.setContentTitle(R.string.welcome)
							.setContentText(R.string.first_desc)
							.setStyle(R.style.CustomShowcaseTheme2)
							.build();
					scv.setButtonPosition(new ButtonLayoutParams(getResources()).bottomLeft());
					SharedPreferences.Editor editor = sharedPref.edit();
					editor.putBoolean(TUTORIAL_FIRST_SHOWN, false);
					editor.apply();
				}
			}.execute();

		}


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
	protected void onPause() {
		super.onPause();

		if (myMode != null){
			myMode.finish();
		}
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
				R.string.opendDrawer,  /* "open drawer" description */
				R.string.closeDrawer
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

			//TODO: android guidelines suggest to ask only few permission in a single shot
			case StaticGlobals.permissions.REQUEST_MANDATORY: {
				// If request is cancelled, the result arrays are empty.
				if (grantResults.length > 0
						&& grantResults[0] == PackageManager.PERMISSION_GRANTED
						&& grantResults[1] == PackageManager.PERMISSION_GRANTED
						&& grantResults[2] == PackageManager.PERMISSION_GRANTED
						&& grantResults[3] == PackageManager.PERMISSION_GRANTED
						&& grantResults[4] == PackageManager.PERMISSION_GRANTED
						&& grantResults[5] == PackageManager.PERMISSION_GRANTED
						&& grantResults[6] == PackageManager.PERMISSION_GRANTED) {

					// permission was granted, yay! Do the
					// contacts-related task you need to do.

				} else {

					CharSequence text = getApplicationContext().getString(R.string.permission_none);
					int duration = Toast.LENGTH_LONG;

					Toast toast = Toast.makeText(getApplicationContext(), text, duration);
					toast.show();
					Log.i("INFO", getResources().getString(R.string.permission_none));
				}
			}

			// other 'case' lines to check for other
			// permissions this app might request
		}
	}
}
