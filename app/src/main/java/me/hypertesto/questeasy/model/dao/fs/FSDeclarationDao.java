package me.hypertesto.questeasy.model.dao.fs;

import android.content.Context;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import me.hypertesto.questeasy.model.Declaration;
import me.hypertesto.questeasy.model.dao.DeclarationDao;

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

	@Override
	public void open(){
		try {
			this.fos = new ObjectOutputStream(context.openFileOutput(FILENAME, Context.MODE_APPEND));
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
