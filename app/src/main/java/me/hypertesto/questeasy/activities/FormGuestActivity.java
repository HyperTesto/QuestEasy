package me.hypertesto.questeasy.activities;

import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import android.support.v4.app.FragmentActivity;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import me.hypertesto.questeasy.DatePickerFragment;
import me.hypertesto.questeasy.R;

public class FormGuestActivity extends AppCompatActivity {


	private EditText guest_name;
	private EditText guest_surname;
	private RadioButton guest_sexMan;
	private RadioButton guest_sexWoman;
	private TextView guest_dateBirth;
	private EditText guest_placeBirth;
	private EditText guest_citizenship;
	private EditText guest_documentCode;
	private EditText guest_documentNumber;
	private EditText guest_documentPlace;
	private FloatingActionButton button_voice_form;
	private FloatingActionButton button_photo;


	//REQ_CODE for voice
	private final int REQ_CODE_SPEECH_INPUT = 100;


	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_form_guest);

		//dateFomatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ITALY);

		guest_dateBirth = (TextView)findViewById(R.id.editText_birthDate_guest_form);
		guest_dateBirth.setInputType(InputType.TYPE_NULL);

		guest_name = (EditText)findViewById(R.id.editText_name_guest_form);
		guest_surname = (EditText)findViewById(R.id.editText_surname_guest_form);
		guest_dateBirth = (TextView)findViewById(R.id.editText_birthDate_guest_form);
		guest_sexMan = (RadioButton)findViewById(R.id.sex_man);
		guest_sexWoman = (RadioButton)findViewById(R.id.sex_woman);
		guest_placeBirth = (EditText)findViewById(R.id.editText_luogoN_guest_form);
		guest_citizenship = (EditText)findViewById(R.id.editText_cittadinanza_guest_form);
		guest_documentCode = (EditText)findViewById(R.id.editText_documentoCodice_guest_form);
		guest_documentNumber = (EditText)findViewById(R.id.editText_documentoNumber_guest_form);
		guest_documentPlace = (EditText)findViewById(R.id.editText_documentoPlace_guest_form);

		button_photo = (FloatingActionButton)findViewById(R.id.camera_guest_form);
		button_voice_form = (FloatingActionButton)findViewById(R.id.voice_guest_form);

		button_photo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//TransitionManager.beginDelayedTransition(sceneRoot);
			}
		});
		button_voice_form.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				promptSpeechInput();
			}
		});

	}


	//showing date
	public void showDatePickerDialog(View v){
		DialogFragment newFragment = new DatePickerFragment();
		newFragment.show(getSupportFragmentManager(), "datePicker");
	}

	//manage data voice insertion

	/**
	 * Showing google speech input dialog
	 * */
	private void promptSpeechInput(){
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ITALY);
		intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.speech_prompt));
		try{
			startActivityForResult(intent,REQ_CODE_SPEECH_INPUT);
		} catch (ActivityNotFoundException a){
			Toast.makeText(getApplicationContext(), getString(R.string.speech_not_supported), Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * Receiving speech input
	 * */
	@Override
	protected void onActivityResult(int requestCode,int resultCode,Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		switch(requestCode){
			case REQ_CODE_SPEECH_INPUT:{
				if (resultCode == RESULT_OK && null!=data){
					ArrayList<String> result = data.getStringArrayListExtra
							(RecognizerIntent.EXTRA_RESULTS);
					ParsingAndAssignData(result);

				}
			}
			break;
			default:break;
		}
	}

	private void ParsingAndAssignData(ArrayList<String>result){
		String [] data = result.get(0).split(" ");
		for (int i = 0; i < data.length; i++){
			String wordSupport = data[i].toUpperCase();
			System.out.println("***Value " + wordSupport);
			switch (wordSupport){
				//manage more than one name
				case "NOME" :
					guest_name.setText(data[i+1]);
					break;
				case "COGNOME" :
					guest_surname.setText(data[i+1]);
					break;
				case "CITTADINANZA" :
					guest_citizenship.setText(data[i+1]);
					break;
				case "SESSO" :
					String sex = data[i+1].toUpperCase();
					if (sex.equals("UOMO")){
						guest_sexMan.setChecked(true);
					}else if (sex.equals("DONNA")){
						guest_sexWoman.setChecked(true);
					}
					//guest_surname.setText(result.get(i+2).toString());
					break;
				case "NASCITA" :
					guest_placeBirth.setText(data[i+1]);
					break;
				case "CODICE" :
					guest_documentCode.setText(data[i+1]);
					break;
				case "NUMERO" :
					if (data[i+1].toUpperCase().equals("DOCUMENTO"))
						guest_documentNumber.setText(data[i+2]);
					break;
				case "RILASCIO" :
					guest_documentPlace.setText(data[i+1]);
					break;
				default : break;
			}


		}

	}

}
