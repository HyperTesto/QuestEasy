package me.hypertesto.questeasy.model;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * Created by rigel on 17/05/16.
 */
public class Place implements Comparable<Place>, Serializable {
	private String id;
	private String name;
	private boolean state;

	public Place(){
		id = "";
		name = "";
		state = false;
	}

	public Place(String id, String name, boolean state){
		this.id = id;
		this.name = name;
		this.state = state;
	}

	public boolean isComplete(){
		if (this.id == null || this.id.equals("")){
			return false;
		} else if (this.name == null || this.name.equals("")){
			return false;
		}

		return true;
	}

	@Override
	public int compareTo(@NonNull Place another){
		return this.name.compareTo(another.getName());
	}

	@Override
	public String toString(){
		return this.name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}
}
