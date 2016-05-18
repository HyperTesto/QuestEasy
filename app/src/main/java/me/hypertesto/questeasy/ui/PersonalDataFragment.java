package me.hypertesto.questeasy.ui;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import me.hypertesto.questeasy.R;
import me.hypertesto.questeasy.model.adapters.PlaceAutoCompleteAdapter;
import me.hypertesto.questeasy.utils.CitizenshipRequest;
import me.hypertesto.questeasy.utils.PlaceRequest;

/**
 * Created by gianluke on 16/05/16.
 */
public class PersonalDataFragment extends Fragment {

	private DelayAutoCompleteTextView guestBirthPlace;
	private DelayAutoCompleteTextView guest_citizenship;
	private EditText guest_name;
	private EditText guest_surname;
	private RadioButton guest_sexMan;
	private RadioButton guest_sexWoman;
	private TextView guest_dateBirth;

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.activity_form_guest_part1_fragment, container, false);

		return rootView;

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		guest_dateBirth = (TextView)getView().findViewById(R.id.editText_birthDate_guest_form);
		guest_dateBirth.setInputType(InputType.TYPE_NULL);

		guest_name = (EditText)getView().findViewById(R.id.editText_name_guest_form);
		guest_surname = (EditText)getView().findViewById(R.id.editText_surname_guest_form);
		guest_dateBirth = (TextView)getView().findViewById(R.id.editText_birthDate_guest_form);
		guest_sexMan = (RadioButton)getView().findViewById(R.id.sex_man);
		guest_sexWoman = (RadioButton)getView().findViewById(R.id.sex_woman);

		//Autocomplete luogo nascita
		guestBirthPlace = (DelayAutoCompleteTextView) getView().findViewById(R.id.editText_luogoN_guest_form);
		guestBirthPlace.setThreshold(1);
		PlaceAutoCompleteAdapter birthPlaceAdapter = new PlaceAutoCompleteAdapter(getActivity(), new PlaceRequest());
		guestBirthPlace.setAdapter(birthPlaceAdapter); // 'this' is Activity instance
		guestBirthPlace.setLoadingIndicator(
				(android.widget.ProgressBar) getView().findViewById(R.id.pb_loading_indicator_luogo));
		guestBirthPlace.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
				String place = adapterView.getItemAtPosition(position).toString();
				guestBirthPlace.setText(place);
			}
		});

		//Autocomplete cittadinanza
		guest_citizenship = (DelayAutoCompleteTextView) getView().findViewById(R.id.editText_cittadinanza_guest_form);
		guest_citizenship.setThreshold(1);
		PlaceAutoCompleteAdapter citizenshipAdapter = new PlaceAutoCompleteAdapter(getActivity(), new CitizenshipRequest());
		guest_citizenship.setAdapter(citizenshipAdapter); // 'this' is Activity instance
		guest_citizenship.setLoadingIndicator(
				(android.widget.ProgressBar) getView().findViewById(R.id.pb_loading_indicator));
		guest_citizenship.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
				String place = adapterView.getItemAtPosition(position).toString();
				guest_citizenship.setText(place);
			}
		});
	}


}
