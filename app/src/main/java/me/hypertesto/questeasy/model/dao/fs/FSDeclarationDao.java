package me.hypertesto.questeasy.model.dao.fs;

import android.content.Context;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import me.hypertesto.questeasy.model.*;
import me.hypertesto.questeasy.model.dao.DeclarationDao;
import me.hypertesto.questeasy.model.other.AppendingObjectOutputStream;
import me.hypertesto.questeasy.utils.DateUtils;

/**
 * Created by rigel on 04/05/16.
 */
public class FSDeclarationDao implements DeclarationDao {
	private ObjectInputStream fis;
	private ObjectOutputStream fos;
	private Context context;
	private static final String FILENAME = "declarations.ser";

	private HashMap<User, HashMap<Date, Declaration>> cache;

	public FSDeclarationDao(Context context){
		this.context = context;
		this.cache = null;
	}

	@Override
	public boolean insertDeclaration(Declaration declaration){
		try {
			Date date = declaration.getDate();
			User owner = declaration.getOwner();

			if (date == null || owner == null){
				throw new RuntimeException("Declarations must have an owner and a date");
			}

			if (this.cache.get(owner) == null){
				this.cache.put(owner, new HashMap<Date, Declaration>());
			}

			if (this.cache.get(owner).get(date) == null){
				fos.writeObject(declaration);
				this.cache.get(owner).put(declaration.getDate(), declaration);
			} else {
				this.updateDeclaration(declaration);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return true;
	}

	@Override
	public boolean updateDeclaration(Declaration declaration){
		this.deleteDeclaration(declaration);
		return this.insertDeclaration(declaration);
	}

	@Override
	public ArrayList<Declaration> getAllDeclarations(){
		ArrayList<Declaration> res = new ArrayList<>();

		for (User owner : this.cache.keySet()){
			res.addAll(this.cache.get(owner).values());
		}

		return res;
	}

	public HashMap<User, HashMap<Date, Declaration>> getAllDeclarationsAsMap(){
		HashMap<User, HashMap<Date, Declaration>> res = new HashMap<>();
		res.putAll(this.cache);
		return res;
	}

	@Override
	public Declaration getDeclarationByOwnerDate(User owner, Date date){
		if (this.cache.get(owner) == null){
			return null;
		} else {
			return this.cache.get(owner).get(date);
		}
	}

	public HashMap<Date, Declaration> getDeclarationsByOwner(User owner){
		HashMap<Date, Declaration> res = new HashMap<>();
		res.putAll(this.cache.get(owner));
		return res;
	}

	@Override
	public void deleteDeclaration(Declaration declaration){
		Date date = declaration.getDate();
		User owner = declaration.getOwner();
		HashMap<Date, Declaration> decs = this.getDeclarationsByOwner(owner);

		decs.remove(date);

		this.close();
		this.clear();
		this.open();

		for (Date d : decs.keySet()){
			this.insertDeclaration(decs.get(d));
		}
	}

	@Override
	public void clear(){
		context.deleteFile(FILENAME);
		this.cache = null;
	}

	public void populate() {
		User TESTO = new User("Testo", "fuffa");
		Place DAMBEL = new Place("404022071", "DAMBEL (TN)", false);
		Place ITA = new Place("100000100", "ITALIA", true);
		DocumentType DT = new DocumentType("CARTA DI IDENTITA'", "IDENT");

		SingleGuest sg1 = new SingleGuest();
		sg1.setName("Paolo");
		sg1.setSurname("Rossi");
		sg1.setSex("M");
		sg1.setBirthDate(DateUtils.getDateInstance(1, 1, 1978));
		sg1.setPlaceOfBirth(DAMBEL);
		sg1.setCittadinanza(ITA);
		sg1.setDocumento(new Documento(DT, "AK474747", DAMBEL));
		SingleGuestCard SGC1 = new SingleGuestCard();
		SGC1.setPermanenza(4);
		SGC1.setGuest(sg1);

		SingleGuest sg2 = new SingleGuest();
		sg2.setName("Carlo");
		sg2.setSurname("Puggu");
		sg2.setSex("M");
		sg2.setBirthDate(DateUtils.getDateInstance(15, 7, 1881));
		sg2.setPlaceOfBirth(DAMBEL);
		sg2.setCittadinanza(ITA);
		sg2.setDocumento(new Documento(DT, "AR101101", DAMBEL));
		SingleGuestCard SGC2 = new SingleGuestCard();
		SGC2.setPermanenza(2);
		SGC2.setGuest(sg2);

		SingleGuest sg3 = new SingleGuest();
		sg3.setName("Galio");
		sg3.setSurname("Mangiula");
		sg3.setSex("M");
		sg3.setBirthDate(DateUtils.getDateInstance(17, 3, 1962));
		sg3.setPlaceOfBirth(DAMBEL);
		sg3.setCittadinanza(ITA);
		sg3.setDocumento(new Documento(DT, "AR001100", DAMBEL));
		SingleGuestCard SGC3 = new SingleGuestCard();
		SGC3.setPermanenza(2);
		SGC3.setGuest(sg3);

		SingleGuest sg4 = new SingleGuest();
		sg4.setName("Anna");
		sg4.setSurname("Bianchi");
		sg4.setSex("F");
		sg4.setBirthDate(DateUtils.getDateInstance(16, 8, 1992));
		sg4.setPlaceOfBirth(DAMBEL);
		sg4.setCittadinanza(ITA);
		sg4.setDocumento(new Documento(DT, "AR011110", DAMBEL));
		SingleGuestCard SGC4 = new SingleGuestCard();
		SGC4.setPermanenza(5);
		SGC4.setGuest(sg4);


		FamilyHeadGuest fhg1 = new FamilyHeadGuest();
		fhg1.setName("Peter");
		fhg1.setSurname("Griffin");
		fhg1.setSex("M");
		fhg1.setBirthDate(DateUtils.getDateInstance(12, 4, 1958));
		fhg1.setPlaceOfBirth(DAMBEL);
		fhg1.setCittadinanza(ITA);
		fhg1.setDocumento(new Documento(DT, "MK123456", DAMBEL));

		FamilyMemberGuest fmg11 = new FamilyMemberGuest();
		fmg11.setName("Lois");
		fmg11.setSurname("Griffin");
		fmg11.setSex("F");
		fmg11.setBirthDate(DateUtils.getDateInstance(1, 1, 1960));
		fmg11.setPlaceOfBirth(DAMBEL);
		fmg11.setCittadinanza(ITA);

		FamilyMemberGuest fmg12 = new FamilyMemberGuest();
		fmg12.setName("Chris");
		fmg12.setSurname("Griffin");
		fmg12.setSex("M");
		fmg12.setBirthDate(DateUtils.getDateInstance(1, 1, 1986));
		fmg12.setPlaceOfBirth(DAMBEL);
		fmg12.setCittadinanza(ITA);

		FamilyMemberGuest fmg13 = new FamilyMemberGuest();
		fmg13.setName("Megatron");
		fmg13.setSurname("Griffin");
		fmg13.setSex("F");
		fmg13.setBirthDate(DateUtils.getDateInstance(1, 1, 1984));
		fmg13.setPlaceOfBirth(DAMBEL);
		fmg13.setCittadinanza(ITA);

		FamilyMemberGuest fmg14 = new FamilyMemberGuest();
		fmg14.setName("Stewie");
		fmg14.setSurname("Griffin");
		fmg14.setSex("M");
		fmg14.setBirthDate(DateUtils.getDateInstance(1, 1, 1998));
		fmg14.setPlaceOfBirth(DAMBEL);
		fmg14.setCittadinanza(ITA);

		FamilyMemberGuest fmg15 = new FamilyMemberGuest();
		fmg15.setName("Brian");
		fmg15.setSurname("Griffin");
		fmg15.setSex("M");
		fmg15.setBirthDate(DateUtils.getDateInstance(1, 1, 1993));
		fmg15.setPlaceOfBirth(DAMBEL);
		fmg15.setCittadinanza(ITA);

		FamilyCard GRIFFIN = new FamilyCard();
		GRIFFIN.setPermanenza(16);
		GRIFFIN.setCapoFamiglia(fhg1);
		GRIFFIN.addFamilyMember(fmg11);
		GRIFFIN.addFamilyMember(fmg12);
		GRIFFIN.addFamilyMember(fmg13);
		GRIFFIN.addFamilyMember(fmg14);
		GRIFFIN.addFamilyMember(fmg15);


		FamilyHeadGuest fhg2 = new FamilyHeadGuest();
		fhg2.setName("Cleveland");
		fhg2.setSurname("Brown");
		fhg2.setSex("M");
		fhg2.setBirthDate(DateUtils.getDateInstance(12, 4, 1958));
		fhg2.setPlaceOfBirth(DAMBEL);
		fhg2.setCittadinanza(ITA);
		fhg2.setDocumento(new Documento(DT, "US987654", DAMBEL));

		FamilyMemberGuest fmg21 = new FamilyMemberGuest();
		fmg21.setName("Donna");
		fmg21.setSurname("Tubbs");
		fmg21.setSex("F");
		fmg21.setBirthDate(DateUtils.getDateInstance(1, 1, 1960));
		fmg21.setPlaceOfBirth(DAMBEL);
		fmg21.setCittadinanza(ITA);

		FamilyMemberGuest fmg22 = new FamilyMemberGuest();
		fmg22.setName("Jr");
		fmg22.setSurname("Brown");
		fmg22.setSex("M");
		fmg22.setBirthDate(DateUtils.getDateInstance(1, 1, 1990));
		fmg22.setPlaceOfBirth(DAMBEL);
		fmg22.setCittadinanza(ITA);

		FamilyMemberGuest fmg23 = new FamilyMemberGuest();
		fmg23.setName("Roberta");
		fmg23.setSurname("Tubbs");
		fmg23.setSex("F");
		fmg23.setBirthDate(DateUtils.getDateInstance(1, 1, 1985));
		fmg23.setPlaceOfBirth(DAMBEL);
		fmg23.setCittadinanza(ITA);

		FamilyMemberGuest fmg24 = new FamilyMemberGuest();
		fmg24.setName("Rallo");
		fmg24.setSurname("Tubbs");
		fmg24.setSex("M");
		fmg24.setBirthDate(DateUtils.getDateInstance(1, 1, 2000));
		fmg24.setPlaceOfBirth(DAMBEL);
		fmg24.setCittadinanza(ITA);

		FamilyCard BROWN = new FamilyCard();
		BROWN.setPermanenza(11);
		BROWN.setCapoFamiglia(fhg2);
		BROWN.addFamilyMember(fmg21);
		BROWN.addFamilyMember(fmg22);
		BROWN.addFamilyMember(fmg23);
		BROWN.addFamilyMember(fmg24);


		GroupHeadGuest ghg1 = new GroupHeadGuest();
		ghg1.setName("Mokungo");
		ghg1.setSurname("Punto Ga");
		ghg1.setSex("M");
		ghg1.setBirthDate(DateUtils.getDateInstance(12, 4, 1980));
		ghg1.setPlaceOfBirth(DAMBEL);
		ghg1.setCittadinanza(ITA);
		ghg1.setDocumento(new Documento(DT, "GA666666", DAMBEL));

		GroupMemberGuest gmg11 = new GroupMemberGuest();
		gmg11.setName("Tizio");
		gmg11.setSurname("Tiziolini");
		gmg11.setSex("M");
		gmg11.setBirthDate(DateUtils.getDateInstance(1, 1, 1980));
		gmg11.setPlaceOfBirth(DAMBEL);
		gmg11.setCittadinanza(ITA);

		GroupMemberGuest gmg12 = new GroupMemberGuest();
		gmg12.setName("Caia");
		gmg12.setSurname("Caioli");
		gmg12.setSex("F");
		gmg12.setBirthDate(DateUtils.getDateInstance(1, 1, 1980));
		gmg12.setPlaceOfBirth(DAMBEL);
		gmg12.setCittadinanza(ITA);

		GroupMemberGuest gmg13 = new GroupMemberGuest();
		gmg13.setName("Sempronio");
		gmg13.setSurname("Sempi");
		gmg13.setSex("M");
		gmg13.setBirthDate(DateUtils.getDateInstance(1, 1, 1980));
		gmg13.setPlaceOfBirth(DAMBEL);
		gmg13.setCittadinanza(ITA);

		GroupCard GRUPPO1 = new GroupCard();
		GRUPPO1.setPermanenza(9);
		GRUPPO1.setCapoGruppo(ghg1);
		GRUPPO1.addGroupMember(gmg11);
		GRUPPO1.addGroupMember(gmg12);
		GRUPPO1.addGroupMember(gmg13);


		GroupHeadGuest ghg2 = new GroupHeadGuest();
		ghg2.setName("Alice");
		ghg2.setSurname("Info");
		ghg2.setSex("F");
		ghg2.setBirthDate(DateUtils.getDateInstance(12, 4, 1980));
		ghg2.setPlaceOfBirth(DAMBEL);
		ghg2.setCittadinanza(ITA);
		ghg2.setDocumento(new Documento(DT, "IT198237", DAMBEL));

		GroupMemberGuest gmg21 = new GroupMemberGuest();
		gmg21.setName("Bob");
		gmg21.setSurname("Info");
		gmg21.setSex("M");
		gmg21.setBirthDate(DateUtils.getDateInstance(1, 1, 1980));
		gmg21.setPlaceOfBirth(DAMBEL);
		gmg21.setCittadinanza(ITA);

		GroupMemberGuest gmg22 = new GroupMemberGuest();
		gmg22.setName("Charlie");
		gmg22.setSurname("Malvagio");
		gmg22.setSex("M");
		gmg22.setBirthDate(DateUtils.getDateInstance(1, 1, 1980));
		gmg22.setPlaceOfBirth(DAMBEL);
		gmg22.setCittadinanza(ITA);

		GroupMemberGuest gmg23 = new GroupMemberGuest();
		gmg23.setName("Mallory");
		gmg23.setSurname("Malvagia");
		gmg23.setSex("F");
		gmg23.setBirthDate(DateUtils.getDateInstance(1, 1, 1980));
		gmg23.setPlaceOfBirth(DAMBEL);
		gmg23.setCittadinanza(ITA);

		GroupCard GRUPPO2 = new GroupCard();
		GRUPPO2.setPermanenza(7);
		GRUPPO2.setCapoGruppo(ghg2);
		GRUPPO2.addGroupMember(gmg21);
		GRUPPO2.addGroupMember(gmg22);
		GRUPPO2.addGroupMember(gmg23);

		Declaration declaration = new Declaration();

		this.open();

		declaration.setDate(DateUtils.getDateInstance(12, 6, 2015));
		declaration.setOwner(TESTO);
		declaration.add(SGC1);
		declaration.add(SGC2);
		declaration.add(GRUPPO2);
		this.insertDeclaration(declaration);

		declaration = new Declaration(DateUtils.getDateInstance(15, 7, 2015));
		declaration.setOwner(TESTO);
		declaration.add(SGC3);
		declaration.add(SGC4);
		declaration.add(GRUPPO1);
		this.insertDeclaration(declaration);

		declaration = new Declaration(DateUtils.getDateInstance(16, 7, 2015));
		declaration.setOwner(TESTO);
		declaration.add(GRIFFIN);
		declaration.add(BROWN);
		this.insertDeclaration(declaration);

		declaration = new Declaration(DateUtils.getDateInstance(20, 7, 2015));
		declaration.setOwner(TESTO);
		declaration.add(GRUPPO1);
		declaration.add(GRUPPO2);
		this.insertDeclaration(declaration);

		declaration = new Declaration(DateUtils.getDateInstance(1, 8, 2015));
		declaration.setOwner(TESTO);
		declaration.add(SGC1);
		declaration.add(SGC2);
		declaration.add(SGC3);
		declaration.add(SGC4);
		this.insertDeclaration(declaration);

		declaration = new Declaration(DateUtils.today());
		declaration.setOwner(TESTO);
		declaration.add(SGC1);
		declaration.add(SGC2);
		declaration.add(SGC3);
		declaration.add(SGC4);
		declaration.add(GRIFFIN);
		declaration.add(BROWN);
		declaration.add(GRUPPO1);
		declaration.add(GRUPPO2);
		this.insertDeclaration(declaration);

		this.close();
	}

	private void updateCache(){
		this.cache = new HashMap<>();

		try {
			fis = new ObjectInputStream(context.openFileInput(FILENAME));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		while (true){
			try {
				Object o = fis.readObject();
				Declaration dec;

				if (o instanceof Declaration){
					dec = (Declaration) o;
					Date date = dec.getDate();
					User owner = dec.getOwner();

					if (owner == null || date == null){
						throw new RuntimeException("Declarations must have an owner and a date");
					}

					if (this.cache.get(owner) == null){
						this.cache.put(owner, new HashMap<Date, Declaration>());
					}

					if (this.cache.get(owner).get(date) != null){
						this.cache.get(owner).get(date).addAll(dec);
					} else {
						this.cache.get(owner).put(date, dec);
					}
				} else {
					throw new RuntimeException("WTF??");
				}
			} catch (EOFException ex){
				break;
			} catch (IOException ex){
				ex.printStackTrace();
				break;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				break;
			}
		}
	}

	@Override
	public void open(){
		try {
			//Check if file exists
			File file = context.getFileStreamPath(FILENAME);
			if (file == null || !file.exists()){
				this.fos = new ObjectOutputStream(context.openFileOutput(FILENAME, Context.MODE_APPEND));
			} else {
				this.fos = new AppendingObjectOutputStream(
						context.openFileOutput(FILENAME, Context.MODE_APPEND));
			}

			this.updateCache();
		} catch (IOException e){
			throw new RuntimeException(e);
		}
	}

	@Override
	public void close(){
		try {
			this.cache = null;
			this.fos.close();
		} catch (IOException e){
			throw new RuntimeException(e);
		}
	}
}
