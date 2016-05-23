package me.hypertesto.questeasy.model.dao;

import java.util.ArrayList;

import me.hypertesto.questeasy.model.Guest;
import me.hypertesto.questeasy.model.SavedGuest;

/**
 * Created by rigel on 23/05/16.
 */
public interface GuestDao {
	void open();
	void close();
	void clear();

	boolean insertGuest(Guest g);
	boolean updateGuest(Guest g);
	boolean deleteGuest(Guest g);
	ArrayList<SavedGuest> getAllGuests();
}
