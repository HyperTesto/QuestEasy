package me.hypertesto.questeasy.model;

import java.util.Date;

/**
 * Classe che specializza Card per rappresentare l'arrivo di un ospite singolo.
 * Created by rigel on 02/05/16.
 */
public class SingleGuestCard extends Card {
	private SingleGuest guest;

	public SingleGuestCard(){}

	public SingleGuestCard(SingleGuest guest, Date date, int permanenza){
		this.guest = guest;
		this.date = date;
		this.permanenza = permanenza;
	}

	public void setGuest(SingleGuest guest) {
		this.guest = guest;
	}

	@Override
	public boolean isComplete() {
		if (this.guest == null){
			return false;
		}

		return this.guest.isComplete();
	}

	public SingleGuest getGuest() {
		return guest;
	}

	@Override
	public String getTitle(){
		return String.format("%s %s", this.guest.getName(), this.guest.getSurname());
	}

	@Override
	public String getInitialLetter() {
		return guest.getName().substring(0,1);
	}
}
