package me.hypertesto.questeasy.model.stubsubs;

import java.util.ArrayList;

/**
 * Classe che specializza Card per documentare l'arrivo di un gruppo.
 * Contiene un ospite marcato come capo gruppo, più una lista di ospiti secondari.
 * Created by rigel on 02/05/16.
 */
public class GroupCard extends Card {
	private GroupHeadGuest capoGruppo;
	private ArrayList<GroupMemberGuest> altri;

	@Override
	public String getTitle(){
		return "Nome comitiva";
	}

	@Override
	public String getDescription(){
		return "Numero membri";
	}
}