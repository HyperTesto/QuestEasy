package me.hypertesto.questeasy.model;

import java.util.Date;

/**
 * Classe che specializza Card per rappresentare l'arrivo di un ospite singolo.
 * Created by rigel on 02/05/16.
 */
public class SingleGuestCard extends Card {
	private SingleGuest guest;

	public SingleGuestCard(SingleGuest guest, Date date, int permanenza, boolean complete){
		this.guest = guest;
		this.date = date;
		this.permanenza = permanenza;
		this.complete = complete;
	}

	@Override
	public String getTitle(){
		return "Nome dell'ospite";
	}
}
