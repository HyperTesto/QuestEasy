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

	public Declaration(){
		super();
	}

	public Declaration(Date date){
		super();
		this.date = date;
	}

	public boolean isComplete(){
		for (Card c : this){
			if (!c.isComplete()){
				return false;
			}
		}

		return true;
	}

	@Override
	public boolean equals(Object o){
		if (o != null){
			if (o instanceof Declaration){
				Declaration dco = (Declaration) o;

				if (this.containsAll(dco)){
					if (dco.containsAll(this)){
						return this.date.equals(dco.getDate());
					}
				}
			}
		}

		return false;
	}

	public void setDate(Date date){
		this.date = date;
	}

	public Date getDate(){
		return this.date;
	}
}
