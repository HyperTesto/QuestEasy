package me.hypertesto.questeasy.voice;

import java.util.List;

import me.hypertesto.questeasy.model.Documento;
import me.hypertesto.questeasy.model.Guest;

/**
 * Class used to parse results from google speech recognition
 * Created by hypertesto on 25/05/16.
 */
public class Recognition {

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
	public Documento parseDocumentInfo (List <String> text){
		return null;
	}

	/**
	 * Should return guest permanenza if set (otherwise -1)
	 * @param text
	 * @return
	 */
	public int parsePermanenza (List<String> text) {
		return -1;
	}
}
