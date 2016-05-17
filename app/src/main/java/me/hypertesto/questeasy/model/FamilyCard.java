package me.hypertesto.questeasy.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Classe che specializza Card per documentare l'arrivo di una famiglia.
 * Contiene un ospite marcato come capo famiglia, più una lista di familiari.
 * Created by rigel on 02/05/16.
 */
public class FamilyCard extends Card {
	private FamilyHeadGuest capoFamiglia;
	private ArrayList<FamilyMemberGuest> familiari;

	public FamilyCard(){}

	public FamilyCard(FamilyHeadGuest guest, ArrayList<FamilyMemberGuest> others, Date date,
										int permanenza){
		this.capoFamiglia = guest;
		this.familiari = others;
		this.date = date;
		this.permanenza = permanenza;
	}

	@Override
	public boolean isComplete(){
		if (!this.capoFamiglia.isComplete()){
			return false;
		} else {
			for (Guest fm : this.familiari){
				if (!fm.isComplete()){
					return false;
				}
			}
		}

		return true;
	}

	public void addFamilyMember(FamilyMemberGuest guest){
		if (this.familiari == null){
			this.familiari = new ArrayList<>();
		}
		this.familiari.add(guest);
	}

	public void setCapoFamiglia(FamilyHeadGuest capoFamiglia) {
		this.capoFamiglia = capoFamiglia;
	}

	public void setFamiliari(ArrayList<FamilyMemberGuest> familiari) {
		this.familiari = familiari;
	}

	public void setDate(Date date){
		this.date = date;
	}

	public void setPermanenza(){
		this.permanenza = permanenza;
	}

	public FamilyHeadGuest getCapoFamiglia() {
		return capoFamiglia;
	}

	public ArrayList<FamilyMemberGuest> getFamiliari() {
		return familiari;
	}

	@Override
	public String getTitle(){
		return "Cognome capo famiglia";
	}

	@Override
	public String getInitialLetter() {
		return capoFamiglia.getName().substring(0,1);
	}
}
