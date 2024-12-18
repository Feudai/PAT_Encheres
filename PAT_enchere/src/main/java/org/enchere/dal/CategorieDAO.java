package org.enchere.dal;

import java.util.List;

import org.enchere.bo.Enchere;

public interface CategorieDAO {

	
	public List<Enchere> create(Enchere enchere);

		public List<Enchere> findAll();
}
