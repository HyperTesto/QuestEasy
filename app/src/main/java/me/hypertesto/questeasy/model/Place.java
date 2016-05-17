package me.hypertesto.questeasy.model;

/**
 * Created by rigel on 17/05/16.
 */
public class Place implements Comparable<Place> {
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

	@Override
	public int compareTo(Place another){
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
