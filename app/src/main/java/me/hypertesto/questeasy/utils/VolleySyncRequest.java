package me.hypertesto.questeasy.utils;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

/**
 * Auxiliary class that performs GET and POST requests synchronously
 * Created by hypertesto on 24/05/16.
 */
public class VolleySyncRequest {

	/**
	 * Perform a synchronous HTTP GET  request
	 * @param ctx
	 * @param url
	 * @return
	 */
	public static JSONObject get (Context ctx, String url) throws ExecutionException, InterruptedException {

		RequestQueue queue = Volley.newRequestQueue(ctx);
		RequestFuture<JSONObject> future = RequestFuture.newFuture();
		JsonObjectRequest request = new JsonObjectRequest(url, null, future, future);
		queue.add(request);

		return future.get();

	}

	/**
	 * Perform a synchronous HTTP POST request with JSON object
	 * @param ctx
	 * @param url
	 * @param obj
	 * @return
	 */
	public static JSONObject post(Context ctx, String url, JSONObject obj){
		return null;
	}
}
