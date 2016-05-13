package me.hypertesto.questeasy.model;

import java.util.Date;

/**
 * Classe che specializza Card per rappresentare l'arrivo di un ospite singolo.
 * Created by rigel on 02/05/16.
 */
public class SingleGuestCard extends Card {
	private SingleGuest guest;

	public SingleGuestCard(SingleGuest guest, Date date, int permanenza){
		this.guest = guest;
		this.date = date;
		this.permanenza = permanenza;
	}

	@Override
	public boolean isComplete() {
		return this.guest.isComplete();
	}

	public SingleGuest getGuest() {
		return guest;
	}

	@Override
	public String getTitle(){
		return "Nome dell'ospite";
	}

	@Override
	public String getInitialLetter() {
		return guest.getName().substring(0,1);
	}
}
