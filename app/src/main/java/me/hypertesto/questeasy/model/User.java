package me.hypertesto.questeasy.model;

import java.io.Serializable;

/**
 * Created by rigel on 25/05/16.
 */
public class User implements Serializable {

	private String name;
	private String email;

	public User(){}

	public User(String name, String email){
		this.name = name;
		this.email = email;
	}

	@Override
	public boolean equals(Object o){
		if (o != null){
			if (o instanceof User){
				User u = (User) o;
				return (this.name.equals(u.getName()) && this.email.equals(u.getEmail()));
			}
		}

		return false;
	}

	@Override
	public int hashCode(){
		int res = this.name.hashCode();
		res = res * 31;
		res = res + this.email.hashCode();
		return res;
	}

	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getEmail(){
		return email;
	}

	public void setEmail(String email){
		this.email = email;
	}
}
