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

import me.hypertesto.questeasy.model.DocumentType;
import me.hypertesto.questeasy.model.Place;

/**
 * Created by hypertesto on 21/05/16.
 */
public class DocumentTypeRequest {

	private String remoteAPI = "https://questura.hypertesto.me/api/v1/comuni/";

	public List<DocumentType> find(Context context, String str){

		RequestQueue queue = Volley.newRequestQueue(context);
		String url = remoteAPI + str;
		List<DocumentType> filteredPlaces = new ArrayList<>();
		RequestFuture<JSONObject> future = RequestFuture.newFuture();
		JsonObjectRequest request = new JsonObjectRequest(url, null, future, future);
		queue.add(request);

		try {
			JSONObject response = future.get(); // this will block (forever)
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
