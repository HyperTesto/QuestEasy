package me.hypertesto.questeasy.model.dao.fs;

import android.content.Context;

import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

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
import me.hypertesto.questeasy.model.dao.DeclarationDao;
import me.hypertesto.questeasy.model.other.AppendingObjectOutputStream;

/**
 * Created by rigel on 04/05/16.
 */
public class FSDeclarationDao implements DeclarationDao {
	private ObjectInputStream fis;
	private ObjectOutputStream fos;
	private Context context;
	private static final String FILENAME = "declarations.ser";

	private HashMap<Date, Declaration> cache;

	public FSDeclarationDao(Context context){
		this.context = context;
		this.cache = null;
	}

	@Override
	public boolean insertDeclaration(Declaration declaration){
		try {
			if (cache.get(declaration.getDate()) == null){
				fos.writeObject(declaration);
				this.cache.put(declaration.getDate(), declaration);
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
	public HashMap<Date, Declaration> getAllDeclarations(){
		HashMap<Date, Declaration> res = new HashMap<>();
		res.putAll(this.cache);
		return res;
	}

	@Override
	public Declaration getDeclarationByDate(Date date){
		return this.cache.get(date);
	}

	@Override
	public void deleteDeclaration(Declaration declaration){
		HashMap<Date, Declaration> decs = this.getAllDeclarations();
		decs.remove(declaration.getDate());

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

	public void populate(){
		Place DAMBEL = new Place("12", "Dambel (TN)", false);

		SingleGuest g = new SingleGuest();
		g.setBirthDate(new Date());
		g.setName("Paolo");
		g.setSurname("Rossi");
		g.setSex("MF");
		g.setCittadinanza(new Place("12","ITALIA",true));
		g.setPlaceOfBirth(DAMBEL);
		g.setDocumento(new Documento(new DocumentType("carta ident", "100000"), "b", new Place("Falcade (BL)", "4000000", false)));

		FamilyHeadGuest g1 = new FamilyHeadGuest();
		g1.setBirthDate(new Date());
		g1.setName("Manilo");
		g1.setSurname("Carlini");
		g1.setSex("TF");
		g1.setCittadinanza(new Place("12","ITALIA",true));
		g1.setPlaceOfBirth(DAMBEL);
		g1.setDocumento(new Documento(new DocumentType("carta ident", "100000"), "d", new Place("Falcade (BL)", "4000000", false)));

		ArrayList<FamilyMemberGuest> fmgs = new ArrayList<>();
		FamilyMemberGuest g2 = new FamilyMemberGuest();
		g2.setBirthDate(new Date());
		g2.setName("Carlo");
		g2.setSurname("Carlini");
		g2.setSex("TF");
		g2.setCittadinanza(new Place("12","ITALIA",true));
		g2.setPlaceOfBirth(DAMBEL);
		fmgs.add(g2);

		g2 = new FamilyMemberGuest();
		g2.setBirthDate(new Date());
		g2.setName("Rallo");
		g2.setSurname("Lorlini");
		g2.setSex("TF");
		g2.setCittadinanza(new Place("12","ITALIA",true));
		g2.setPlaceOfBirth(DAMBEL);
		fmgs.add(g2);

		GroupHeadGuest g3 = new GroupHeadGuest();
		g3.setBirthDate(new Date());
		g3.setName("Lapillo");
		g3.setSurname("lupalini");
		g3.setSex("TF");
		g3.setCittadinanza(new Place("12","ITALIA",true));
		g3.setPlaceOfBirth(DAMBEL);
		g3.setDocumento(new Documento(new DocumentType("carta ident", "100000"), "d", new Place("Falcade (BL)", "4000000", false)));

		ArrayList<GroupMemberGuest> gmgs = new ArrayList<>();
		GroupMemberGuest g4 = new GroupMemberGuest();
		g4.setBirthDate(new Date());
		g4.setName("Marala");
		g4.setSurname("Carlona");
		g4.setSex("TF");
		g4.setCittadinanza(new Place("12","ITALIA",true));
		g4.setPlaceOfBirth(DAMBEL);
		gmgs.add(g4);

		g4 = new GroupMemberGuest();
		g4.setBirthDate(new Date());
		g4.setName("Trullo");
		g4.setSurname("frolliani");
		g4.setSex("TF");
		g4.setCittadinanza(new Place("12","ITALIA",true));
		g4.setPlaceOfBirth(DAMBEL);
		gmgs.add(g4);

		SingleGuestCard SGC = new SingleGuestCard(g, new Date(), 5);
		FamilyCard FC = new FamilyCard(g1, fmgs, new Date(), 12);
		GroupCard GC = new GroupCard(g3, gmgs, new Date(), 3);


		this.open();

		Declaration d;

		d = new Declaration(new Date(2016, 4, 1));
		d.add(SGC);
		this.insertDeclaration(d);

		d = new Declaration(new Date(2016, 4, 2));
		d.add(SGC);
		d.add(FC);
		this.insertDeclaration(d);

		d = new Declaration(new Date(2016, 4, 3));
		d.add(SGC);
		d.add(FC);
		d.add(GC);
		this.insertDeclaration(d);

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

					if (this.cache.get(date) != null){
						this.cache.get(date).addAll(dec);
					} else {
						this.cache.put(date, dec);
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
		} catch (FileNotFoundException e){
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
	}

	@Override
	public void close(){
		try {
			this.cache = null;
			this.fos.close();
		} catch (FileNotFoundException e){
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
	}
}
