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
import me.hypertesto.questeasy.model.DocumentType;
import me.hypertesto.questeasy.model.Documento;
import me.hypertesto.questeasy.model.Place;
import me.hypertesto.questeasy.model.adapters.DocumentTypeAdapter;
import me.hypertesto.questeasy.model.adapters.PlaceAutoCompleteAdapter;
import me.hypertesto.questeasy.utils.DocumentTypeRequest;
import me.hypertesto.questeasy.utils.PlaceRequest;

/**
 * Fragment that contains document fields (HeadGuests only)
 * Created by gianluke on 16/05/16.
 * @edited by hypertesto
 */
public class DocumentDataFragment extends Fragment {

	private DelayAutoCompleteTextView guest_documentType;
	private EditText guest_documentNumber;
	private DelayAutoCompleteTextView guest_documentPlace;
	PlaceAutoCompleteAdapter releasePlaceAdapter;
	DocumentTypeAdapter docTypeAdapter;

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

		guest_documentType = (DelayAutoCompleteTextView) getView().findViewById(R.id.editText_documentoCodice_guest_form);
		guest_documentType.setThreshold(1);
		docTypeAdapter = new DocumentTypeAdapter(getActivity(), new DocumentTypeRequest());
		guest_documentType.setAdapter(docTypeAdapter);
		guest_documentType.setLoadingIndicator(
				(ProgressBar) getView().findViewById(R.id.pb_loading_indicator_doc_type));
		guest_documentType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
				String type = adapterView.getItemAtPosition(position).toString();
				guest_documentType.setText(type);
			}
		});

		guest_documentNumber = (EditText)getView().findViewById(R.id.editText_documentoNumber_guest_form);

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

	/**
	 * Return documen number (aka code or id)
	 * @return
	 */
	public String getDocumentNumber(){
		return guest_documentNumber.getText().toString();
	}

	/**
	 * Return a correcly formatted document type
	 * If documentType was not filled in the form an empty one is returned to prevent null pointers
	 * @return
	 */
	public DocumentType getDocumentType(){
		if (docTypeAdapter.getCount() > 0) {
			return docTypeAdapter.getItem(0);
		} else {
			return new DocumentType("", "");
		}

	}

	/**
	 * Retun document release Place. If Not set an empy one is returned.
	 * NOTE: it returns the item at position 0 in the adapter. This cover 99% of the cases.
	 * 		If a Place is substring of another this could cause misbehaviour
	 * @return
	 */
	public Place getDocumentReleasePlace(){
		if (releasePlaceAdapter.getCount() > 0) {
			return releasePlaceAdapter.getItem(0);
		} else {
			return new Place();
		}

	}

	/**
	 * Set the document provided in the data fields
	 * Empty fields are skipped
	 * @param doc
	 */
	public void setDocument (Documento doc) {

		if (doc != null) {

			DocumentType dt = doc.getDocType();
			if ( dt !=null && !dt.getName().equals("")){
				guest_documentType.setText(dt.getName());
			}

			if (doc.getCodice() != null && !doc.getCodice().equals("")) {
				guest_documentNumber.setText(doc.getCodice());
			}

			Place p = doc.getLuogoRilascio();
			if ( p != null && !p.getName().equals("")){
				guest_documentPlace.setText(p.getName());
			}
		}

	}
}
