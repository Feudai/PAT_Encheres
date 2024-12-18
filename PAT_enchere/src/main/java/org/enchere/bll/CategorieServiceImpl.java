package org.enchere.bll;



import java.util.List;

import org.enchere.bo.Categorie;
import org.enchere.dal.CategorieDAO;
import org.springframework.stereotype.Service;

@Service
public class CategorieServiceImpl implements CategorieService {

	private CategorieDAO categorieDao;

	public CategorieServiceImpl(CategorieDAO categorieDao) {
		this.categorieDao = categorieDao;
	}
	
public List<Categorie> ajouterCategorie(Categorie categorie) {
		
		return this.categorieDao.findAll();
	}

public List<Categorie> getListeCategories(){
	
	return this.categorieDao.findAll();
}
}
