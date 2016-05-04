package me.hypertesto.questeasy.model.listitems;

import java.util.Date;

/**
 * Created by gianluke on 28/04/16.
 */
public class GuestListItem {
	private String Name;
	private String Cognome;
	private String CodiceFiscale;
	private Date DataDiNascita;
	private String LuogoDiNascita;
	private String Nazione;
	private String Comune;
	private String CAP;
	private Date Arrivo;
	private Date Partenza;


	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getCognome() {
		return Cognome;
	}

	public void setCognome(String cognome) {
		Cognome = cognome;
	}

	public String getCodiceFiscale() {
		return CodiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		CodiceFiscale = codiceFiscale;
	}

	public Date getDataDiNascita() {
		return DataDiNascita;
	}

	public void setDataDiNascita(Date dataDiNascita) {
		DataDiNascita = dataDiNascita;
	}

	public String getLuogoDiNascita() {
		return LuogoDiNascita;
	}

	public void setLuogoDiNascita(String luogoDiNascita) {
		LuogoDiNascita = luogoDiNascita;
	}

	public String getNazione() {
		return Nazione;
	}

	public void setNazione(String nazione) {
		Nazione = nazione;
	}

	public String getComune() {
		return Comune;
	}

	public void setComune(String comune) {
		Comune = comune;
	}

	public String getCAP() {
		return CAP;
	}

	public void setCAP(String CAP) {
		this.CAP = CAP;
	}

	public Date getArrivo() {
		return Arrivo;
	}

	public void setArrivo(Date arrivo) {
		Arrivo = arrivo;
	}

	public Date getPartenza() {
		return Partenza;
	}

	public void setPartenza(Date partenza) {
		Partenza = partenza;
	}
}
