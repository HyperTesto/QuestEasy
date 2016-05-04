package me.hypertesto.questeasy.model.dao;

import java.util.ArrayList;

import me.hypertesto.questeasy.model.Declaration;

/**
 * Created by rigel on 04/05/16.
 */
public interface DeclarationDao {
	void open();
	void close();

	boolean insertDeclaration(Declaration declaration);
	void deleteDeclaration(Declaration declaration);
	ArrayList<Declaration> getAllDeclarations();
}
