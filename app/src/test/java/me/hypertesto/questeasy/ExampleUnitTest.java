package me.hypertesto.questeasy;

import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import me.hypertesto.questeasy.model.Declaration;
import me.hypertesto.questeasy.model.DocumentType;
import me.hypertesto.questeasy.model.Documento;
import me.hypertesto.questeasy.model.Guest;
import me.hypertesto.questeasy.model.Place;
import me.hypertesto.questeasy.model.SingleGuest;
import me.hypertesto.questeasy.model.SingleGuestCard;
import me.hypertesto.questeasy.utils.DateUtils;
import me.hypertesto.questeasy.utils.FormatQuestura;
import me.hypertesto.questeasy.voice.Recognition;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void permaneza_recognition_isCorrect() throws Exception {
        Recognition r = new Recognition();
        List<String> testList = new ArrayList<>();
        testList.add("asdas permanenza 10 giorni");
		testList.add("asdfg 12 giorni di permanenza    ");
		testList.add("garbage");
        assertEquals("Should match first case", 10, r.parsePermanenza(testList.get(0)));
		assertEquals("Should match second case", 12, r.parsePermanenza(testList.get(1)));
		assertEquals("Shoud return -1", -1, r.parsePermanenza(testList.get(2)));
    }

	@Test
	public void document_recognition_isCorrect() throws Exception {

		Recognition r = new Recognition();
		List<String> testList = new ArrayList<>();
		testList.add("carta di identità numero AK12345 rilasciata in italia");
		testList.add("numero 123456787");
		testList.add("rilasciato a falcade");
		testList.add("garbage");
		testList.add("carta di identità");
		testList.add("passaporto");
		assertEquals("Should match first case number", "ak12345", r.parseDocumentInfo(testList.get(0)).getCodice());
		assertEquals("Should match first case release", "italia", r.parseDocumentInfo(testList.get(0)).getLuogoRilascio().getName());
		assertEquals("Should match number", "123456787", r.parseDocumentInfo(testList.get(1)).getCodice());
		assertEquals("Should match release", "falcade", r.parseDocumentInfo(testList.get(2)).getLuogoRilascio().getName());
		assertEquals("Should match c.ident", "carta di identità", r.parseDocumentInfo(testList.get(4)).getDocType().getName());
		assertEquals("Should match passaporto", "passaporto",  r.parseDocumentInfo(testList.get(5)).getDocType().getName());

	}

	@Test
	public void guest_recognition_isCorrect() throws Exception {

		Recognition r = new Recognition();

		Guest test = r.parsePersonalInfo("signor Zanfo zanfagni nato a roma il 26 gennaio 1994, cittadinanza francia", Guest.type.SINGLE_GUEST);
		assertEquals("Should match name", "zanfo", test.getName());
		assertEquals("Should match surname", "zanfagni", test.getSurname());
		assertEquals("Should match sex", "M", test.getSex());
		assertEquals("Should match bithPlace", "roma", test.getPlaceOfBirth().getName());
		assertEquals("Should match date", "26/01/1994", DateUtils.format(test.getBirthDate()));
		assertEquals("Should match citizenship", "francia", test.getCittadinanza().getName());

	}

	@Test
	public void file_conversion_isCorrect() throws Exception {
		Declaration d = new Declaration();
		d.setDate(DateUtils.today());
		SingleGuestCard c = new SingleGuestCard();
		c.setDate(DateUtils.parse("12/12/2016"));
		c.setPermanenza(4);
		SingleGuest g = new SingleGuest();
		g.setName("Gianluca");
		g.setSurname("Apriceno");
		g.setSex("M");
		g.setBirthDate(DateUtils.parse("15/06/1994"));
		g.setPlaceOfBirth(new Place("405025006", "BELLUNO (BL)", false));
		g.setCittadinanza(new Place("100000100", "ITALIA", true));

		Documento doc = new Documento(new DocumentType("Carta identità", "IDENT"), "AR1789342", new Place("405025006", "BELLUNO (BL)", false));
		g.setDocumento(doc);
		c.setGuest(g);
		d.add(c);

		String test = FormatQuestura.convert(d);
		assertEquals("Should be final file size + 2 extra chars", 170, test.length());
		//TODO: add other tests
	}
}