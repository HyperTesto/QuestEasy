package me.hypertesto.questeasy.voice;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.hypertesto.questeasy.model.DocumentType;
import me.hypertesto.questeasy.model.Documento;
import me.hypertesto.questeasy.model.Guest;
import me.hypertesto.questeasy.model.Place;
import me.hypertesto.questeasy.model.adapters.DocumentTypeAdapter;

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

		DocumentType dt = new DocumentType();
		Place release = new Place();
		Documento res = new Documento();

		Pattern docType = Pattern.compile(".*(carta\\sdi\\sidentità|passaporto).*"); //right now we only support this 2
		Pattern docNumber = Pattern.compile(".*numero\\s([a-zA-Z-0-9]+).*");
		Pattern docRelease = Pattern.compile(".*rilasciat(o|a)\\s(a|in)\\s([a-zA-Z]+).*");

		Matcher m1 = docNumber.matcher(text);
		Matcher m2 = docRelease.matcher(text);
		Matcher m3 = docType.matcher(text);

		//TODO: add documentType support

		if (m1.matches()){
			res.setCodice(m1.group(1));
		}

		if (m2.matches()){
			release.setName(m2.group(3));	//we need just the name to set the texView (API request is done automatically)
		}
		res.setLuogoRilascio(release);

		if (m3.matches()) {
			dt.setName(m3.group(1));
		}
		res.setDocType(dt);

		return res;
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
