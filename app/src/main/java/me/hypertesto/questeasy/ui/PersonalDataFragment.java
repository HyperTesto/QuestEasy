package me.hypertesto.questeasy.ui;


import android.app.Fragment;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import me.hypertesto.questeasy.R;
import me.hypertesto.questeasy.model.Guest;
import me.hypertesto.questeasy.model.Place;
import me.hypertesto.questeasy.model.adapters.PlaceAutoCompleteAdapter;
import me.hypertesto.questeasy.utils.CitizenshipRequest;
import me.hypertesto.questeasy.utils.DateUtils;
import me.hypertesto.questeasy.utils.PlaceRequest;
import me.hypertesto.questeasy.utils.WordsCapitalizer;

/**
 * Fragment that contains the form of personal data (needed by every type of guests)
 * Created by gianluke on 16/05/16.
 * @edited by hypertesto
 */
public class PersonalDataFragment extends Fragment {

	private DelayAutoCompleteTextView guestBirthPlace;
	private DelayAutoCompleteTextView guest_citizenship;
	private EditText guest_name;
	private EditText guest_surname;
	private RadioGroup guest_gender;
	private TextView guest_dateBirth;
	private PlaceAutoCompleteAdapter birthPlaceAdapter;
	private PlaceAutoCompleteAdapter citizenshipAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.activity_form_guest_fragment_data, container, false);

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
		guest_gender = (RadioGroup) getView().findViewById(R.id.gender_group);

		//Autocomplete luogo nascita
		guestBirthPlace = (DelayAutoCompleteTextView) getView().findViewById(R.id.editText_luogoN_guest_form);
		guestBirthPlace.setThreshold(1);
		birthPlaceAdapter = new PlaceAutoCompleteAdapter(getActivity(), new PlaceRequest());
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
		citizenshipAdapter = new PlaceAutoCompleteAdapter(getActivity(), new CitizenshipRequest());
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

	/**
	 * Return a correctly capitalized guest name
	 * @return
	 */
	public String getGuestName(){

		return WordsCapitalizer.capitalizeEveryWord(guest_name.getText().toString(),Locale.ITALY);

	}

	/**
	 * Return a correctly capitalized guest surname
	 * @return
	 */
	public String getSurname(){

		return WordsCapitalizer.capitalizeEveryWord(guest_surname.getText().toString(),Locale.ITALY);

	}

	/**
	 * Return the date of birth parsed from the form
	 * @return
	 * @throws ParseException
	 */
	public Date getDateofBirth() throws ParseException {
		return DateUtils.parse(guest_dateBirth.getText().toString());

	}

	/**
	 * Return the string representing sex from the radioGroup
	 * If for some bug radioGroup is unset we return an empty string to avoid conflicts.
	 * @return
	 */
	public String getSex(){

		int selected = guest_gender.getCheckedRadioButtonId();
		if (selected == R.id.sex_man){
			return "M";
		} else if (selected == R.id.sex_woman) {
			return "F";
		} else {
			return "";
		}
	}

	/**
	 * Returns the birthPlace is was set in the form, otherwise we return a new empty place in
	 * order to avoid null pointers.
	 * NOTE: this always return the Place at position 0 in the adapter, this cover 99% of the cases
	 * but it could cause misbehaviour if we have place whom name is substring of another.
	 * @return
	 */
	public Place getBirthPlace(){
		if (birthPlaceAdapter.getCount() > 0) {
			return birthPlaceAdapter.getItem(0);
		} else {
			return new Place(); //TODO: should it return an initzialized empty place?
		}

	}

	/**
	 * Returns the citizenship is was set in the form, otherwise we return a new empty place in
	 * order to avoid null pointers.
	 * NOTE: this always return the Place at position 0 in the adapter, this cover 99% of the cases
	 * but it could cause misbehaviour if we have place whom name is substring of another.
	 * @return
	 */
	public Place getCittadinanza(){
		if (citizenshipAdapter.getCount() > 0) {
			return citizenshipAdapter.getItem(0);
		} else {
			return new Place();
		}

	}

	/**
	 * Method that automatically fills the form data for the guest provided
	 * NOTE: null or empty fields are skipped.
	 * @param guest
	 */
	public void setGuest (Guest guest) {

		if (guest != null) {

			if (!guest.getName().equals("")){
				guest_name.setText(WordsCapitalizer.capitalizeEveryWord(guest.getName(),Locale.ITALY));
			}

			if (!guest.getSurname().equals("")){
				guest_surname.setText(WordsCapitalizer.capitalizeEveryWord(guest.getSurname(),Locale.ITALY));
			}

			Date d = guest.getBirthDate();
			if( d != null ){
				guest_dateBirth.setText(DateUtils.format(d));
			}

			//If no sex, "M" is the default one defined by layout
			if (guest.getSex().equals("M")){
				guest_gender.check(R.id.sex_man);
			} else if (guest.getSex().equals("F")){
				guest_gender.check(R.id.sex_woman);
			}

			Place placeOfBirth = guest.getPlaceOfBirth();
			if( placeOfBirth != null && !placeOfBirth.getName().equals("")){
				guestBirthPlace.setText(placeOfBirth.getName());
			}

			Place citizenship = guest.getCittadinanza();
			if (citizenship != null && !citizenship.getName().equals("")){
				guest_citizenship.setText(citizenship.getName());
			}
		}

	}

}
