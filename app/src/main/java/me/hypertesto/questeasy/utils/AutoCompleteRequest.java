package me.hypertesto.questeasy.utils;

import android.content.Context;

import java.util.List;

import me.hypertesto.questeasy.model.Place;

/**
 * Created by hypertesto on 10/05/16.
 */
public interface AutoCompleteRequest {
	List<Place> find(Context context, String str);
}
