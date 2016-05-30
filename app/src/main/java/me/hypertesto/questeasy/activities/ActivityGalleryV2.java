package me.hypertesto.questeasy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.github.clans.fab.FloatingActionButton;

import me.hypertesto.questeasy.R;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gallery);

		Fragment fr;
		String tag = ImagePagerFragment.class.getSimpleName();
		fr = getSupportFragmentManager().findFragmentByTag(tag);
		if (fr == null) {
			fr = new ImagePagerFragment();
		}

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
					Log.i(StaticGlobals.logTags.VOICE_REC, "Start listening");
					speech.startListening(recognizerIntent);
				} else if (event.getAction() == MotionEvent.ACTION_UP){
					Log.i(StaticGlobals.logTags.VOICE_REC, "Stop listening");
					speech.stopListening();
				}
				return false;
			}
		});
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (speech != null) {
			speech.destroy();
			Log.i(StaticGlobals.logTags.VOICE_REC, "destroy");
		}

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		if (id == android.R.id.home){
			Log.d(StaticGlobals.logTags.VOICE_DEBUG, "TEXT: " + recognizerListener.getMatch());
			resultIntent.putExtra(StaticGlobals.intentExtras.MATCHES_FROM_GALLEY, recognizerListener.getMatch());
			setResult(StaticGlobals.resultCodes.VOICE_FROM_GALLEY_SUCCESS, resultIntent);
			finish();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
