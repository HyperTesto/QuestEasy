package me.hypertesto.questeasy.utils;

import android.content.Context;

import java.util.List;

/**
 * Created by hypertesto on 10/05/16.
 */
public interface AutoCompleteRequest {
	List<String> find(Context context, String str);
}
