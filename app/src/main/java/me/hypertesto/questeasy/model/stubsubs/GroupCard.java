package me.hypertesto.questeasy.model.stubsubs;

import java.util.ArrayList;
import java.util.Date;

/**
 * Classe che specializza Card per documentare l'arrivo di un gruppo.
 * Contiene un ospite marcato come capo gruppo, più una lista di ospiti secondari.
 * Created by rigel on 02/05/16.
 */
public class GroupCard extends Card {
	private GroupHeadGuest capoGruppo;
	private ArrayList<GroupMemberGuest> altri;

	public GroupCard(GroupHeadGuest guest, ArrayList<GroupMemberGuest> others, Date date,
										int permanenza, boolean complete){
		this.capoGruppo = guest;
		this.altri = others;
		this.date = date;
		this.permanenza = permanenza;
		this.complete = complete;
	}

	@Override
	public String getTitle(){
		return "Nome comitiva";
	}

	@Override
	public String getDescription(){
		return "Numero membri";
	}
}