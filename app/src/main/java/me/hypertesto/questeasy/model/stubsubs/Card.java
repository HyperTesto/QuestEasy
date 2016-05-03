package me.hypertesto.questeasy.model.stubsubs;

import java.util.Date;

/**
 * Questa è la classe astratta della schedina generica, che documenta un singolo arrivo,
 * e viene specializzata a seconda della tipologia.
 * Potrebbe ritornare qualche sorta di nome/titolo + descrizione.
 * Created by rigel on 02/05/16.
 */
public abstract class Card {
	private Date date;
	private int permanenza;
	private boolean complete;

	public boolean isComplete(){
		return this.complete;
	}

	public abstract String getTitle();

	public abstract String getDescription();
}
