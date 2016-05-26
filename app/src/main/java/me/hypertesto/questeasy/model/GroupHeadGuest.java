package me.hypertesto.questeasy.model;

import android.graphics.Color;

/**
 * Created by rigel on 03/05/16.
 */
public class GroupHeadGuest extends MainGuest {
	public static final String CODICE = "18";

	public GroupHeadGuest(){}

	@Override
	public int getColor(){
		return Color.parseColor("#01579B");
	}
}
