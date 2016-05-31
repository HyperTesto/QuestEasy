package me.hypertesto.questeasy.utils;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import me.hypertesto.questeasy.model.Place;

/**
 * Class that handle generic Place api request (states + minicipalities)
 * Created by hypertesto on 10/05/16.
 */
public class PlaceRequest implements AutoCompleteRequest {

	private String remoteAPI = "http://questura.hypertesto.me:8080/api/v1/comuni/";

	@Override
	public List<Place> find(Context context, String str) {

		List<Place> filteredPlaces = new ArrayList<>();

		try {

			JSONObject response = VolleySyncRequest.get(context, remoteAPI + str); // this will block (forever)
			JSONArray jArray = response.getJSONArray("comuni");
			System.out.println(jArray);
			for (int i=0; i < jArray.length(); i++) {
				try {
					JSONObject oneObject = jArray.getJSONObject(i);
					// Pulling items from the array

					Place item = new Place();
					item.setId(oneObject.getString("id"));
					item.setName(oneObject.getString("nome"));

					String b = oneObject.getString("isState");

					if (b.equals("0")){
						item.setState(false);
					} else {
						item.setState(true);
					}

					filteredPlaces.add(item);

					System.out.println("[DEBUG] " + oneObject.getString("nome"));

				} catch (JSONException e) {
					CharSequence text = "Error parsing JSON";
					int duration = Toast.LENGTH_SHORT;

					Toast toast = Toast.makeText(context, text, duration);
					toast.show();
					e.printStackTrace();
				}
			}

		} catch (InterruptedException e) {
			CharSequence text = "Errore di rete";
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
			e.printStackTrace();
		} catch (ExecutionException e) {
			/*CharSequence text = "Errore di esecuzione";
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(context, text, duration);
			toast.show();*/
			e.printStackTrace();
		} catch (JSONException e) {
			CharSequence text = "Error parsing JSON";
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
			e.printStackTrace();
		}

		return filteredPlaces;
	}
}
