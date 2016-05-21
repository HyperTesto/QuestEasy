package me.hypertesto.questeasy.model;

import java.io.Serializable;

/**
 * Created by hypertesto on 21/05/16.
 */
public class DocumentType implements Comparable<DocumentType>, Serializable {

	private String code;
	private String name;

	public DocumentType(){}

	public DocumentType(String name, String code) {
		this.name = name;
		this.code = code;
	}

	public boolean isComplete(){
		if (this.code == null || this.code.equals("")){
			return false;
		} else if (this.name == null || this.code.equals("")) {
			return false;
		}

		return true;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int compareTo(DocumentType another) {
		return this.name.compareTo(another.getName());
	}
}
