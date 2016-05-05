package me.hypertesto.questeasy.activities;

import android.app.DatePickerDialog;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import android.support.v4.app.FragmentActivity;
import android.widget.Spinner;
import android.widget.TextView;

import me.hypertesto.questeasy.DatePickerFragment;
import me.hypertesto.questeasy.R;

public class FormGuestActivity extends AppCompatActivity {

	private TextView dataDiNascitaInput;
	//private DatePickerDialog dataDiNascitaDialog;

	//private SimpleDateFormat dateFomatter;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_form_guest);

		//dateFomatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ITALY);

		dataDiNascitaInput = (TextView)findViewById(R.id.editText_birthDate_guest_form);
		dataDiNascitaInput.setInputType(InputType.TYPE_NULL);

	}

	/*private void setDateTimeField(){
		dataDiNascitaInput.setOnClickListener(this);
		Calendar newCalendar = Calendar.getInstance();

		dataDiNascitaDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				Calendar newDate = Calendar.getInstance();
				newDate.set(year, monthOfYear, dayOfMonth);
				dataDiNascitaInput.setText(dateFomatter.format(newDate.getTime()));
			}
		},newCalendar.get(Calendar.YEAR),newCalendar.get(Calendar.MONTH),newCalendar.get
				(Calendar.DAY_OF_MONTH));
	}

	public void onClick (View view){
		if (view == dataDiNascitaInput){
			dataDiNascitaDialog.show();
			Log.e("CACCCA", "CACCA");
		}
	}
	*/
	public void showDatePickerDialog(View v){
		DialogFragment newFragment = new DatePickerFragment();
		newFragment.show(getSupportFragmentManager(), "datePicker");
	}
}
