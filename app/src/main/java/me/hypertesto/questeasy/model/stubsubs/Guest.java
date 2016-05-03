package me.hypertesto.questeasy.model.stubsubs;

import java.util.Date;

/**
 * Interfaccia che descrive l'ospite generico, specializzato a seconda del tipo,
 * visto che si diceva che nel caso di gruppi e famiglie alcune informazioni sono richieste
 * solo per il capo; forse si può decidere di fare una sola classe Guest e compilare queste
 * informazioni dietro le quinte (se possibile), oppure fregarsene proprio.
 * Created by rigel on 02/05/16.
 */
public abstract class Guest {
	protected String name;
	protected String surname;
	protected Date birthDate;
	protected String sex;
	protected String comuneDiNascita;
	protected String provinciaDiNascita;
	protected String statoDiNascita;
	protected String cittadinanza;

}
