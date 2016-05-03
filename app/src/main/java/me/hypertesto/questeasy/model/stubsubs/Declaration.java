package me.hypertesto.questeasy.model.stubsubs;

import java.util.ArrayList;
import java.util.Date;

/**
 * Questa è la classe della denuncia cumulativa che viene fatta alla questura
 * (quella per l'intera giornata che va consegnata entro 48(?) ore).
 * Contiene una lista di schede e la data.
 * Created by rigel on 02/05/16.
 */
public class Declaration {

	private Date date;
	private boolean complete;
	private ArrayList<Card> cards;

	public int getCardCount(){
		return cards.size();
	}
}
