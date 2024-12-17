package org.enchere.dal;

import java.util.List;

import org.enchere.bo.Enchere;

public interface EnchereDAO {

public List<Enchere> create(Enchere enchere);

		public List<Enchere> findAll();
	
}

