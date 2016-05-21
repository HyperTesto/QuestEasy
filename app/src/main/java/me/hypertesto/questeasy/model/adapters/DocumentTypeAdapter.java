package me.hypertesto.questeasy.model.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.hypertesto.questeasy.R;
import me.hypertesto.questeasy.model.DocumentType;
import me.hypertesto.questeasy.utils.DocumentTypeRequest;

/**
 * Created by hypertesto on 21/05/16.
 */
public class DocumentTypeAdapter extends BaseAdapter implements Filterable {

	private static final int MAX_RESULTS = 10;
	private Context mContext;
	private List<DocumentType> resultList = new ArrayList<>();
	private DocumentTypeRequest api;

	public DocumentTypeAdapter(Context context, DocumentTypeRequest api) {
		mContext = context;
		this.api = api;
	}

	@Override
	public int getCount() {
		return resultList.size();
	}

	@Override
	public DocumentType getItem(int position) {
		return resultList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.simple_dropdown_item, parent, false);
		}
		((TextView) convertView.findViewById(R.id.autocompleted_text)).setText(getItem(position).getName());
		return convertView;
	}

	@Override
	public Filter getFilter() {
		Filter filter = new Filter() {
			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				Filter.FilterResults filterResults = new FilterResults();
				if (constraint != null) {
					List<DocumentType> docTypes = api.find(mContext, constraint.toString());

					// Assign the data to the FilterResults
					Collections.sort(docTypes);
					filterResults.values = docTypes;
					filterResults.count = docTypes.size();
				}
				return filterResults;
			}

			@Override
			protected void publishResults(CharSequence constraint, FilterResults results) {
				if (results != null && results.count > 0) {
					resultList = (List<DocumentType>) results.values;
					notifyDataSetChanged();
				} else {
					notifyDataSetInvalidated();
				}
			}
		};
		return filter;
	}

}
