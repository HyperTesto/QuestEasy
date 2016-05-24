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

	@Override
	public boolean isComplete() {
		if (this.guest == null){
			return false;
		}

		return this.guest.isComplete();
	}

	@Override
	public boolean equals(Object o){
		if (o != null){
			if (o instanceof SingleGuestCard){
				SingleGuestCard sgco = (SingleGuestCard) o;
				return (this.guest.equals(sgco.getGuest()) &&
								this.permanenza == sgco.getPermanenza());
			}
		}

		return false;
	}

	public void setGuest(SingleGuest guest) {
		this.guest = guest;
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
		if (guest.getName().length() > 0)
			return guest.getName().substring(0,1);
		return "";
	}
}
