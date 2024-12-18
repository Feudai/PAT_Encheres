package org.enchere.dal;

import java.util.List;

import org.enchere.bo.ArticleVendu;

public interface ArticleVenduDAO {

	
	List<ArticleVendu> findAll();
	
	List<ArticleVendu> create(ArticleVendu article);
}
