package org.enchere.bll;



import java.util.List;

import org.enchere.bo.ArticleVendu;
import org.enchere.dal.ArticleVenduDAO;
import org.springframework.stereotype.Service;

@Service
public class ArticleVenduServiceImpl implements ArticleVenduService {

	private ArticleVenduDAO articleVenduDAO;

	public ArticleVenduServiceImpl(ArticleVenduDAO articleVenduDAO) {
		this.articleVenduDAO = articleVenduDAO;
	}
	
	public List<ArticleVendu> ajouterArticle() {
		
		return this.articleVenduDAO.findAll();
	}
}
