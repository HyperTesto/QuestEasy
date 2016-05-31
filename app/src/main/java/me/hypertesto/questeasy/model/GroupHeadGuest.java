package me.hypertesto.questeasy.model;

import android.graphics.Color;

import java.util.ArrayList;

/**
 * Created by rigel on 03/05/16.
 */
public class GroupHeadGuest extends MainGuest {
	public static final String CODICE = "18";

	public GroupHeadGuest(){
		this.pictures = new ArrayList<>();
	}

	@Override
	public int getColor(){
		return Color.parseColor("#01579B");
	}
}
