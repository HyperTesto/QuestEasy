package me.hypertesto.questeasy.model.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import me.hypertesto.questeasy.model.Declaration;

/**
 * Created by rigel on 04/05/16.
 */
public interface DeclarationDao {
	void open();
	void close();

	boolean insertDeclaration(Declaration declaration);
	boolean updateDeclaration(Declaration declaration);
	Declaration getDeclarationByDate(Date date);
	void deleteDeclaration(Declaration declaration);
	void clear();
	HashMap<Date, Declaration> getAllDeclarations();
}
