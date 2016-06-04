package me.hypertesto.questeasy.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;

import me.hypertesto.questeasy.R;
import me.hypertesto.questeasy.showcase.ButtonLayoutParams;
import me.hypertesto.questeasy.showcase.FabTarget;
import me.hypertesto.questeasy.ui.ImagePagerFragment;
import me.hypertesto.questeasy.utils.StaticGlobals;
import me.hypertesto.questeasy.voice.CustomRecognitionListener;

/*

This activity manages fragment's replacement

 */

public class ActivityGalleryV2 extends AppCompatActivity {

	FloatingActionButton fab;
	private SpeechRecognizer speech = null;
	private Intent recognizerIntent;
	private final Intent resultIntent = new Intent();
	CustomRecognitionListener recognizerListener;

	private ShowcaseView scv;
	private SharedPreferences sharedPref;
	public static final String TUTORIAL_FOURTH_SHOWN = "tutorialFourthShown";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gallery);

		sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

		Fragment fr;
		String tag = ImagePagerFragment.class.getSimpleName();
		fr = getSupportFragmentManager().findFragmentByTag(tag);
		if (fr == null) {
			fr = new ImagePagerFragment();
		}

		Intent intentFromForm = getIntent();
		ArrayList<String> uriStrings = (ArrayList<String>)
				intentFromForm.getSerializableExtra(StaticGlobals.intentExtras.URI_S_TO_GALLERY);

		System.out.println("URISTRINGS: " + uriStrings);

		Bundle fragArgs = new Bundle();
		fragArgs.putStringArrayList(StaticGlobals.bundleArgs.STRING_URIS, uriStrings);
		fr.setArguments(fragArgs);

		getSupportFragmentManager().beginTransaction().add(R.id.gallery_container, fr).commit();

		recognizerListener = new CustomRecognitionListener(getApplicationContext());

		speech = SpeechRecognizer.createSpeechRecognizer(this);
		speech.setRecognitionListener(recognizerListener);
		recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "it-IT");
		recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
				this.getPackageName());
		recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

		fab = (FloatingActionButton) findViewById(R.id.fab_gallery);

		fab.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if (event.getAction() == MotionEvent.ACTION_DOWN){
					if (scv != null){
						scv.hide(); //just to be sure
					}
					Log.i(StaticGlobals.logTags.VOICE_REC, "Start listening");
					speech.startListening(recognizerIntent);
				} else if (event.getAction() == MotionEvent.ACTION_UP){
					Log.i(StaticGlobals.logTags.VOICE_REC, "Stop listening");
					speech.stopListening();
				}
				return false;
			}
		});

		if (sharedPref.getBoolean(TUTORIAL_FOURTH_SHOWN, true)){
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
					scv = new ShowcaseView.Builder(ActivityGalleryV2.this)
							.withMaterialShowcase()
							.setTarget(new FabTarget(fab))
							.setContentTitle(R.string.fourth_step_title)
							.setContentText(R.string.fourth_step_desc)
							.setStyle(R.style.CustomShowcaseTheme2)
							//.hideOnTouchOutside() //this showcase doesn't enforce an action because fabMenu has an issue with showCase
							.build();
					scv.setButtonPosition(new ButtonLayoutParams(getResources()).bottomLeft());
					SharedPreferences.Editor editor = sharedPref.edit();
					editor.putBoolean(TUTORIAL_FOURTH_SHOWN, false);
					editor.apply();
				}
			}.execute();

		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (speech != null) {
			speech.destroy();	//needed to avoid exception if activity is resumed
			Log.i(StaticGlobals.logTags.VOICE_REC, "destroy");
		}

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		if (id == android.R.id.home){
			returnForm();
			finish();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		returnForm();
		super.onBackPressed();
	}

	public void returnForm(){
		Log.d(StaticGlobals.logTags.VOICE_DEBUG, "TEXT: " + recognizerListener.getMatch());
		resultIntent.putExtra(StaticGlobals.intentExtras.MATCHES_FROM_GALLEY, recognizerListener.getMatch());
		setResult(StaticGlobals.resultCodes.VOICE_FROM_GALLEY_SUCCESS, resultIntent);
	}

}
