package me.hypertesto.questeasy.model.stubsubs;

import java.util.ArrayList;

/**
 * Classe che specializza Card per documentare l'arrivo di una famiglia.
 * Contiene un ospite marcato come capo famiglia, più una lista di familiari.
 * Created by rigel on 02/05/16.
 */
public class FamilyCard extends Card {
	private FamilyHeadGuest capoFamiglia;
	private ArrayList<FamilyMemberGuest> familiari;

	@Override
	public String getTitle(){
		return "Cognome capo famiglia";
	}

	@Override
	public String getDescription(){
		return "Numero membri";
	}
}
