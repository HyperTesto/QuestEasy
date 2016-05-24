package me.hypertesto.questeasy.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
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


	@Override
	public boolean add(Card card){
		card.setDate(this.date);
		return super.add(card);
	}

	@Override
	public void add(int index, Card card){
		card.setDate(this.date);
		super.add(index, card);
	}

	@Override
	public boolean addAll(Collection<? extends Card> cards){
		for (Card c : cards){
			c.setDate(this.date);
		}

		return super.addAll(cards);
	}

	@Override
	public boolean addAll(int index, Collection<? extends Card> cards) {
		for (Card c : cards){
			c.setDate(this.date);
		}

		return super.addAll(index, cards);
	}

	@Override
	public Card set(int index, Card card){
		card.setDate(this.date);
		return super.set(index, card);
	}
}
