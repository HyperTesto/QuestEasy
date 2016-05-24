package me.hypertesto.questeasy.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import me.hypertesto.questeasy.R;
import me.hypertesto.questeasy.model.Card;
import me.hypertesto.questeasy.model.Declaration;
import me.hypertesto.questeasy.model.FamilyCard;
import me.hypertesto.questeasy.model.FamilyHeadGuest;
import me.hypertesto.questeasy.model.FamilyMemberGuest;
import me.hypertesto.questeasy.model.GroupCard;
import me.hypertesto.questeasy.model.GroupHeadGuest;
import me.hypertesto.questeasy.model.GroupMemberGuest;
import me.hypertesto.questeasy.model.SingleGuest;
import me.hypertesto.questeasy.model.adapters.CardListAdapter;
import me.hypertesto.questeasy.model.SingleGuestCard;
import me.hypertesto.questeasy.model.dao.fs.FSDeclarationDao;
import me.hypertesto.questeasy.utils.DateUtils;
import me.hypertesto.questeasy.utils.FabAnimation;
import me.hypertesto.questeasy.utils.FileUtils;
import me.hypertesto.questeasy.utils.FormatQuestura;
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
	private RelativeLayout itemContainer;
	private Animation flipAnim;
	private Animation flipAnimReverse;
	private ImageView letterImage;
	private TextDrawable textDrawable;
	CardListAdapter adapter;
	private int previousColor;

	private Declaration displayed;
	private int indexClicked;
	private int indexClickedSavePopUp;

	private AlertDialog.Builder saveDialogBuilder;

	private final CharSequence [] dialogItems = {StaticGlobals.saveDialogOptions.SAVE_DISK,
			StaticGlobals.saveDialogOptions.SAVE_DROPBOX,
			StaticGlobals.saveDialogOptions.SEND_MAIL};

	private AlertDialog saveAlertDialog;

	private AlertDialog.Builder filterDialogBuilder;
	private ArrayList<String> selectedItemDialogFilter;
	private final CharSequence [] dialogItemsFilter = {"Ospite singolo", "Famiglia", "Gruppo"};
	private AlertDialog filterAlertDialog;


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

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
			case R.id.action_saveDec:
				saveAlertDialog.show();
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
		fabMenu = (FloatingActionMenu) findViewById
				(R.id.categoryGuestGo);
		singlefab = (FloatingActionButton) findViewById(R.id.categoryGuestSingleGo);
		groupFab = (FloatingActionButton) findViewById(R.id.categoryGuestGroupGo);
		familyFab = (FloatingActionButton) findViewById(R.id.categoryGuestFamilyGo);
		flipAnim = AnimationUtils.loadAnimation(EditDecActivity.this,R.anim.flip_anim);
		flipAnimReverse = AnimationUtils.loadAnimation(EditDecActivity.this,R.anim.flip_anim);

		createSaveDialog();
		createFilterDialog();

		Intent intent = getIntent();


		//ArrayList<Card> d = (ArrayList<Card>) intent.getSerializableExtra(StaticGlobals.intentExtras.DECLARATION);
		//this.displayed = new Declaration();
		//this.displayed.setDate(date);
		//this.displayed.addAll(d);

		Date date = (Date) intent.getSerializableExtra(StaticGlobals.intentExtras.DECLARATION_DATE);

		FSDeclarationDao fsd = new FSDeclarationDao(this.getApplicationContext());
		fsd.open();
		this.displayed = fsd.getDeclarationByDate(date);
		fsd.close();

		this.updateList();


		fabMenu.hideMenuButton(false);

		new FabAnimation(fabMenu, getApplicationContext());
		flipAnim.setAnimationListener(new Animation.AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				textDrawable = (TextDrawable) letterImage.getDrawable();
				previousColor = textDrawable.getPaint().getColor();
				//TextDrawable drawable = TextDrawable.builder().buildRoundRect(item.getInitialLetter(),
				//color, 100);
				//textDrawable.setTint(getResources().getColor(R.color.background_bar));
				//textDrawable.setColorFilter(R.color.background_bar, PorterDuff.Mode.MULTIPLY);
				//textDrawable.getPaint().setColor(getResources().getColor(R.color.background_bar));

				//if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
					DrawableCompat.setTint(textDrawable,getResources().getColor(R.color.background_bar));
				//} else {
					//Drawable wrappedDrawable = DrawableCompat.wrap(textDrawable);
					//wrappedDrawable.setTintList(getResources().getColorStateList(R.color.background_bar));

				//}
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
				//TextDrawable drawable = TextDrawable.builder().buildRoundRect(item.getInitialLetter(),
				//color, 100);
				//textDrawable.setTint(getResources().getColor(R.color.background_bar));
				//textDrawable.setColorFilter(R.color.background_bar, PorterDuff.Mode.MULTIPLY);
				//textDrawable.getPaint().setColor(getResources().getColor(R.color.background_bar));
				DrawableCompat.setTint(textDrawable,previousColor);
				letterImage.setImageDrawable(textDrawable);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}
		});

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

				itemContainer = (RelativeLayout)listView.getChildAt(position);
				letterImage = (ImageView) itemContainer.findViewById(R.id.cardTypeImg);
				if (checked){
					letterImage.startAnimation(flipAnim);
				}
				else{
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
				if (fabMenu.isOpened()) {
					fabMenu.close(false);
				}

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
				String exportQuestura = FormatQuestura.convert(displayed);
				switch (selected) {

					case StaticGlobals.saveDialogOptions.SAVE_DISK:
						//try to write the file
						//TODO: test me
						System.out.println(FormatQuestura.convert(displayed));
						File f = null;
						DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

						String fileName = "export_" + df.format(DateUtils.today()); //FIXME: better naming
						try {
							f = FileUtils.getFileQuesturaStorageDir(fileName);
						} catch (IOException e) {
							e.printStackTrace();
						}
						OutputStream out = null;

						try {
							//TODO: handle special cases (card errors...)
							out = new BufferedOutputStream(new FileOutputStream(f));
							out.write(exportQuestura.getBytes());
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							if (out != null) {
								try {
									out.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}

						}
						break;
					case StaticGlobals.saveDialogOptions.SAVE_DROPBOX:

						break;
					case StaticGlobals.saveDialogOptions.SEND_MAIL: //FIXME: clean bad code

						Log.d("MAIN", "sending email...");
						Intent intent = new Intent(Intent.ACTION_SEND);
						intent.setType("text/plain");
						//intent.putExtra(Intent.EXTRA_EMAIL, new String[] {"email@example.com"});
						intent.putExtra(Intent.EXTRA_SUBJECT, "File questura");
						intent.putExtra(Intent.EXTRA_TEXT, "In allegato il file della questura pronto per l'invio");
						File file = null;
						df = new SimpleDateFormat("dd-MM-yyyy");
						try {
							file = FileUtils.getFileQuesturaStorageDir( "export_"+ df.format(DateUtils.today())+ ".txt");
						} catch (IOException e) {
							e.printStackTrace();
						}
						/*if (file != null && (!file.exists() || !file.canRead())) {
							//Toast.makeText(this, "Attachment Error", Toast.LENGTH_SHORT).show();
							Log.e("[SEND_MAIL]", "Cannot open temp file");
							finish();
							return;
						}*/
						OutputStream out2 = null;

						try {
							//TODO: handle special cases (card errors...)
							out2 = new BufferedOutputStream(new FileOutputStream(file));
							out2.write(exportQuestura.getBytes());
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							if (out2 != null) {
								try {
									out2.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}

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
				setMultiChoiceItems(dialogItemsFilter, null, new DialogInterface.OnMultiChoiceClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which, boolean isChecked) {
						//filterAlertDialog.dismiss();
						if (isChecked){
							selectedItemDialogFilter.add(dialogItemsFilter[which].toString().
									toUpperCase());
						} else if (selectedItemDialogFilter.contains(dialogItemsFilter[which].
								toString().toUpperCase())){
							selectedItemDialogFilter.remove(which);
						}
					}
				}).
				setPositiveButton(R.string.okButton, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						for (int i = 0; i < selectedItemDialogFilter.size(); i++){
							System.out.println("val "+selectedItemDialogFilter.get(i));
						}
						adapter.filter(selectedItemDialogFilter);
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

	@Override
	public void onPause(){
		super.onPause();
		//fabMenu.close(false);
		//System.out.println("RELAX");
	}

	@Override
	public void onResume(){
		fabMenu.close(false);
		super.onResume();
		//System.out.println("NO relax");
	}
}
