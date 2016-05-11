package me.hypertesto.questeasy.model;

import java.io.Serializable;

/**
 * Created by rigel on 03/05/16.
 */
public class Documento implements Serializable{
	private String tipo;
	private String codice;
	private String luogoRilascio;

	public Documento(){};

	public Documento(String tipo, String codice, String luogoRilascio){
		this.tipo = tipo;
		this.codice = codice;
		this.luogoRilascio = luogoRilascio;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getLuogoRilascio() {
		return luogoRilascio;
	}

	public void setLuogoRilascio(String luogoRilascio) {
		this.luogoRilascio = luogoRilascio;
	}
}
