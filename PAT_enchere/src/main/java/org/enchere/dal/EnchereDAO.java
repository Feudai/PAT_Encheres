package org.enchere.dal;

import java.util.List;

import org.enchere.bo.Enchere;

public interface EnchereDAO {

public void create(Enchere enchere);

		 List<Enchere> findAll();
	
}

