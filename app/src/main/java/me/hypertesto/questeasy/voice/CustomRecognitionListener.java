package me.hypertesto.questeasy.voice;

import android.content.Context;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import me.hypertesto.questeasy.utils.StaticGlobals;

/**
 * Class that implements a custom voice recognition listener.
 * This is used to launch a voice rec intent witout the standard google interface.
 * Created by hypertesto on 30/05/16.
 */
public class CustomRecognitionListener implements RecognitionListener {

	private Context context;
	private String match;

	public CustomRecognitionListener(Context context, String match){
		this.context = context;
		this.match= match;
	}

	@Override
	public void onReadyForSpeech(Bundle params) {
		Log.i(StaticGlobals.logTags.VOICE_REC, "onReadyForSpeech");

		CharSequence text = "Parla ora...";
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}

	@Override
	public void onBeginningOfSpeech() {
		Log.i(StaticGlobals.logTags.VOICE_REC, "onBeginningOfSpeech");
	}

	@Override
	public void onRmsChanged(float rmsdB) {
		Log.i(StaticGlobals.logTags.VOICE_REC, "onRmsChanged");
	}

	@Override
	public void onBufferReceived(byte[] buffer) {
		Log.i(StaticGlobals.logTags.VOICE_REC, "onBufferReceived " + Arrays.toString(buffer));
	}

	@Override
	public void onEndOfSpeech() {
		Log.i(StaticGlobals.logTags.VOICE_REC, "onEndOfSpeech");
		CharSequence text = "Sto analizzando i dati...";
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}

	@Override
	public void onError(int error) {
		String errorMessage = getErrorText(error);
		Log.e(StaticGlobals.logTags.VOICE_REC, "onError " + errorMessage);
	}

	@Override
	public void onResults(Bundle results) {
		Log.i(StaticGlobals.logTags.VOICE_REC, "onResults");
		ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
		match += matches != null ? matches.get(0) : "";
		CharSequence text = "Dati analizzati con successo!";
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}

	@Override
	public void onPartialResults(Bundle partialResults) {
		Log.i(StaticGlobals.logTags.VOICE_REC, "onPartialResults");
	}

	@Override
	public void onEvent(int eventType, Bundle params) {
		Log.i(StaticGlobals.logTags.VOICE_REC, "onEvent");
	}


	public static String getErrorText(int errorCode) {
		String message;
		switch (errorCode) {
			case SpeechRecognizer.ERROR_AUDIO:
				message = "Audio recording error";
				break;
			case SpeechRecognizer.ERROR_CLIENT:
				message = "Client side error";
				break;
			case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
				message = "Insufficient permissions";
				break;
			case SpeechRecognizer.ERROR_NETWORK:
				message = "Network error";
				break;
			case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
				message = "Network timeout";
				break;
			case SpeechRecognizer.ERROR_NO_MATCH:
				message = "No match";
				break;
			case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
				message = "RecognitionService busy";
				break;
			case SpeechRecognizer.ERROR_SERVER:
				message = "error from server";
				break;
			case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
				message = "No speech input";
				break;
			default:
				message = "Didn't understand, please try again.";
				break;
		}
		return message;
	}
}
