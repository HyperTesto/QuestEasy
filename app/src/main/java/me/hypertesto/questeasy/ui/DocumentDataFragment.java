package me.hypertesto.questeasy.ui;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ProgressBar;

import me.hypertesto.questeasy.R;
import me.hypertesto.questeasy.model.Place;
import me.hypertesto.questeasy.model.adapters.PlaceAutoCompleteAdapter;
import me.hypertesto.questeasy.utils.PlaceRequest;

/**
 * Created by gianluke on 16/05/16.
 */
public class DocumentDataFragment extends Fragment {

	private EditText guest_documentCode;
	private EditText guest_documentNumber;
	private DelayAutoCompleteTextView guest_documentPlace;
	PlaceAutoCompleteAdapter releasePlaceAdapter;

	@Override
	public void onCreate (Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.activity_form_guest_fragment_document, container, false);

		return rootView;

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		guest_documentCode = (EditText)getView().findViewById(R.id.editText_documentoCodice_guest_form);
		guest_documentNumber = (EditText)getView().findViewById(R.id.editText_documentoNumber_guest_form);
		//guest_documentPlace = (EditText)getView().findViewById(R.id.editText_documentoPlace_guest_form);
		guest_documentPlace = (DelayAutoCompleteTextView) getView().findViewById(R.id.editText_documentoPlace_guest_form);
		guest_documentPlace.setThreshold(1);
		releasePlaceAdapter = new PlaceAutoCompleteAdapter(getActivity(), new PlaceRequest());
		guest_documentPlace.setAdapter(releasePlaceAdapter); // 'this' is Activity instance
		guest_documentPlace.setLoadingIndicator(
				(ProgressBar) getView().findViewById(R.id.pb_loading_indicator_doc));
		guest_documentPlace.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
				String place = adapterView.getItemAtPosition(position).toString();
				guest_documentPlace.setText(place);
			}
		});
	}

	public String getDocumentNumber(){
		return guest_documentNumber.toString();
	}

	public Place getDocumentType(){
		return null; //TODO: we need a wrap class for name and id
	}

	public Place getDocumentReleasePlace(){
		return releasePlaceAdapter.getItem(0);
	}
}
