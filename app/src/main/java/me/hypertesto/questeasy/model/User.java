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
}
