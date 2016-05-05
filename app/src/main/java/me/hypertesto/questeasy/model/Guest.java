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
	protected String comuneDiNascita;
	protected String provinciaDiNascita;
	protected String statoDiNascita;
	protected String cittadinanza;

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

	public String getComuneDiNascita() {
		return comuneDiNascita;
	}

	public void setComuneDiNascita(String comuneDiNascita) {
		this.comuneDiNascita = comuneDiNascita;
	}

	public String getProvinciaDiNascita() {
		return provinciaDiNascita;
	}

	public void setProvinciaDiNascita(String provinciaDiNascita) {
		this.provinciaDiNascita = provinciaDiNascita;
	}

	public String getStatoDiNascita() {
		return statoDiNascita;
	}

	public void setStatoDiNascita(String statoDiNascita) {
		this.statoDiNascita = statoDiNascita;
	}

	public String getCittadinanza() {
		return cittadinanza;
	}

	public void setCittadinanza(String cittadinanza) {
		this.cittadinanza = cittadinanza;
	}
}
