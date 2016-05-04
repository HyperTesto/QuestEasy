package me.hypertesto.questeasy.model.listitems;

import java.util.ArrayList;

/**
 * Created by gianluke on 29/04/16.
 */
public class CardListItem {
	private String type;
	private String name;
	private String description;
	private boolean complete;
	private GuestListItem mainGuest;
	private ArrayList<GuestListItem> otherGuests;

	public CardListItem(String type, String name, String description, boolean complete,
											GuestListItem mainGuest, ArrayList<GuestListItem> otherGuests) {
		this.setType(type);
		this.setName(name);
		this.setDescription(description);
		this.setComplete(complete);
		this.setMainGuest(mainGuest);
		this.setOtherGuests(otherGuests);

	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public GuestListItem getMainGuest() {
		return mainGuest;
	}

	public void setMainGuest(GuestListItem mainGuest) {
		this.mainGuest = mainGuest;
	}

	public ArrayList<GuestListItem> getOtherGuests() {
		return otherGuests;
	}

	public void setOtherGuests(ArrayList<GuestListItem> otherGuests) {
		this.otherGuests = otherGuests;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isComplete() {
		return complete;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}
}
