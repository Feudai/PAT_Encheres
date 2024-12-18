package org.enchere.bll;

import java.util.List;

import org.enchere.bo.Categorie;
import org.enchere.bo.Enchere;

public interface CategorieService {

	
public List<Categorie> ajouterCategorie(Categorie categorie);

public List<Categorie> getListeCategories();

public Categorie getCategorieById(int id);

}
