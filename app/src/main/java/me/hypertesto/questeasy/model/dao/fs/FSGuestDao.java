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
import java.util.List;

import me.hypertesto.questeasy.model.Declaration;
import me.hypertesto.questeasy.model.Guest;
import me.hypertesto.questeasy.model.SavedGuest;
import me.hypertesto.questeasy.model.dao.GuestDao;
import me.hypertesto.questeasy.model.other.AppendingObjectOutputStream;

/**
 * Created by rigel on 23/05/16.
 */
public class FSGuestDao implements GuestDao {
	private ObjectInputStream fis;
	private ObjectOutputStream fos;
	private Context context;
	private static final String FILENAME = "guests.ser";

	private ArrayList<SavedGuest> cache;

	public FSGuestDao(Context context){
		this.context = context;
		this.cache = null;
	}

	@Override
	public boolean insertGuest(Guest guest){
		SavedGuest sg = new SavedGuest(guest);

		try {
			if (this.cache.contains(sg)){
				return this.updateGuest(guest);
			} else {
				fos.writeObject(sg);
				this.cache.add(sg);
			}
		} catch (IOException e){
			throw new RuntimeException(e);
		}

		return true;
	}

	@Override
	public boolean updateGuest(Guest guest){
		this.deleteGuest(guest);
		return this.insertGuest(guest);
	}

	@Override
	public boolean deleteGuest(Guest guest){
		SavedGuest sg = new SavedGuest(guest);

		if (this.cache.contains(sg)){
			ArrayList<SavedGuest> allGuests = this.getAllGuests();
			allGuests.remove(sg);

			this.close();
			this.clear();
			this.open();

			for (SavedGuest g : allGuests){
				this.insertGuest(g);
			}
		} else {
			return false;
		}

		return true;
	}

	@Override
	public ArrayList<SavedGuest> getAllGuests(){
		ArrayList<SavedGuest> res = new ArrayList<>();
		res.addAll(this.cache);
		return res;
	}

	private void updateCache(){
		this.cache = new ArrayList<>();

		try {
			fis = new ObjectInputStream(context.openFileInput(FILENAME));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		while (true){
			try {
				Object o = fis.readObject();
				SavedGuest g;

				if (o instanceof SavedGuest){
					g = (SavedGuest) o;
					this.cache.add(g);

				} else {
					throw new RuntimeException("Saved file corrupted");
				}
			} catch (EOFException ex){
				break;
			} catch (IOException | ClassNotFoundException ex){
				throw new RuntimeException(ex);
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

	@Override
	public void clear(){
		context.deleteFile(FILENAME);
		this.cache = null;
	}
}
