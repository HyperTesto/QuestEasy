package me.hypertesto.questeasy.model;

import android.graphics.Color;

/**
 * Created by rigel on 03/05/16.
 */
public class SingleGuest extends MainGuest {
	public static final String CODICE = "16";

	public SingleGuest(){}

	@Override
	public int getColor() {
		return Color.parseColor("#D50000");
	}
}
