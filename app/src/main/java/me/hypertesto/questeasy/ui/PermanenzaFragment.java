package me.hypertesto.questeasy.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

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
}
