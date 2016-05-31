package me.hypertesto.questeasy.model;

import android.graphics.Color;

import java.util.ArrayList;

/**
 * Created by rigel on 03/05/16.
 */
public class FamilyHeadGuest extends MainGuest {
	public static final String CODICE = "17";

	public FamilyHeadGuest(){
		this.pictures = new ArrayList<>();
	}

	@Override
	public int getColor(){
		return Color.parseColor("#FF6F00");
	}
}
