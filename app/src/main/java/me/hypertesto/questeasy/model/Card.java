package me.hypertesto.questeasy.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Questa è la classe astratta della schedina generica, che documenta un singolo arrivo,
 * e viene specializzata a seconda della tipologia.
 * Potrebbe ritornare qualche sorta di nome/titolo + descrizione.
 * Created by rigel on 02/05/16.
 */
public abstract class Card implements Serializable{
	protected Date date;
	protected int permanenza;

	public static final class type {
		public static final String SINGLE_GUEST_CARD = "SINGLE_GUEST_CARD";
		public static final String FAMILY_CARD = "FAMILY_CARD";
		public static final String GROUP_CARD = "GROUP_CARD";
	}

	public abstract boolean isComplete();

	public void setDate(Date date) {
		this.date = date;
	}

	public void setPermanenza(int permanenza) {
		this.permanenza = permanenza;
	}

	public Date getDate() {
		return date;
	}

	public int getPermanenza() {
		return permanenza;
	}

	public abstract String getTitle();

	public String getDescription(){
		return "Permanenza: " + this.permanenza + " giorni";
	}

	public abstract String getInitialLetter();
}
