package org.enchere.bll;

import org.enchere.dal.ArticleVenduDAO;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class ArticleVenduServiceImpl implements ArticleVenduService {

	private ArticleVenduDAO articleVenduDAO;

	public ArticleVenduServiceImpl(ArticleVenduDAO articleVenduDAO) {
		this.articleVenduDAO = articleVenduDAO;
	}

}
