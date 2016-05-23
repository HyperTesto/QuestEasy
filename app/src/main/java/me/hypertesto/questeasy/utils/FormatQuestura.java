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
import me.hypertesto.questeasy.model.FamilyMemberGuest;
import me.hypertesto.questeasy.model.GroupCard;
import me.hypertesto.questeasy.model.GroupHeadGuest;
import me.hypertesto.questeasy.model.GroupMemberGuest;
import me.hypertesto.questeasy.model.Place;
import me.hypertesto.questeasy.model.SingleGuest;
import me.hypertesto.questeasy.model.SingleGuestCard;

/**
 * Created by hypertesto on 23/05/16.
 * Static class that converts a Declaration into Questura specification
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
		res += formatPlaceOfBirth(sg.getPlaceOfBirth());

		Place cita = sg.getCittadinanza(); //banana, box
		res += cita.getId();

		res += sg.getDocumento().getDocType().getCode();
		res += padRight(sg.getDocumento().getCodice(),20);
		res += sg.getDocumento().getLuogoRilascio().getId();
		Assert.assertEquals(168,res.length()); //if string lenght is 168 we are ok
		res+= "\n";
		return res;

	}

	private static String formatGroup(GroupCard gc) {
		String res = "";

		GroupHeadGuest ghg = gc.getCapoGruppo();
		res += GroupHeadGuest.CODICE;
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		res += df.format(gc.getDate());
		res += String.format("%02d", gc.getPermanenza());
		res += padRight(ghg.getSurname().trim().toUpperCase(),50);
		res += padRight(ghg.getName().trim().toUpperCase(),30);
		res += ghg.getSex().equals("M") ? 1 : 2;
		res += df.format(ghg.getBirthDate());

		//Setting luogo et other balles is a bit more 'na rottura
		res += formatPlaceOfBirth(ghg.getPlaceOfBirth());

		Place cita = ghg.getCittadinanza(); //banana, box
		res += cita.getId();

		res += ghg.getDocumento().getDocType().getCode();
		res += padRight(ghg.getDocumento().getCodice(),20);
		res += ghg.getDocumento().getLuogoRilascio().getId();
		Assert.assertEquals(168,res.length()); //if string lenght is 168 we are ok
		res += "\n";


		for (GroupMemberGuest gmg : gc.getAltri()){
			res += formatGroupMember(gmg, gc.getDate(), gc.getPermanenza());
			res += "\n";
		}
		return "";
	}

	private static String formatGroupMember(GroupMemberGuest gmg, Date arrivo, int permanenza) {
		String res = "";

		res += GroupMemberGuest.CODICE;
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		res += df.format(arrivo);
		res += String.format("%02d", permanenza);
		res += padRight(gmg.getSurname().trim().toUpperCase(),50);
		res += padRight(gmg.getName().trim().toUpperCase(),30);
		res += gmg.getSex().equals("M") ? 1 : 2;
		res += df.format(gmg.getBirthDate());

		//Setting luogo et other balles is a bit more 'na rottura
		res += formatPlaceOfBirth(gmg.getPlaceOfBirth());

		Place cita = gmg.getCittadinanza(); //banana, box
		res += cita.getId();
		res += "\n";

		return res;
	}

	private static String formatFamily (FamilyCard fc) {
		return "";
	}

	private static String formatFamilyMember (FamilyMemberGuest fmg) {
		return "";
	}

	private static String formatPlaceOfBirth(Place p) {

		String res = "";
		if (p.isState()){
			res += padRight("",9);
			res += padRight("",2);
			res += p.getId();
		} else {
			res += padRight(p.getId(),9);
			res += p.getProvincia();
			res += "100000100";
		}
		return res;

	}

	public static String padRight(String s, int n) {
		return String.format("%1$-" + n + "s", s);
	}

	public static String padLeft(String s, int n) {
		return String.format("%1$" + n + "s", s);
	}
}
