package me.hypertesto.questeasy.model;

import java.util.Date;

/**
 * Questa è la classe astratta della schedina generica, che documenta un singolo arrivo,
 * e viene specializzata a seconda della tipologia.
 * Potrebbe ritornare qualche sorta di nome/titolo + descrizione.
 * Created by rigel on 02/05/16.
 */
public abstract class Card {
	protected Date date;
	protected int permanenza;
	protected boolean complete;

	public boolean isComplete(){
		return this.complete;
	}

	public abstract String getTitle();

	public String getDescription(){
		return "Permanenza: " + this.permanenza + " giorni";
	}
}
