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

import me.hypertesto.questeasy.model.Declaration;
import me.hypertesto.questeasy.model.Documento;
import me.hypertesto.questeasy.model.FamilyCard;
import me.hypertesto.questeasy.model.FamilyHeadGuest;
import me.hypertesto.questeasy.model.FamilyMemberGuest;
import me.hypertesto.questeasy.model.GroupCard;
import me.hypertesto.questeasy.model.GroupHeadGuest;
import me.hypertesto.questeasy.model.GroupMemberGuest;
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

	public FSDeclarationDao(Context context){
		this.context = context;
	}

	@Override
	public boolean insertDeclaration(Declaration declaration){
		try {
			fos.writeObject(declaration);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public ArrayList<Declaration> getAllDeclarations(){
		ArrayList<Declaration> res = new ArrayList<>();
		try {
			fis = new ObjectInputStream(context.openFileInput(FILENAME));
		} catch (IOException e) {
			e.printStackTrace();
			return res;
		}

		while (true){
			try {
				Object o = fis.readObject();
				Declaration dec;

				if (o instanceof Declaration){
					dec = (Declaration) o;
					res.add(dec);
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

		return res;
	}

	@Override
	public void deleteDeclaration(Declaration declaration){
		System.out.println("NOT YET IMPLEMENTED");
	}

	public void clear(){
		context.deleteFile(FILENAME);
	}

	public void populate(){
		Declaration d = new Declaration(new Date(), true);
		SingleGuest g = new SingleGuest();
		g.setBirthDate(new Date());
		g.setName("Paolo");
		g.setSurname("Rossi");
		g.setSex("MF");
		g.setCittadinanza("Italia");
		g.setComuneDiNascita("Dambel");
		g.setProvinciaDiNascita("TN");
		g.setStatoDiNascita("ITA");
		g.setDocumento(new Documento("a", "b", "c"));
		d.add(new SingleGuestCard(g, new Date(), 5, true));

		Declaration d1 = new Declaration(new Date(), true);
		FamilyHeadGuest g1 = new FamilyHeadGuest();
		g1.setBirthDate(new Date());
		g1.setName("Manilo");
		g1.setSurname("Carlini");
		g1.setSex("TF");
		g1.setCittadinanza("Italia");
		g1.setComuneDiNascita("Dambel");
		g1.setProvinciaDiNascita("TN");
		g1.setStatoDiNascita("ITA");
		g1.setDocumento(new Documento("e", "d", "c"));

		ArrayList<FamilyMemberGuest> fmgs = new ArrayList<>();
		FamilyMemberGuest g2 = new FamilyMemberGuest();
		g2.setBirthDate(new Date());
		g2.setName("Carlo");
		g2.setSurname("Carlini");
		g2.setSex("TF");
		g2.setCittadinanza("Italia");
		g2.setComuneDiNascita("Dambel");
		g2.setProvinciaDiNascita("TN");
		g2.setStatoDiNascita("ITA");
		fmgs.add(g2);

		g2 = new FamilyMemberGuest();
		g2.setBirthDate(new Date());
		g2.setName("Rallo");
		g2.setSurname("Lorlini");
		g2.setSex("TF");
		g2.setCittadinanza("Italia");
		g2.setComuneDiNascita("Dambel");
		g2.setProvinciaDiNascita("TN");
		g2.setStatoDiNascita("ITA");
		fmgs.add(g2);

		d1.add(new SingleGuestCard(g, new Date(), 5, true));
		d1.add(new FamilyCard(g1, fmgs, new Date(), 5, true));

		Declaration d2 = new Declaration(new Date(), true);
		GroupHeadGuest g3 = new GroupHeadGuest();
		g3.setBirthDate(new Date());
		g3.setName("Manilo");
		g3.setSurname("Carlini");
		g3.setSex("TF");
		g3.setCittadinanza("Italia");
		g3.setComuneDiNascita("Dambel");
		g3.setProvinciaDiNascita("TN");
		g3.setStatoDiNascita("ITA");
		g3.setDocumento(new Documento("e", "d", "c"));

		ArrayList<GroupMemberGuest> gmgs = new ArrayList<>();
		GroupMemberGuest g4 = new GroupMemberGuest();
		g4.setBirthDate(new Date());
		g4.setName("Carlo");
		g4.setSurname("Carlini");
		g4.setSex("TF");
		g4.setCittadinanza("Italia");
		g4.setComuneDiNascita("Dambel");
		g4.setProvinciaDiNascita("TN");
		g4.setStatoDiNascita("ITA");
		gmgs.add(g4);

		g4 = new GroupMemberGuest();
		g4.setBirthDate(new Date());
		g4.setName("Rallo");
		g4.setSurname("Lorlini");
		g4.setSex("TF");
		g4.setCittadinanza("Italia");
		g4.setComuneDiNascita("Dambel");
		g4.setProvinciaDiNascita("TN");
		g4.setStatoDiNascita("ITA");
		gmgs.add(g4);

		d2.add(new SingleGuestCard(g, new Date(), 5, true));
		d2.add(new FamilyCard(g1, fmgs, new Date(), 23, true));
		d2.add(new GroupCard(g3, gmgs, new Date(), 7, true));

		this.open();
		this.insertDeclaration(d);
		this.insertDeclaration(d1);
		this.insertDeclaration(d2);
		this.close();
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
		} catch (FileNotFoundException e){
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
	}

	@Override
	public void close(){
		try {
			this.fos.close();
		} catch (FileNotFoundException e){
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
	}
}
