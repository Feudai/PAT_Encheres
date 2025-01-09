package org.enchere.bll;



import java.util.List;

import org.enchere.bo.ArticleVendu;
import org.enchere.bo.Retrait;
import org.enchere.dal.RetraitDAO;
import org.springframework.stereotype.Service;

@Service
public class RetraitServiceImpl implements RetraitService {

	private RetraitDAO retraitDao;

	public RetraitServiceImpl(RetraitDAO retraitDao) {
		this.retraitDao = retraitDao;
	}

	@Override
	public void ajouterRetrait(Retrait retrait) {
		this.retraitDao.create(retrait);
		
	}

	@Override
	public void modifierRetrait(Retrait retrait, int noArticle) {
		this.retraitDao.modifer(retrait, noArticle);
		
	}


	
}
