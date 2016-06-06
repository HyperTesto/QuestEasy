package me.hypertesto.questeasy.utils;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import me.hypertesto.questeasy.R;
import me.hypertesto.questeasy.model.Place;

/**
 * Class that handle API request for citizenship
 * Created by hypertesto on 10/05/16.
 */
public class CitizenshipRequest implements AutoCompleteRequest{

	private String remoteAPI = "http://questura.hypertesto.me:8080/api/v1/cittadinanza/";

	@Override
	public List<Place> find(Context context, String str) {

		List<Place> filteredPlaces = new ArrayList<>();

		try {

			JSONObject response = VolleySyncRequest.get(context, remoteAPI + str); // this will block (forever)
			JSONArray jArray = response.getJSONArray("stati");
			System.out.println(jArray);
			for (int i=0; i < jArray.length(); i++) {
				try {
					JSONObject oneObject = jArray.getJSONObject(i);
					// Pulling items from the array
					Place item = new Place();
					item.setState(true);
					item.setId(oneObject.getString("id"));
					item.setName(oneObject.getString("nome"));

					filteredPlaces.add(item);

				} catch (JSONException e) {
					CharSequence text = context.getString(R.string.citizenship_json_exception);
					int duration = Toast.LENGTH_SHORT;

					Toast toast = Toast.makeText(context, text, duration);
					toast.show();
					e.printStackTrace();
				}
			}

		} catch (InterruptedException e) {
			CharSequence text = context.getString(R.string.remote_api_net_error);
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			CharSequence text = context.getString(R.string.citizenship_json_exception);
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
			e.printStackTrace();
		}

		return filteredPlaces;
	}

}
