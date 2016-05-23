package me.hypertesto.questeasy.model;

/**
 * Created by rigel on 23/05/16.
 */
public class SavedGuest extends Guest {

	public SavedGuest(){}

	public SavedGuest(Guest g){
		this.name = g.getName();
		this.surname = g.getSurname();
		this.birthDate = g.getBirthDate();
		this.sex = g.getSex();
		this.placeOfBirth = g.getPlaceOfBirth();
		this.cittadinanza = g.getCittadinanza();
	}
}
