package me.hypertesto.questeasy.model;

import android.graphics.Color;

import java.util.ArrayList;

/**
 * Created by rigel on 03/05/16.
 */
public class SingleGuest extends MainGuest {
	public static final String CODICE = "16";

	public SingleGuest(){
		this.pictures = new ArrayList<>();
	}

	@Override
	public int getColor() {
		return Color.parseColor("#D50000");
	}
}
