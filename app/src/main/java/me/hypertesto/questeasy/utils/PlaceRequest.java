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

/**
 * Created by hypertesto on 10/05/16.
 */
public class PlaceRequest implements AutoCompleteRequest {

	private String remoteAPI = "https://questura.hypertesto.me/api/v1/comuni/";

	@Override
	public List<String> find(Context context, String str) {
		RequestQueue queue = Volley.newRequestQueue(context);
		String url = remoteAPI + str;
		List<String> filteredPlaces = new ArrayList<>();
		RequestFuture<JSONObject> future = RequestFuture.newFuture();
		JsonObjectRequest request = new JsonObjectRequest(url, null, future, future);
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
					CharSequence text = "Error parsing JSON";
					int duration = Toast.LENGTH_SHORT;

					Toast toast = Toast.makeText(context, text, duration);
					toast.show();
					e.printStackTrace();
				}
			}

		} catch (InterruptedException | ExecutionException | JSONException e) {
			CharSequence text = "Errore di rete";
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
			e.printStackTrace();
		}

		return filteredPlaces;
	}
}
