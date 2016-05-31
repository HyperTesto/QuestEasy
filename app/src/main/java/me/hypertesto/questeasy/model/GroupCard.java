package me.hypertesto.questeasy.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Classe che specializza Card per documentare l'arrivo di un gruppo.
 * Contiene un ospite marcato come capo gruppo, più una lista di ospiti secondari.
 * Created by rigel on 02/05/16.
 */
public class GroupCard extends Card {
	private GroupHeadGuest capoGruppo;
	private ArrayList<GroupMemberGuest> altri;

	public GroupCard(){}

	public GroupCard(GroupHeadGuest guest, ArrayList<GroupMemberGuest> others, Date date,
									 int permanenza){
		this.capoGruppo = guest;
		this.altri = others;
		this.date = date;
		this.permanenza = permanenza;
	}

	@Override
	public Guest getMainGuest() {
		return this.capoGruppo;
	}

	@Override
	public boolean isComplete(){
		if (this.capoGruppo == null || !this.capoGruppo.isComplete()){
			return false;
		} else if (this.altri == null){
			return false;
		} else {
			for (Guest gm : this.altri){
				if (!gm.isComplete()){
					return false;
				}
			}
		}

		return true;
	}

	@Override
	public boolean equals(Object o){
		if (o != null){
			if (o instanceof GroupCard){
				GroupCard gco = (GroupCard) o;

				if (this.altri.containsAll(gco.getAltri())){
					if (gco.getAltri().containsAll(this.altri)){
						return (this.capoGruppo.equals(gco.getCapoGruppo()) &&
								this.permanenza == gco.getPermanenza());
					}
				}
			}
		}

		return false;
	}

	public void addGroupMember(GroupMemberGuest guest){
		if (this.altri == null){
			this.altri = new ArrayList<>();
		}
		this.altri.add(guest);
	}

	public void setCapoGruppo(GroupHeadGuest capoGruppo) {
		this.capoGruppo = capoGruppo;
	}

	public void setAltri(ArrayList<GroupMemberGuest> altri) {
		this.altri = altri;
	}

	public GroupHeadGuest getCapoGruppo() {
		return capoGruppo;
	}

	public ArrayList<GroupMemberGuest> getAltri() {
		return altri;
	}

	@Override
	public String getTitle(){
		return String.format("Gruppo %s", this.capoGruppo.getSurname());
	}

	@Override
	public String getInitialLetter() {
		if (this.capoGruppo.getName() == null || this.capoGruppo.getName().equals("")){
			return "X";
		} else {
			return this.capoGruppo.getName().substring(0,1);
		}
	}
}