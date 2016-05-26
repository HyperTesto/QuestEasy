package me.hypertesto.questeasy.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import me.hypertesto.questeasy.R;

/**
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
	 * Set field permanenza if is valid
	 * @param p
	 */
	public void setPermanenza(int p) {
		if (p > 0)
			permTextView.setText(String.valueOf(p));
	}
}
