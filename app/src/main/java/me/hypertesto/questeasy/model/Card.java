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
	protected boolean complete;

	public void setDate(Date date) {
		this.date = date;
	}

	public void setPermanenza(int permanenza) {
		this.permanenza = permanenza;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}

	public Date getDate() {
		return date;
	}

	public int getPermanenza() {
		return permanenza;
	}

	public boolean isComplete(){
		return this.complete;
	}

	public abstract String getTitle();

	public String getDescription(){
		return "Permanenza: " + this.permanenza + " giorni";
	}
}
