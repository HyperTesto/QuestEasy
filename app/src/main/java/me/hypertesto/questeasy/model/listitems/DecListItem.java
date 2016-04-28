package me.hypertesto.questeasy.model.listitems;

/**
 * Created by rigel on 28/04/16.
 */
public class DecListItem {

	private boolean warningSign;
	private String date;
	private String description;

	public DecListItem(){
		super();
	}

	public DecListItem(boolean warningSign, String date, String description){
		super();
		this.warningSign = warningSign;
		this.date = date;
		this.description = description;
	}

	public boolean isWarningSign() {
		return warningSign;
	}

	public void setWarningSign(boolean warningSign) {
		this.warningSign = warningSign;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
