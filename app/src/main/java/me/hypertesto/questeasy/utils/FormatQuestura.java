package me.hypertesto.questeasy.utils;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import me.hypertesto.questeasy.model.Card;
import me.hypertesto.questeasy.model.Declaration;
import me.hypertesto.questeasy.model.DocumentType;
import me.hypertesto.questeasy.model.Documento;
import me.hypertesto.questeasy.model.FamilyCard;
import me.hypertesto.questeasy.model.FamilyHeadGuest;
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

	/**
	 * Just a main for testing purposes
	 * @param args
	 * @throws ParseException
	 */
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


		System.out.print(convert(d));


	}

	/**
	 * Main method invoked to convert a declaration, it returns a String contanining all the
	 * various Cards converted to questura specification.
	 * @param dec
	 * @return
	 */
	public static String convert (Declaration dec) {

		String result = "";

		if ( dec.isComplete() ) {

			for (Card c : dec) {

				if ( c instanceof SingleGuestCard) {

					result += formatSingleGuest((SingleGuestCard) c);

				} else if ( c instanceof FamilyCard) {

					result += formatFamily((FamilyCard) c);

				} else if ( c instanceof GroupCard) {

					result += formatGroup((GroupCard) c);

				} else {
					new RuntimeException("Card not recognized");
				}

			}

		} else {

			//TODO: should we rise an error?

		}
		return result;
	}

	/**
	 * Returns a formatted SingleGuestCard
	 * @param sgc
	 * @return
	 */
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
		//Assert.assertEquals(168,res.length()); //if string lenght is 168 we are ok
		res+= "\r\n";
		return res;

	}

	/**
	 * Returns a formatted GroupCard
	 * @param gc
	 * @return
	 */
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
		//Assert.assertEquals(168,res.length()); //if string lenght is 168 we are ok
		res += "\r\n";

		for (GroupMemberGuest gmg : gc.getAltri()){
			res += formatGroupMember(gmg, gc.getDate(), gc.getPermanenza());
			//res += "\r\n";
		}
		return res;
	}

	/**
	 * Return a formatted group member
	 * Arrivo and permanenza are gotten from the parent card
	 * @param gmg
	 * @param arrivo
	 * @param permanenza
	 * @return
	 */
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
		res += padRight("", 34);
		//Assert.assertEquals(168,res.length()); //if string lenght is 168 we are ok
		res += "\r\n";

		return res;
	}

	/**
	 * Returns a formatted FamilyCard
	 * @param fc
	 * @return
	 */
	private static String formatFamily (FamilyCard fc) {
		String res = "";

		FamilyHeadGuest fhg = fc.getCapoFamiglia();
		res += FamilyHeadGuest.CODICE;
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		res += df.format(fc.getDate());
		res += String.format("%02d", fc.getPermanenza());
		res += padRight(fhg.getSurname().trim().toUpperCase(),50);
		res += padRight(fhg.getName().trim().toUpperCase(),30);
		res += fhg.getSex().equals("M") ? 1 : 2;
		res += df.format(fhg.getBirthDate());

		//Setting luogo et other balles is a bit more 'na rottura
		res += formatPlaceOfBirth(fhg.getPlaceOfBirth());

		Place cita = fhg.getCittadinanza(); //banana, box
		res += cita.getId();

		res += fhg.getDocumento().getDocType().getCode();
		res += padRight(fhg.getDocumento().getCodice(),20);
		res += fhg.getDocumento().getLuogoRilascio().getId();
		//Assert.assertEquals(168,res.length()); //if string lenght is 168 we are ok
		res += "\r\n";

		for (FamilyMemberGuest fmg : fc.getFamiliari()){
			res += formatFamilyMember(fmg, fc.getDate(), fc.getPermanenza());
			//res += "\r\n";
		}

		return res;
	}

	/**
	 * Returns a formatted family member
	 * arrivo and permamenza are gotten from parent FamilyCard
	 * @param fmg
	 * @param arrivo
	 * @param permanenza
	 * @return
	 */
	private static String formatFamilyMember (FamilyMemberGuest fmg, Date arrivo, int permanenza) {
		String res = "";

		res += FamilyMemberGuest.CODICE;
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		res += df.format(arrivo);
		res += String.format("%02d", permanenza);
		res += padRight(fmg.getSurname().trim().toUpperCase(),50);
		res += padRight(fmg.getName().trim().toUpperCase(),30);
		res += fmg.getSex().equals("M") ? 1 : 2;
		res += df.format(fmg.getBirthDate());

		//Setting luogo et other balles is a bit more 'na rottura
		res += formatPlaceOfBirth(fmg.getPlaceOfBirth());

		Place cita = fmg.getCittadinanza(); //banana, box
		res += cita.getId();
		//Assert.assertEquals(168,res.length()); //if string lenght is 168 we are ok
		res += padRight("", 34);
		res += "\r\n";

		return res;
	}

	/**
	 * Extra method that format place of birth.
	 * Since we got an unified api for both states and municipalities we have to
	 * discriminate here and set fields as specifications.
	 * @param p
	 * @return
	 */
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

	/**
	 * Helper method that pad a String with n spaces on the right.
	 * @param s
	 * @param n
	 * @return
	 */
	public static String padRight(String s, int n) {
		return String.format("%1$-" + n + "s", s);
	}
}
