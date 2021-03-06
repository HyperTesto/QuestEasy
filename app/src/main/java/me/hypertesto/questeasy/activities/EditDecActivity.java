package me.hypertesto.questeasy.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import me.hypertesto.questeasy.R;
import me.hypertesto.questeasy.model.Card;
import me.hypertesto.questeasy.model.Declaration;
import me.hypertesto.questeasy.model.FamilyCard;
import me.hypertesto.questeasy.model.GroupCard;
import me.hypertesto.questeasy.model.User;
import me.hypertesto.questeasy.model.adapters.CardListAdapter;
import me.hypertesto.questeasy.model.SingleGuestCard;
import me.hypertesto.questeasy.model.dao.fs.FSDeclarationDao;
import me.hypertesto.questeasy.showcase.ButtonLayoutParams;
import me.hypertesto.questeasy.showcase.ShowcaseTarget;
import me.hypertesto.questeasy.utils.DateUtils;
import me.hypertesto.questeasy.utils.FabAnimation;
import me.hypertesto.questeasy.utils.FileUtils;
import me.hypertesto.questeasy.utils.FormatQuestura;
import me.hypertesto.questeasy.utils.ListScrollListener;
import me.hypertesto.questeasy.utils.StaticGlobals;

public class EditDecActivity extends AppCompatActivity {

	private ListView listView;
	private FrameLayout frameLayout;
	private FloatingActionMenu fabMenu;
	private FloatingActionButton singlefab;
	private FloatingActionButton groupFab;
	private FloatingActionButton familyFab;


	CardListAdapter adapter;

	public static final String TUTORIAL_SECOND_SHOWN = "tutorialSecondShown";

	private Declaration displayed;
	private int indexClicked;
	private int indexClickedSavePopUp;

	private AlertDialog.Builder saveDialogBuilder;


	private final CharSequence [] dialogItems = {StaticGlobals.saveDialogOptions.SAVE_DISK,
			StaticGlobals.saveDialogOptions.SHARE};

	private AlertDialog saveAlertDialog;

	private AlertDialog.Builder filterDialogBuilder;
	private ArrayList<String> selectedItemDialogFilter;
	private final CharSequence [] dialogItemsFilter = {StaticGlobals.filterDialogOptions.FILTER_SINGLE,
			StaticGlobals.filterDialogOptions.FILTER_FAMILY,
			StaticGlobals.filterDialogOptions.FILTER_GROUP};
	private boolean[] selectedCheckedItems ={false, false, false};
	private AlertDialog filterAlertDialog;
	private ActionMode myMode;
	private ShowcaseView scv;
	private SharedPreferences sharedPref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_dec);

		sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);


		defineSettings();

		if (sharedPref.getBoolean(TUTORIAL_SECOND_SHOWN, true)){
			System.out.println("Building showcase...");
			new AsyncTask<String, Integer, String>(){

				@Override
				protected String doInBackground(String... params) {
					try {
						Thread.sleep(1000);
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
					scv = new ShowcaseView.Builder(EditDecActivity.this)
							.withMaterialShowcase()
							.setTarget(new ShowcaseTarget.FabMenu(fabMenu))
							.setContentTitle(R.string.second_step_title)
							.setContentText(R.string.second_step_desc)
							.setStyle(R.style.CustomShowcaseTheme2)
							.hideOnTouchOutside() //this showcase doesn't enforce an action because fabMenu has an issue with showCase
							.build();
					scv.setButtonPosition(new ButtonLayoutParams(getResources()).bottomLeft());
					scv.setButtonPosition(new ButtonLayoutParams(getResources()).bottomLeft());
					SharedPreferences.Editor editor = sharedPref.edit();
					editor.putBoolean(TUTORIAL_SECOND_SHOWN, false);
					editor.apply();
				}
			}.execute();

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();

		inflater.inflate(R.menu.edit_dec_bar, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
			case R.id.action_saveDec:
				FSDeclarationDao fsd = new FSDeclarationDao(getApplicationContext());

				fsd.open();
				fsd.updateDeclaration(displayed);
				fsd.close();

				//Intent i = new Intent(EditDecActivity.this, HomeActivity.class);
				Toast.makeText(this, "Aggiornamento completato", Toast.LENGTH_LONG).show();
				//startActivity(i);
				finish();
				return true;

			case R.id.action_export_dec:
				saveAlertDialog.show();
				return true;

			case R.id.action_delete_dec:
				FSDeclarationDao fsd2 = new FSDeclarationDao(getApplicationContext());

				fsd2.open();
				fsd2.deleteDeclaration(displayed);
				fsd2.close();

				//Intent ii = new Intent(EditDecActivity.this, HomeActivity.class);
				Toast.makeText(this, "Cancellazione completata", Toast.LENGTH_LONG).show();
				finish();
				//startActivity(ii);
				return true;

			case R.id.action_filterDec:
				filterAlertDialog.show();
				return true;

			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private void defineSettings() {
		frameLayout = (FrameLayout) findViewById(R.id.frameButtonCategory);
		fabMenu = (FloatingActionMenu) findViewById(R.id.categoryGuestGo);
		singlefab = (FloatingActionButton) findViewById(R.id.categoryGuestSingleGo);
		groupFab = (FloatingActionButton) findViewById(R.id.categoryGuestGroupGo);
		familyFab = (FloatingActionButton) findViewById(R.id.categoryGuestFamilyGo);
		//flipAnim = AnimationUtils.loadAnimation(EditDecActivity.this,R.anim.flip_anim);
		//flipAnimReverse = AnimationUtils.loadAnimation(EditDecActivity.this,R.anim.flip_anim);

		createSaveDialog();
		createFilterDialog();

		Intent intent = getIntent();

		Date date = (Date) intent.getSerializableExtra(StaticGlobals.intentExtras.DECLARATION_DATE);
		User owner = (User) intent.getSerializableExtra(StaticGlobals.intentExtras.DECLARATION_OWNER);

		FSDeclarationDao fsd = new FSDeclarationDao(this.getApplicationContext());
		fsd.open();
		this.displayed = fsd.getDeclarationByOwnerDate(owner, date);
		fsd.close();

		this.updateList();

		fabMenu.hideMenuButton(false);

		new FabAnimation(fabMenu, getApplicationContext());

		listView.setOnScrollListener(new ListScrollListener(fabMenu));
		fabMenu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (scv != null){
					scv.hide();
				}
			}
		});

		try{
			frameLayout.getBackground().setAlpha(0);

		} catch (NullPointerException e) {
			Log.e("ERROR", "error null field");
		}

		sendFormRequest(singlefab,0);
		sendFormRequest(groupFab, 1);
		sendFormRequest(familyFab, 2);

		final Intent intentToEditCard = new Intent(EditDecActivity.this, EditCardActivity.class);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Object o = parent.getItemAtPosition(position);
				indexClicked = position;

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

				startActivityForResult(intentToEditCard, StaticGlobals.requestCodes.EDIT_CARD);
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
				Log.d("SELECTED   ", "s");
				switch (item.getItemId()) {
					case R.id.delete:
						SparseBooleanArray selected = adapter.getSelectedIds();
						Log.e("selected", String.valueOf(selected));
						for (int i = (selected.size() - 1); i >= 0; i--) {
							if (selected.valueAt(i)) {
								Card selectedItem = adapter.getItem(selected.keyAt(i));
								displayed.remove(selectedItem);

								FSDeclarationDao fsd = new FSDeclarationDao(getApplicationContext());
								fsd.open();
								fsd.updateDeclaration(displayed);
								fsd.close();
								updateList();
								//adapter.remove(selectedItem);
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
				if (fabMenu.isOpened()) {
					fabMenu.close(false);
				}
				checkAndDeleteModeAction();
				startActivityForResult(intentForm, StaticGlobals.requestCodes.NEW_CARD);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		FSDeclarationDao fsd = new FSDeclarationDao(getApplicationContext());

		switch (requestCode){

			case StaticGlobals.requestCodes.NEW_CARD:
				if (resultCode == StaticGlobals.resultCodes.EDIT_CARD_SUCCESS){
					Object o = data.getSerializableExtra(StaticGlobals.intentExtras.CARD);

					if (o instanceof Card){
						Card c = (Card) o;
						displayed.add(c);

						fsd.open();
						fsd.updateDeclaration(this.displayed);
						fsd.close();

						this.updateList();
					} else {
						throw new RuntimeException("");
					}
				}
				break;

			case StaticGlobals.requestCodes.EDIT_CARD:
				if (resultCode == StaticGlobals.resultCodes.EDIT_CARD_SUCCESS){
					Object o = data.getSerializableExtra(StaticGlobals.intentExtras.CARD);

					if (o instanceof Card){
						Card c = (Card) o;

						displayed.remove(indexClicked);
						displayed.add(c);

						fsd.open();
						fsd.updateDeclaration(this.displayed);
						fsd.close();

						this.updateList();
					} else {
						throw new RuntimeException("");
					}
				}
				break;

		}
	}

	public void createSaveDialog (){
		saveDialogBuilder = new AlertDialog.Builder(EditDecActivity.this);

		saveDialogBuilder.setTitle(R.string.saveDialogTitle);
		saveDialogBuilder.setSingleChoiceItems(dialogItems, 0, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(EditDecActivity.this, dialogItems[which], Toast.LENGTH_SHORT).show();
				//saveAlertDialog.dismiss();
				indexClickedSavePopUp = which;
			}
		});
		saveDialogBuilder.setPositiveButton(R.string.okButton, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String selected = dialogItems[indexClickedSavePopUp].toString();
				System.out.println(displayed);
				String exportQuestura = FormatQuestura.convert(displayed);
				File file = null;

				switch (selected) {

					case StaticGlobals.saveDialogOptions.SAVE_DISK:

						String fileName = "export_" + DateUtils.formatForFileName(DateUtils.today()) + ".txt"; //FIXME: better naming

						try {
							file = FileUtils.getFileQuesturaStorageDir(fileName);
							FormatQuestura.writeFile(exportQuestura, file);
						} catch (IOException e) {
							e.printStackTrace();
						}

						break;
					case StaticGlobals.saveDialogOptions.SHARE: //FIXME: clean bad code

						Log.d("MAIN", "sending email...");
						Intent intent = new Intent(Intent.ACTION_SEND);
						intent.setType("text/plain");
						//intent.putExtra(Intent.EXTRA_EMAIL, new String[] {"email@example.com"});
						intent.putExtra(Intent.EXTRA_SUBJECT, "File questura");
						intent.putExtra(Intent.EXTRA_TEXT, "In allegato il file della questura pronto per l'invio");
						try {
							file = FileUtils.getFileQuesturaStorageDir( "export_"+ DateUtils.formatForFileName(DateUtils.today())+ ".txt");
							FormatQuestura.writeFile(exportQuestura, file);
						} catch (IOException e) {
							e.printStackTrace();
						}
						Uri uri = Uri.fromFile(file);
						intent.putExtra(Intent.EXTRA_STREAM, uri);
						startActivity(Intent.createChooser(intent, "Send email..."));

						break;
					default:
				}
				saveAlertDialog.dismiss();
			}
		});
		saveDialogBuilder.setNegativeButton(R.string.cancelButton, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				saveAlertDialog.dismiss();
			}
		});
		saveAlertDialog = saveDialogBuilder.create();

	}

	public void createFilterDialog(){
		selectedItemDialogFilter = new ArrayList();
		ContextThemeWrapper ctw = new ContextThemeWrapper(this, R.style.AppTheme);
		filterDialogBuilder = new AlertDialog.Builder(ctw);
		filterDialogBuilder.setTitle(R.string.filterDialotTitle).
				setMultiChoiceItems(dialogItemsFilter, selectedCheckedItems , new DialogInterface.OnMultiChoiceClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which, boolean isChecked) {
						//filterAlertDialog.dismiss();

						if (isChecked){
							selectedCheckedItems[which] = true;
							selectedItemDialogFilter.add(dialogItemsFilter[which].toString());
						} else if (selectedItemDialogFilter.contains(dialogItemsFilter[which].
								toString())){
							selectedItemDialogFilter.remove(dialogItemsFilter[which].toString());
							selectedCheckedItems[which] = false;
						}
					}
				}).
				setPositiveButton(R.string.okButton, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

						ArrayList<Card> items = new ArrayList<>();
						items.addAll(displayed);
						adapter = new CardListAdapter(EditDecActivity.this,R.layout.card_list_item,items);
						adapter.getFilter().filter(selectedItemDialogFilter.toString());
						listView = (ListView)findViewById(R.id.cardlistView);
						listView.setAdapter(adapter);
						filterAlertDialog.dismiss();
					}
				}).
				setNegativeButton(R.string.cancelButton, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						filterAlertDialog.dismiss();

					}
				});


		filterAlertDialog = filterDialogBuilder.create();


	}


	private void updateList(){
		ArrayList<Card> items = new ArrayList<>();
		items.addAll(displayed);
		adapter = new CardListAdapter(this,R.layout.card_list_item,items);
		listView = (ListView)findViewById(R.id.cardlistView);
		listView.setAdapter(adapter);
	}


	private void checkAndDeleteModeAction(){
		if (myMode != null){
			myMode.finish();
		}
	}
	@Override
	public void onPause(){
		super.onPause();
		//fabMenu.close(false);

	}

	@Override
	public void onResume(){
		fabMenu.close(false);
		super.onResume();

	}
}
