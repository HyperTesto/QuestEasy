package me.hypertesto.questeasy.model;

import android.graphics.Color;

import java.util.ArrayList;

/**
 * Classe che rappresenta l'ospite membro di un gruppo di cui non è a capo.
 * Created by rigel on 02/05/16.
 */
public class GroupMemberGuest extends SecondaryGuest {
	public static final String CODICE = "20";

	public GroupMemberGuest(){
		this.pictures = new ArrayList<>();
	}

	@Override
	public int getColor() {
		return Color.parseColor("#03A9F4");
	}
}
