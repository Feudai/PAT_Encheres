package org.enchere.dal;

import java.util.List;

import org.enchere.bo.Categorie;

public interface CategorieDAO {

	
	public List<Categorie> create(Categorie categorie);

		public List<Categorie> findAll();
		
		public Categorie findById(int id);
}
