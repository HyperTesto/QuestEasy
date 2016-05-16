package me.hypertesto.questeasy.ui;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import me.hypertesto.questeasy.R;

/**
 * Created by gianluke on 16/05/16.
 */
public class DocumentDataFragment extends Fragment {

	private EditText guest_documentCode;
	private EditText guest_documentNumber;
	private EditText guest_documentPlace;

	@Override
	public void onCreate (Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.activity_form_guest_part2_fragment, container, false);

		return rootView;

	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		guest_documentCode = (EditText)getView().findViewById(R.id.editText_documentoCodice_guest_form);
		guest_documentNumber = (EditText)getView().findViewById(R.id.editText_documentoNumber_guest_form);
		guest_documentPlace = (EditText)getView().findViewById(R.id.editText_documentoPlace_guest_form);
	}
}
