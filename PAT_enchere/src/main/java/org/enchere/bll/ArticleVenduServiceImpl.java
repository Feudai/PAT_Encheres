package org.enchere.bll;



import org.enchere.dal.ArticleVenduDAO;

import org.springframework.stereotype.Service;

@Service
public class ArticleVenduServiceImpl implements ArticleVenduService {

	private ArticleVenduDAO articleVenduDAO;

	public ArticleVenduServiceImpl(ArticleVenduDAO articleVenduDAO) {
		this.articleVenduDAO = articleVenduDAO;
	}
	
}
