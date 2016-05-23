package me.hypertesto.questeasy.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import me.hypertesto.questeasy.model.Card;
import me.hypertesto.questeasy.model.Declaration;
import me.hypertesto.questeasy.model.FamilyCard;
import me.hypertesto.questeasy.model.GroupCard;
import me.hypertesto.questeasy.model.Place;
import me.hypertesto.questeasy.model.SingleGuest;
import me.hypertesto.questeasy.model.SingleGuestCard;

/**
 * Created by hypertesto on 23/05/16.
 */
public class FormatQuestura {

	public Object convert (Declaration dec) {

		String result = "";

		if ( dec.isComplete() ) {

			for (Card c : dec) {

				if ( c instanceof SingleGuestCard) {

					String tmp = String.format("%s")

				} else if ( c instanceof FamilyCard) {

				} else if ( c instanceof GroupCard) {

				} else {
					new RuntimeException("Card not recognized");
				}

			}

		} else {

			//TODO: should we rise an error?

		}
		return null;
	}

	private String formatSignleGuest(SingleGuestCard sgc) {

		String res = "";
		SingleGuest sg = sgc.getGuest();

		res += SingleGuest.CODICE;
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		res += df.format(sgc.getDate());
		res += String.format("%02d", sgc.getPermanenza());
		res += padRight(sg.getSurname().trim().toUpperCase(),50);
		res += padRight(sg.getName().trim().toUpperCase(),30);
		res += sg.getSex() == "M" ? 1 : 2;
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


		return res;

	}

	public static String padRight(String s, int n) {
		return String.format("%1$-" + n + "s", s);
	}

	public static String padLeft(String s, int n) {
		return String.format("%1$" + n + "s", s);
	}
}
