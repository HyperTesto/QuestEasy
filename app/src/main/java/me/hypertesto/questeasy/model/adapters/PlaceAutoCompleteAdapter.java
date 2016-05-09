package me.hypertesto.questeasy.model.adapters;

import android.app.DownloadManager;
import android.app.admin.SystemUpdatePolicy;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import me.hypertesto.questeasy.R;

/**
 * Created by hypertesto on 09/05/16.
 */
public class PlaceAutoCompleteAdapter extends BaseAdapter implements Filterable {

	private static final int MAX_RESULTS = 10;
	private Context mContext;
	//private List<Book> resultList = new ArrayList<Book>();
	private List<String> resultList = new ArrayList<>();

	public PlaceAutoCompleteAdapter(Context context) {
		mContext = context;
	}

	@Override
	public int getCount() {
		return resultList.size();
	}

	@Override
	public String getItem(int index) {
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
		((TextView) convertView.findViewById(R.id.autocompleted_text)).setText(getItem(position));
		((TextView) convertView.findViewById(R.id.text2)).setText("filler");
		return convertView;
	}

	@Override
	public Filter getFilter() {
		Filter filter = new Filter() {
			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				Filter.FilterResults filterResults = new FilterResults();
				if (constraint != null) {
					List<String> places = findPlace(mContext, constraint.toString());

					// Assign the data to the FilterResults
					filterResults.values = places;
					filterResults.count = places.size();
				}
				return filterResults;
			}

			@Override
			protected void publishResults(CharSequence constraint, FilterResults results) {
				if (results != null && results.count > 0) {
					resultList = (List<String>) results.values;
					notifyDataSetChanged();
				} else {
					notifyDataSetInvalidated();
				}
			}
		};
		return filter;
	}

	/**
	 * Returns a search result for the given book title.
	 */
	private List<String> findPlace(Context context, String place) {

		RequestQueue queue = Volley.newRequestQueue(context);
		String url ="https://questura.hypertesto.me/api/v1/comuni/" + place;
		List<String> filteredPlaces = new ArrayList<>();
		RequestFuture<JSONObject> future = RequestFuture.newFuture();
		JsonObjectRequest request = new JsonObjectRequest(url, null, future, future);

		System.out.println("[DEBUG] Sono qui");
		queue.add(request);

		try {
			JSONObject response = future.get(); // this will block (forever)
			JSONArray jArray = response.getJSONArray("comuni");
			System.out.println(jArray);
			for (int i=0; i < jArray.length(); i++) {
				try {
					JSONObject oneObject = jArray.getJSONObject(i);
					// Pulling items from the array
					filteredPlaces.add(oneObject.getString("nome"));
					System.out.println("[DEBUG] " + oneObject.getString("nome"));

				} catch (JSONException e) {
					// Oops
				}
			}
		} catch (InterruptedException e) {
			// exception handling
		} catch (ExecutionException e) {
			// exception handling
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return filteredPlaces;
	}
}

