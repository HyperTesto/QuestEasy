package me.hypertesto.questeasy.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * Interfaccia che descrive l'ospite generico, specializzato a seconda del tipo,
 * visto che si diceva che nel caso di gruppi e famiglie alcune informazioni sono richieste
 * solo per il capo; forse si può decidere di fare una sola classe Guest e compilare queste
 * informazioni dietro le quinte (se possibile), oppure fregarsene proprio.
 * Created by rigel on 02/05/16.
 */
public abstract class Guest implements Serializable{
	protected String name;
	protected String surname;
	protected Date birthDate;
	protected String sex;
	protected Place placeOfBirth;
	protected Place cittadinanza;

	protected ArrayList<String> pictures;

	public static final class type {
		public static final String SINGLE_GUEST = "Ospite Singolo";
		public static final String FAMILY_HEAD = "Capofamiglia";
		public static final String FAMILY_MEMBER = "Familiare";
		public static final String GROUP_HEAD = "Capogruppo";
		public static final String GROUP_MEMBER = "Membro";
	}

	public ArrayList<String> getPictureUris(){
		ArrayList<String> res = new ArrayList<>();
		res.addAll(this.pictures);
		return res;
	}

	public void addPictureUri(String uri){
		this.pictures.add(uri);
	}

	public void addPictureUris(Collection<? extends String> uris){
		this.pictures.addAll(uris);
	}

	public abstract int getColor();

	public boolean isComplete(){
		if (this.name == null || this.name.equals("")){
			return false;
		} else if (this.surname == null || this.surname.equals("")){
			return false;
		} else if (this.birthDate == null){
			return false;
		} else if (this.sex == null || this.sex.equals("")){
			return false;
		} else if (this.placeOfBirth == null || !this.placeOfBirth.isComplete()){
			return false;
		} else if (this.cittadinanza == null || !this.cittadinanza.isComplete()){
			return false;
		}

		return true;
	}

	@Override
	public boolean equals(Object o){
		if (o != null){
			if (o instanceof Guest){
				Guest go = (Guest) o;

				if (this.name.equals(go.getName())){
					if (this.surname.equals(go.getSurname())){
						if (this.birthDate.equals(go.getBirthDate())){
							if (this.sex.equals(go.getSex())){
								if (this.placeOfBirth.equals(go.getPlaceOfBirth())){
									if (this.cittadinanza.equals(go.getCittadinanza())){
										return this.getClass().equals(go.getClass());
									}
								}
							}
						}
					}
				}
			}
		}

		return false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Place getPlaceOfBirth(){
		return placeOfBirth;
	}

	public void setPlaceOfBirth(Place placeOfBirth){
		this.placeOfBirth = placeOfBirth;
	}

	public Place getCittadinanza() {
		return cittadinanza;
	}

	public void setCittadinanza(Place cittadinanza) {
		this.cittadinanza = cittadinanza;
	}
}
