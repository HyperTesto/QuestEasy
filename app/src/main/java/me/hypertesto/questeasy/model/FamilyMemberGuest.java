package me.hypertesto.questeasy.model;

import android.graphics.Color;

import java.util.ArrayList;

/**
 * Classe che implementa l'utente membro di una famiglia di cui non è capo.
 * Created by rigel on 02/05/16.
 */
public class FamilyMemberGuest extends SecondaryGuest {
	public static final String CODICE = "19";

	public FamilyMemberGuest(){
		this.pictures = new ArrayList<>();
	}

	@Override
	public int getColor() {
		return Color.parseColor("#FFC107");
	}
}
