package me.hypertesto.questeasy.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Classe che specializza Card per documentare l'arrivo di una famiglia.
 * Contiene un ospite marcato come capo famiglia, più una lista di familiari.
 * Created by rigel on 02/05/16.
 */
public class FamilyCard extends Card {
	private FamilyHeadGuest capoFamiglia;
	private ArrayList<FamilyMemberGuest> familiari;

	public FamilyCard(FamilyHeadGuest guest, ArrayList<FamilyMemberGuest> others, Date date,
										int permanenza, boolean complete){
		this.capoFamiglia = guest;
		this.familiari = others;
		this.date = date;
		this.permanenza = permanenza;
		this.complete = complete;
	}

	@Override
	public String getTitle(){
		return "Cognome capo famiglia";
	}
}
