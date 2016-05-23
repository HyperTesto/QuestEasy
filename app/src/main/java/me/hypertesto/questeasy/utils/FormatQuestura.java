package me.hypertesto.questeasy.utils;

import junit.framework.Assert;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import me.hypertesto.questeasy.model.Card;
import me.hypertesto.questeasy.model.Declaration;
import me.hypertesto.questeasy.model.DocumentType;
import me.hypertesto.questeasy.model.Documento;
import me.hypertesto.questeasy.model.FamilyCard;
import me.hypertesto.questeasy.model.GroupCard;
import me.hypertesto.questeasy.model.Place;
import me.hypertesto.questeasy.model.SingleGuest;
import me.hypertesto.questeasy.model.SingleGuestCard;

/**
 * Created by hypertesto on 23/05/16.
 */
public class FormatQuestura {

	public static void main (String args[]) throws ParseException {

		Declaration d = new Declaration();
		SingleGuestCard c = new SingleGuestCard();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		c.setDate(df.parse("12/12/2016"));
		c.setPermanenza(4);
		SingleGuest g = new SingleGuest();
		g.setName("Gianluca");
		g.setSurname("Apriceno");
		g.setSex("M");
		g.setBirthDate(df.parse("15/06/1994"));
		g.setPlaceOfBirth(new Place("405025006", "BELLUNO (BL)", false));
		g.setCittadinanza(new Place("100000100", "ITALIA", true));

		Documento doc = new Documento(new DocumentType("Carta identità", "IDENT"), "AR1789342", new Place("405025006", "BELLUNO (BL)", false));
		g.setDocumento(doc);
		c.setGuest(g);
		d.add(c);


		System.out.println(formatSingleGuest(c));


	}

	public String convert (Declaration dec) {

		String result = "";

		if ( dec.isComplete() ) {

			for (Card c : dec) {

				if ( c instanceof SingleGuestCard) {

					String tmp = String.format("%s");

				} else if ( c instanceof FamilyCard) {

				} else if ( c instanceof GroupCard) {

				} else {
					new RuntimeException("Card not recognized");
				}

			}

		} else {

			//TODO: should we rise an error?

		}
		return result;
	}

	private static String formatSingleGuest(SingleGuestCard sgc) {

		String res = "";
		SingleGuest sg = sgc.getGuest();

		res += SingleGuest.CODICE;
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		res += df.format(sgc.getDate());
		res += String.format("%02d", sgc.getPermanenza());
		res += padRight(sg.getSurname().trim().toUpperCase(),50);
		res += padRight(sg.getName().trim().toUpperCase(),30);
		res += sg.getSex().equals("M") ? 1 : 2;
		res += df.format(sg.getBirthDate());

		//Setting luogo et other balles is a bit more 'na rottura
		Place p = sg.getPlaceOfBirth();
		if (p.isState()){
			res += padRight("",9);
			res += padRight("",2);
			res += p.getId();
		} else {
			res += padRight(p.getId(),9);
			res += p.getProvincia();
			res += "100000100";
		}

		Place cita = sg.getCittadinanza(); //banana, box
		res += cita.getId();

		res += sg.getDocumento().getDocType().getCode();
		res += padRight(sg.getDocumento().getCodice(),20);
		res += sg.getDocumento().getLuogoRilascio().getId();
		Assert.assertEquals(168,res.length()); //if string lenght is 168 we are ok
		return res;

	}

	public static String padRight(String s, int n) {
		return String.format("%1$-" + n + "s", s);
	}

	public static String padLeft(String s, int n) {
		return String.format("%1$" + n + "s", s);
	}
}
