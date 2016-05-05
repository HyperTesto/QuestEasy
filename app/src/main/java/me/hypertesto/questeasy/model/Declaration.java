package me.hypertesto.questeasy.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Questa è la classe della denuncia cumulativa che viene fatta alla questura
 * (quella per l'intera giornata che va consegnata entro 48(?) ore).
 * Contiene una lista di schede e la data.
 * Created by rigel on 02/05/16.
 */
public class Declaration extends ArrayList<Card> implements Serializable {
	private Date date;
	private boolean complete;

	public Declaration(Date date, boolean complete){
		super();
		this.date = date;
		this.complete = complete;
	}

	public void setDate(Date date){
		this.date = date;
	}

	public void setComplete(boolean complete){
		this.complete = complete;
	}

	public boolean isComplete(){
		return this.complete;
	}

	public Date getDate(){
		return this.date;
	}
}
