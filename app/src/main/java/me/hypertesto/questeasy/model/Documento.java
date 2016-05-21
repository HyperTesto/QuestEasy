package me.hypertesto.questeasy.model;

import java.io.Serializable;

/**
 * Created by rigel on 03/05/16.
 */
public class Documento implements Serializable{

	private DocumentType docType;
	private String codice;
	private String luogoRilascio;

	public Documento(){}

	public Documento(DocumentType docType, String codice, String luogoRilascio) {
		this.docType = docType;
		this.codice = codice;
		this.luogoRilascio = luogoRilascio;
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

	public DocumentType getDocType() {
		return docType;
	}

	public void setDocType(DocumentType docType) {
		this.docType = docType;
	}
}
