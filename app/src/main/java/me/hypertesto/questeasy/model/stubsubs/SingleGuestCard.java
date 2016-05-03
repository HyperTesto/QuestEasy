package me.hypertesto.questeasy.model.stubsubs;

/**
 * Classe che specializza Card per rappresentare l'arrivo di un ospite singolo.
 * Created by rigel on 02/05/16.
 */
public class SingleGuestCard extends Card {
	private SingleGuest guest;

	@Override
	public String getTitle(){
		return "Nome dell'ospite";
	}

	@Override
	public String getDescription(){
		return "Qualcosa a caso";
	}
}
