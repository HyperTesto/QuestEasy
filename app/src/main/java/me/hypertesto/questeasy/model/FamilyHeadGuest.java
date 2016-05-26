package me.hypertesto.questeasy.model;

import android.graphics.Color;

/**
 * Created by rigel on 03/05/16.
 */
public class FamilyHeadGuest extends MainGuest {
	public static final String CODICE = "17";

	public FamilyHeadGuest(){}

	@Override
	public int getColor(){
		return Color.parseColor("#FF6F00");
	}
}
