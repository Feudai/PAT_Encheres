package org.enchere.dal;

import java.util.List;

import org.enchere.bo.Categorie;

public interface CategorieDAO {

	
	 List<Categorie> create(Categorie categorie);

		 List<Categorie> findAll();
		
		 Categorie findById(int id);
}
