package org.enchere.bll;



import java.util.List;

import org.enchere.bo.ArticleVendu;
import org.enchere.bo.Enchere;
import org.enchere.dal.EnchereDAO;
import org.springframework.stereotype.Service;

@Service
public class EnchereServiceImpl implements EnchereService {

	private EnchereDAO enchereDao;

	public EnchereServiceImpl(EnchereDAO enchereDao) {
		this.enchereDao = enchereDao;
	}
	
public void ajouterEnchere(Enchere enchere) {
		
		this.enchereDao.create(enchere);
	}

public Enchere getEnchereByIdArticle(int idArticle) {
	return this.enchereDao.findByIdArticle(idArticle);
}

public List<Enchere> getListeEncheres(){
	
	return this.enchereDao.findAll();
}
	
}
