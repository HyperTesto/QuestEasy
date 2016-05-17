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

	public GroupCard(GroupHeadGuest guest, ArrayList<GroupMemberGuest> others, Date date,
									 int permanenza){
		this.capoGruppo = guest;
		this.altri = others;
		this.date = date;
		this.permanenza = permanenza;
	}

	@Override
	public boolean isComplete(){
		if (!this.capoGruppo.isComplete()){
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

	public void addGroupMember(GroupMemberGuest guest){
		if (this.altri == null){
			this.altri = new ArrayList<>();
		}
		this.altri.add(guest);
	}

	public GroupHeadGuest getCapoGruppo() {
		return capoGruppo;
	}

	public ArrayList<GroupMemberGuest> getAltri() {
		return altri;
	}

	@Override
	public String getTitle(){
		return "Nome comitiva";
	}

	@Override
	public String getInitialLetter() {
		return capoGruppo.getName().substring(0,1);
	}
}