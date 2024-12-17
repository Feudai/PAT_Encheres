package org.enchere.bll;



import org.enchere.dal.CategorieDAO;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
public class CategorieServiceImpl implements CategorieService {

	private CategorieDAO categorieDao;

	public CategorieServiceImpl(CategorieDAO categorieDao) {
		this.categorieDao = categorieDao;
	}
	
	
}
