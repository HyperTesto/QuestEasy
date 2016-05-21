package me.hypertesto.questeasy.model;

/**
 * Created by hypertesto on 21/05/16.
 */
public class DocumentType implements Comparable<DocumentType> {

	private String code;
	private String name;

	public DocumentType() {}

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

	@Override
	public String toString() { return this.name; }
}
