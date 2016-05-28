package me.hypertesto.questeasy.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.os.Environment;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.support.design.widget.CoordinatorLayout;

import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import java.io.File;
import java.io.Serializable;
import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


import me.hypertesto.questeasy.model.Documento;
import me.hypertesto.questeasy.model.FamilyHeadGuest;
import me.hypertesto.questeasy.model.FamilyMemberGuest;
import me.hypertesto.questeasy.model.GroupHeadGuest;
import me.hypertesto.questeasy.model.GroupMemberGuest;
import me.hypertesto.questeasy.model.Guest;
import me.hypertesto.questeasy.model.MainGuest;
import me.hypertesto.questeasy.model.Place;
import me.hypertesto.questeasy.model.SingleGuest;
import me.hypertesto.questeasy.ui.DatePickerFragment;
import me.hypertesto.questeasy.R;
import me.hypertesto.questeasy.ui.DocumentDataFragment;
import me.hypertesto.questeasy.ui.PermanenzaFragment;
import me.hypertesto.questeasy.ui.PersonalDataFragment;
import me.hypertesto.questeasy.utils.StaticGlobals;
import me.hypertesto.questeasy.utils.UnknownGuestTypeException;
import me.hypertesto.questeasy.voice.Recognition;

public class FormGuestActivity extends AppCompatActivity {
	private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 200;
	public static final int MEDIA_TYPE_IMAGE = 1;


	// directory name to store captured images and videos
	public static final String IMAGE_DIRECTORY_NAME = "QuestEasy";
	private Uri fileUri; // file url to store image/video

	private final Intent resultIntent = new Intent();

	private PermanenzaFragment fragmentPermanenza;
	private PersonalDataFragment fragmentPersonal;
	private DocumentDataFragment fragmentDocument;


	private Serializable ser;

	private BottomBar mBottomBar;

	private String guestType;
	private int permanenza;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_form_guest);


		mBottomBar = BottomBar.attachShy((CoordinatorLayout) findViewById(R.id.formCordinator),
				findViewById(R.id.myScrollingContent), savedInstanceState);
		mBottomBar.setDefaultTabPosition(2);
		mBottomBar.setItemsFromMenu(R.menu.bottom_bar, new OnMenuTabClickListener() {
			@Override
			public void onMenuTabSelected(int menuItemId) {
				switch (menuItemId) {
					case R.id.photoButton:
						captureImage();
						break;

					case R.id.galleryButton:
						startActivity(new Intent(FormGuestActivity.this, ActivityGalleryV2.class));
						break;

					case R.id.voiceButton:

						Log.d(StaticGlobals.logTags.DEBUG, "Not firng event the first time we open activity");
						break;
					default:

				}
			}

			@Override
			public void onMenuTabReSelected(int menuItemId) {
				switch (menuItemId) {
					case R.id.photoButton:
						captureImage();
						break;

					case R.id.galleryButton:
						startActivity(new Intent(FormGuestActivity.this, ActivityGalleryV2.class));
						break;

					case R.id.voiceButton:

						Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
						i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
						i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "it-IT");
						i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Parla ora");
						i.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS, 1000);
						i.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 2000);
						i.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);
						try {
							Log.d(StaticGlobals.logTags.DEBUG, "starting speech intent");
							startActivityForResult(i, StaticGlobals.requestCodes.SPEECH);
						} catch (Exception e) {
							Toast.makeText(getApplicationContext(), "Error initializing speech to text engine.", Toast.LENGTH_LONG).show();
						}
						break;

					default:

				}
			}
		});
		//mBottomBar.noTopOffset();
		//TODO STUB STUPIDO
		Intent intent = getIntent();

		this.ser = intent.getSerializableExtra(StaticGlobals.intentExtras.GUEST_TO_EDIT);
		this.permanenza = intent.getIntExtra(StaticGlobals.intentExtras.PERMANENZA, -1);
		this.guestType = intent.getStringExtra(StaticGlobals.intentExtras.GUEST_TYPE);

		System.out.println("*****Category" + guestType);
		setTitle(guestType);

		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

		fragmentPermanenza = new PermanenzaFragment();
		fragmentPersonal = new PersonalDataFragment();
		fragmentDocument = new DocumentDataFragment();


		switch (guestType) {
			case Guest.type.SINGLE_GUEST:

				fragmentTransaction.add(R.id.fragment_guest_container, fragmentPermanenza);
				fragmentTransaction.add(R.id.fragment_guest_container, fragmentPersonal);
				fragmentTransaction.add(R.id.fragment_guest_container, fragmentDocument);

				break;

			case Guest.type.FAMILY_HEAD:

				fragmentTransaction.add(R.id.fragment_guest_container, fragmentPermanenza);
				fragmentTransaction.add(R.id.fragment_guest_container, fragmentPersonal);
				fragmentTransaction.add(R.id.fragment_guest_container, fragmentDocument);

				break;

			case Guest.type.FAMILY_MEMBER:

				fragmentTransaction.add(R.id.fragment_guest_container, fragmentPersonal);

				break;

			case Guest.type.GROUP_HEAD:

				fragmentTransaction.add(R.id.fragment_guest_container, fragmentPermanenza);
				fragmentTransaction.add(R.id.fragment_guest_container, fragmentPersonal);
				fragmentTransaction.add(R.id.fragment_guest_container, fragmentDocument);

				break;

			case Guest.type.GROUP_MEMBER:

				fragmentTransaction.add(R.id.fragment_guest_container, fragmentPersonal);

				break;

			default:
				throw new UnknownGuestTypeException();

		}

		fragmentTransaction.commit();
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (this.ser == null) {
			//Nuovo guest, non mostrare dati sul form
		} else {
			switch (this.guestType) {
				case Guest.type.SINGLE_GUEST:
				case Guest.type.FAMILY_HEAD:
				case Guest.type.GROUP_HEAD:
					MainGuest mg = (MainGuest) ser;
					fragmentDocument.setDocument(mg.getDocumento());
					fragmentPermanenza.setPermanenza(this.permanenza);

				case Guest.type.FAMILY_MEMBER:
				case Guest.type.GROUP_MEMBER:
					Guest g = (Guest) ser;
					fragmentPersonal.setGuest(g);
					break;

				default:
					throw new UnknownGuestTypeException();
			}
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		if (id == R.id.btnSaveForm) {

			Place p;
			Documento d;

			switch (guestType) {
				case Guest.type.SINGLE_GUEST:

					SingleGuest sg = new SingleGuest();
					sg.setName(fragmentPersonal.getGuestName());
					sg.setSurname(fragmentPersonal.getSurname());

					try {
						sg.setBirthDate(fragmentPersonal.getDateofBirth());
					} catch (ParseException e) {
						e.printStackTrace();
					}

					sg.setSex(fragmentPersonal.getSex());
					sg.setCittadinanza(fragmentPersonal.getCittadinanza());

					p = fragmentPersonal.getBirthPlace();
					sg.setPlaceOfBirth(p);

					d = new Documento();
					d.setDocType(fragmentDocument.getDocumentType());
					d.setCodice(fragmentDocument.getDocumentNumber());
					d.setLuogoRilascio(fragmentDocument.getDocumentReleasePlace());
					sg.setDocumento(d);

					resultIntent.putExtra(StaticGlobals.intentExtras.FORM_OUTPUT_GUEST, sg);
					resultIntent.putExtra(StaticGlobals.intentExtras.PERMANENZA, fragmentPermanenza.getPermanenza());
					setResult(StaticGlobals.resultCodes.GUEST_FORM_SUCCESS, resultIntent);

					break;

				case Guest.type.FAMILY_HEAD:

					FamilyHeadGuest fhg = new FamilyHeadGuest();
					fhg.setName(fragmentPersonal.getGuestName());
					fhg.setSurname(fragmentPersonal.getSurname());

					try {
						fhg.setBirthDate(fragmentPersonal.getDateofBirth());
					} catch (ParseException e) {
						e.printStackTrace();
					}

					fhg.setSex(fragmentPersonal.getSex());
					fhg.setCittadinanza(fragmentPersonal.getCittadinanza());

					p = fragmentPersonal.getBirthPlace();
					fhg.setPlaceOfBirth(p);

					d = new Documento();
					d.setDocType(fragmentDocument.getDocumentType());
					d.setCodice(fragmentDocument.getDocumentNumber());
					d.setLuogoRilascio(fragmentDocument.getDocumentReleasePlace());

					fhg.setDocumento(d);

					resultIntent.putExtra(StaticGlobals.intentExtras.FORM_OUTPUT_GUEST, fhg);
					resultIntent.putExtra(StaticGlobals.intentExtras.PERMANENZA, fragmentPermanenza.getPermanenza());
					setResult(StaticGlobals.resultCodes.GUEST_FORM_SUCCESS, resultIntent);

					break;

				case Guest.type.FAMILY_MEMBER:

					FamilyMemberGuest fmg = new FamilyMemberGuest();
					fmg.setName(fragmentPersonal.getGuestName());
					fmg.setSurname(fragmentPersonal.getSurname());

					try {
						fmg.setBirthDate(fragmentPersonal.getDateofBirth());
					} catch (ParseException e) {
						e.printStackTrace();
					}

					fmg.setSex(fragmentPersonal.getSex());
					fmg.setCittadinanza(fragmentPersonal.getCittadinanza());
					p = fragmentPersonal.getBirthPlace();
					fmg.setPlaceOfBirth(p);

					resultIntent.putExtra(StaticGlobals.intentExtras.FORM_OUTPUT_GUEST, fmg);
					setResult(StaticGlobals.resultCodes.GUEST_FORM_SUCCESS, resultIntent);

					break;

				case Guest.type.GROUP_HEAD:

					GroupHeadGuest ghg = new GroupHeadGuest();
					ghg.setName(fragmentPersonal.getGuestName());
					ghg.setSurname(fragmentPersonal.getSurname());

					try {
						ghg.setBirthDate(fragmentPersonal.getDateofBirth());
					} catch (ParseException e) {
						e.printStackTrace();
					}

					ghg.setSex(fragmentPersonal.getSex());
					ghg.setCittadinanza(fragmentPersonal.getCittadinanza());

					p = fragmentPersonal.getBirthPlace();
					ghg.setPlaceOfBirth(p);

					d = new Documento();
					d.setDocType(fragmentDocument.getDocumentType());
					d.setCodice(fragmentDocument.getDocumentNumber());
					d.setLuogoRilascio(fragmentDocument.getDocumentReleasePlace());
					ghg.setDocumento(d);

					resultIntent.putExtra(StaticGlobals.intentExtras.FORM_OUTPUT_GUEST, ghg);
					resultIntent.putExtra(StaticGlobals.intentExtras.PERMANENZA, fragmentPermanenza.getPermanenza());
					setResult(StaticGlobals.resultCodes.GUEST_FORM_SUCCESS, resultIntent);
					break;

				case Guest.type.GROUP_MEMBER:

					GroupMemberGuest gmg = new GroupMemberGuest();
					gmg.setName(fragmentPersonal.getGuestName());
					gmg.setSurname(fragmentPersonal.getSurname());

					try {
						gmg.setBirthDate(fragmentPersonal.getDateofBirth());
					} catch (ParseException e) {
						e.printStackTrace();
					}

					gmg.setSex(fragmentPersonal.getSex());
					gmg.setCittadinanza(fragmentPersonal.getCittadinanza());
					p = fragmentPersonal.getBirthPlace();
					gmg.setPlaceOfBirth(p);

					resultIntent.putExtra(StaticGlobals.intentExtras.FORM_OUTPUT_GUEST, gmg);
					setResult(StaticGlobals.resultCodes.GUEST_FORM_SUCCESS, resultIntent);
					break;

				default:
					throw new RuntimeException("Unhandled type of guest");
			}

			finish();
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();

		inflater.inflate(R.menu.form_bar, menu);

		return super.onCreateOptionsMenu(menu);
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
			case StaticGlobals.requestCodes.SPEECH:

				if (resultCode == RESULT_OK) {

					ProgressDialog progress = new ProgressDialog(this);
					progress.setTitle("Caricamento");
					progress.setMessage("Attendi mentre analizzo i dati...");
					progress.show();
					// To dismiss the dialog

					Log.d(StaticGlobals.logTags.DEBUG, "Parsing detected voice...");
					ArrayList<String> guestSpeechData = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

					Log.d(StaticGlobals.logTags.VOICE_DEBUG, guestSpeechData.get(0));

					Recognition rec = new Recognition();

					switch (guestType) {

						case Guest.type.SINGLE_GUEST:
						case Guest.type.FAMILY_HEAD:
						case Guest.type.GROUP_HEAD:
							fragmentPermanenza.setPermanenza(rec.parsePermanenza(guestSpeechData.get(0)));
							fragmentDocument.setDocument(rec.parseDocumentInfo(guestSpeechData.get(0)));
							break;

						case Guest.type.FAMILY_MEMBER:
						case Guest.type.GROUP_MEMBER:
							break;

						default:

					}
					fragmentPersonal.setGuest(rec.parsePersonalInfo(guestSpeechData.get(0), guestType));
					progress.dismiss();
				}
				break;

			default:

		}

	}

	//showing date
	public void showDatePickerDialog(View v) {
		DialogFragment newFragment = new DatePickerFragment();
		newFragment.show(getSupportFragmentManager(), "datePicker");
	}

	/*
 	 Launch the camera
 	*/

	private void captureImage() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

		intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

		// start the image capture Intent
		startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
	}


	/**
	 * Creating file uri to store image/video
	 */

	public Uri getOutputMediaFileUri(int type) {
		return Uri.fromFile(getOutputMediaFile(type));
	}


	/*
	 * returning image / video
	 */


	private static File getOutputMediaFile(int type) {

		// External sdcard location
		File mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				IMAGE_DIRECTORY_NAME);

		// Create the storage directory if it does not exist
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.e(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
						+ IMAGE_DIRECTORY_NAME + " directory");
				return null;
			}
		}

		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
				Locale.getDefault()).format(new Date());
		File mediaFile;
		if (type == MEDIA_TYPE_IMAGE) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ "IMG_" + timeStamp + ".jpg");
		} else {
			return null;
		}

		return mediaFile;
	}
}