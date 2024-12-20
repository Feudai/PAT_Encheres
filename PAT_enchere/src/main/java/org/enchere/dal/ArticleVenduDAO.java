package org.enchere.dal;

import java.util.List;

import org.enchere.bo.ArticleVendu;

public interface ArticleVenduDAO {

	
	List<ArticleVendu> findAll();
	
	void create(ArticleVendu article, int noUtilisateur);

	List<ArticleVendu> findById(int noArticle);
}
