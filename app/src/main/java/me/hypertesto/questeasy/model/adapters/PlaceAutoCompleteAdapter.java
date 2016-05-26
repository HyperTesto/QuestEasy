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
import me.hypertesto.questeasy.model.Place;
import me.hypertesto.questeasy.utils.AutoCompleteRequest;

/**
 * Created by hypertesto on 09/05/16.
 */
public class PlaceAutoCompleteAdapter extends BaseAdapter implements Filterable {

	private static final int MAX_RESULTS = 10;
	private Context mContext;
	private List<Place> resultList = new ArrayList<>();
	private AutoCompleteRequest api;

	public PlaceAutoCompleteAdapter(Context context, AutoCompleteRequest api) {
		mContext = context;
		this.api = api;
	}

	@Override
	public int getCount() {
		return resultList.size();
	}

	@Override
	public Place getItem(int index) {
		return resultList.get(index);
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
					List<Place> places = api.find(mContext, constraint.toString());

					// Assign the data to the FilterResults
					Collections.sort(places);
					filterResults.values = places;
					filterResults.count = places.size();
				}
				return filterResults;
			}

			@Override
			protected void publishResults(CharSequence constraint, FilterResults results) {
				if (results != null && results.count > 0) {
					resultList = (List<Place>) results.values;
					notifyDataSetChanged();
				} else {
					notifyDataSetInvalidated();
				}
			}
		};
		return filter;
	}

	/**
	 * Clear the curret objects in this adaper and sets dt.
	 * This method should be called when we enter in edit mode of a guest, and it is needed
	 * to make sure the underlying data structure is aligned whit the view. (We already return
	 * the first object in list when asked but this can cause misbehaviour if we have a Place
	 * whose name is substring of another Place)
	 * @param dt
	 */
	public void setDocType (Place p) {

		resultList.clear();
		resultList.add(p);

	}
}

