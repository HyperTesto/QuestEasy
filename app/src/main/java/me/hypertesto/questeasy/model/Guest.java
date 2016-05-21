package me.hypertesto.questeasy.model;

import java.io.Serializable;
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
	protected String cittadinanza;

	public static final class type {
		public static final String SINGLE_GUEST = "Ospite Singolo";
		public static final String FAMILY_HEAD = "Capofamiglia";
		public static final String FAMILY_MEMBER = "Failiare";
		public static final String GROUP_HEAD = "Capogruppo";
		public static final String GROUP_MEMBER = "Membro";
	}

	public boolean isComplete(){
		if (this.name == null || this.name.equals("")){
			return false;
		} else if (this.surname == null || this.surname.equals("")){
			return false;
		} else if (this.birthDate == null){
			return false;
		} else if (this.sex == null || this.sex.equals("")){
			return false;
		} else if (!this.placeOfBirth.isComplete()){
			return false;
		} else if (this.cittadinanza == null || this.cittadinanza.equals("")){
			return false;
		}

		return true;
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

	public String getCittadinanza() {
		return cittadinanza;
	}

	public void setCittadinanza(String cittadinanza) {
		this.cittadinanza = cittadinanza;
	}
}
