package me.hypertesto.questeasy;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by gianluke on 05/05/16.
 */
public  class DatePickerFragment extends DialogFragment implements
		DatePickerDialog.OnDateSetListener {

	@Override
	public Dialog onCreateDialog(Bundle savedInstance){

		// Use the current date as the default date in the picker
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);

		// Create a new instance of DatePickerDialog and return it
		return new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, this, year, month, day);
	}


	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		//Do something with the date that user inserts
	}
}
