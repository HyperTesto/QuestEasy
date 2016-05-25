package me.hypertesto.questeasy.voice;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.hypertesto.questeasy.model.Documento;
import me.hypertesto.questeasy.model.Guest;

/**
 * Class used to parse results from google speech recognition
 * Created by hypertesto on 25/05/16.
 */
public class Recognition {

	public static void main (String[] args) {

	}

	/**
	 * Should return a guest with personal info
	 * Riconosce testo che contiene frasi nella forma:
	 * - Permanenza %d giorni
	 * - %d giorni di permanenza
	 * @param text
	 * @return
	 */
	public Guest parsePersonalInfo (List<String> text){


		return null;
	}

	/**
	 * Should return a well formatted document
	 * @param text
	 * @return
	 */
	public Documento parseDocumentInfo (String text){
		return null;
	}

	/**
	 * Should return guest permanenza if set (otherwise -1)
	 * @param text
	 * @return
	 */
	public int parsePermanenza (String text) {

		Pattern p1 = Pattern.compile(".*permanenza\\s([0-9]+)\\sgiorni.*");
		Pattern p2 = Pattern.compile(".*\\s([0-9]+)\\sgiorni\\sdi\\spermanenza.*");
		Matcher m1 = p1.matcher(text);
		Matcher m2 = p2.matcher(text);
		boolean b1 = m1.matches();
		boolean b2 = m2.matches();

		if (b1) {
			return Integer.parseInt(m1.group(1));
		} else if (b2) {
			return Integer.parseInt(m2.group(1));
		}

		return -1;
	}
}
