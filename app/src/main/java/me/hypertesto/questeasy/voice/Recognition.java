package me.hypertesto.questeasy.voice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.hypertesto.questeasy.model.DocumentType;
import me.hypertesto.questeasy.model.Documento;
import me.hypertesto.questeasy.model.FamilyHeadGuest;
import me.hypertesto.questeasy.model.FamilyMemberGuest;
import me.hypertesto.questeasy.model.GroupHeadGuest;
import me.hypertesto.questeasy.model.GroupMemberGuest;
import me.hypertesto.questeasy.model.Guest;
import me.hypertesto.questeasy.model.Place;
import me.hypertesto.questeasy.model.SingleGuest;
import me.hypertesto.questeasy.model.adapters.DocumentTypeAdapter;
import me.hypertesto.questeasy.utils.DateUtils;
import me.hypertesto.questeasy.utils.StaticGlobals;

/**
 * Class used to parse results from google speech recognition
 * Created by hypertesto on 25/05/16.
 */
public class Recognition {

	public static void main (String[] args) {

	}



	/**
	 * Should return a guest with personal info
	 * We pass a type in order to init the right class (guest is abstract), this choice allow us to
	 * keep activity code more clear and simpler
	 * @param text
	 * @param type
	 * @return
	 */
	public Guest parsePersonalInfo (String text, String type){

		Guest res = initGuestByType(type);
		//												(1)				(2)				(3)
		Pattern personalInfo = Pattern.compile(".*(signor|signora)\\s([a-zA-Z]+)\\s([a-zA-Z]+).*");
		//												(1)			(2)				(3)			(4)			(5)				(6)
		Pattern birthInfo = Pattern.compile(".*nato\\s(a|in)\\s([a-zA-Z]+)\\sil\\s(giorno\\s)*([0-9]+)\\s([a-zA-Z0-9]+)\\s([0-9]+).*");

		Pattern citizenship = Pattern.compile(".*cittadinanza\\s([a-zA-Z]+).*");

		//TODO: make sure month is expressed as a number
		Matcher m1 = personalInfo.matcher(text);
		Matcher m2 = birthInfo.matcher(text);
		Matcher m3 = citizenship.matcher(text);

		if (m1.matches()){
			if (m1.group(1).equals("signor")){
				res.setSex("M");
			} else {
				res.setSex("F");
			}
			res.setName(m1.group(2));
			res.setName(m1.group(3));
		}

		Place birth = new Place();
		if (m2.matches()) {
			birth.setName(m2.group(2));
			convertMonths(text); //convert months to number
			String birthDate = String.format("%s/%s/%s", m2.group(4), m2.group(5), m2.group(6));
			res.setBirthDate(DateUtils.parse(birthDate));
		}
		res.setPlaceOfBirth(birth);

		Place citizen = new Place();
		if (m3.matches()){
			citizen.setName(m3.group(1));
		}
		res.setCittadinanza(citizen);

		return res;

	}

	/**
	 * Should return a well formatted document
	 * @param text
	 * @return
	 */
	public Documento parseDocumentInfo (String text){

		DocumentType dt = new DocumentType("","");
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

	/**
	 * Auxiliary method that correctly init a guest given his type
	 * @param type
	 * @return
	 */
	private Guest initGuestByType(String type){

		Guest res = null;
		switch (type){
			case Guest.type.SINGLE_GUEST:
				res = new SingleGuest();
				break;
			case Guest.type.FAMILY_HEAD:
				res = new FamilyHeadGuest();
				break;
			case Guest.type.GROUP_HEAD:
				res = new GroupHeadGuest();
				break;
			case Guest.type.FAMILY_MEMBER:
				res = new FamilyMemberGuest();
				break;
			case Guest.type.GROUP_MEMBER:
				res = new GroupMemberGuest();
				break;
		}
		return res;
	}

	/**
	 * Auxiliary method that replace every month in a give string with his respective number
	 * @param text
	 */
	private void convertMonths(String text){

		//FIXME: bad implementation
		String[] months = {"gennaio", "febbraio", "marzo", "aprile", "maggio", "giugno", "luglio",
							"agosto", "settembre", "ottobre", "novembre", "dicembre"};
		for (int i = 0; i < months.length; i++){
			text = text.replace(months[i], String.valueOf(i));
		}


	}
}
