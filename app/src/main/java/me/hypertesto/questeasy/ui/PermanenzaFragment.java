package me.hypertesto.questeasy.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import me.hypertesto.questeasy.R;

/**
 * Fragment that contains the form part to set staying (HeadGuests only)
 * Created by gianluke on 17/05/16.
 */
public class PermanenzaFragment extends Fragment {

	EditText permTextView;

	@Override
	public void onCreate (Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.activity_form_guest_fragment_permanenza, container, false);

		return rootView;

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		permTextView = (EditText) getView().findViewById(R.id.input_permanenza);

		permTextView.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (permTextView.getText().length() == 2){
					String permanenzaString = permTextView.getText().toString();
					int number = Integer.parseInt(permanenzaString);
					if (number > 30) {
						Toast.makeText(getActivity(), R.string.errorPermanenza, Toast.LENGTH_LONG).show();
						StringBuilder sb = new StringBuilder(permanenzaString);
						permTextView.setText(sb.deleteCharAt(1).toString());
					}

				}
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});



		restorePreviousValues(savedInstanceState);
	}

	/**
	 * Return and int representing guest days of staying.
	 * If Parsing cause errors or field left empty we return -1
	 * @return
	 */
	public int getPermanenza(){

		int i;

		try {
			i  = Integer.parseInt(permTextView.getText().toString());
		} catch (Exception e){
			e.printStackTrace();
			i = -1;
		}

		return i;

	}

	/**
	 * Set field permanenza if is valid, if not, skip
	 * @param p
	 */
	public void setPermanenza(int p) {
		if (p > 0)
			permTextView.setText(String.valueOf(p));
	}

	/*
		Save values of the PermanenzaFragment's fields
	 */
	@Override
	public void onSaveInstanceState (Bundle instanceSaveState){
		super.onSaveInstanceState(instanceSaveState);

		instanceSaveState.putString("guestPermanenza", permTextView.getText().toString());
	}

	/*
		Restore values of the PermanenzaFragment's fields
	 */
	public void restorePreviousValues(Bundle savedInstanceState){
		if (savedInstanceState != null) {
			permTextView.setText(savedInstanceState.getString("guestPermanenza"));
		}
	}
}
