package me.hypertesto.questeasy.utils;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import me.hypertesto.questeasy.model.DocumentType;

/**
 * Class used to handle DocType remote request
 * server returns a JSON list that we map to DocType class
 * Created by hypertesto on 21/05/16.
 */
public class DocumentTypeRequest {

	private String remoteAPI = "https://questura.hypertesto.me/api/v1/documenti/";

	/**
	 * Returns a list of DocType that starts with the string str
	 * @param context
	 * @param str
	 * @return
	 */
	public List<DocumentType> find(Context context, String str){

		List<DocumentType> filteredPlaces = new ArrayList<>();

		try {

			JSONObject response = VolleySyncRequest.get(context, remoteAPI + str); // this will block (forever)
			JSONArray jArray = response.getJSONArray("documenti");
			System.out.println(jArray);
			for (int i=0; i < jArray.length(); i++) {
				try {
					JSONObject oneObject = jArray.getJSONObject(i);
					// Pulling items from the array

					DocumentType item = new DocumentType();
					item.setCode(oneObject.getString("codice"));
					item.setName(oneObject.getString("nome"));

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
