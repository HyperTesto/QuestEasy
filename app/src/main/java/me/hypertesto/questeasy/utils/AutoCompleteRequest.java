package me.hypertesto.questeasy.utils;

import android.content.Context;

import java.util.List;

import me.hypertesto.questeasy.model.Place;

/**
 * Interface that define methods for the autocomplete feature to get Places
 * Created by hypertesto on 10/05/16.
 */
public interface AutoCompleteRequest {

	/**
	 * Perform the request give the String srt and return a list of Places
	 * @param context
	 * @param str
	 * @return
	 */
	List<Place> find(Context context, String str);
}
