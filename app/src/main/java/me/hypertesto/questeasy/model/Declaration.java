package me.hypertesto.questeasy.model;

import android.support.annotation.NonNull;

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
public class Declaration extends ArrayList<Card> implements Serializable, Comparable<Declaration> {
	private User owner;
	private Date date;
	private boolean sent;

	public Declaration(){
		super();
		sent = false;
	}

	public Declaration(Date date){
		super();
		this.date = date;
		this.owner = null;
		this.sent = false;
	}

	public Declaration(Date date, User owner){
		super();
		this.date = date;
		this.owner = owner;
		this.sent = false;
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

	@Override
	public int compareTo(@NonNull Declaration another) {
		return -this.date.compareTo(another.getDate());
	}

	public void setDate(Date date){
		this.date = date;
	}

	public Date getDate(){
		return this.date;
	}

	public void setOwner(User owner){
		this.owner = owner;
	}

	public User getOwner(){
		return owner;
	}

	public boolean isSent(){
		return sent;
	}

	public void setSent(boolean sent) {
		this.sent = sent;
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
