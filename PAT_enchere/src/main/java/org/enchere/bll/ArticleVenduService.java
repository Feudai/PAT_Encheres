package org.enchere.bll;

import java.util.List;

import org.enchere.bo.ArticleVendu;

public interface ArticleVenduService {

	
	List<ArticleVendu> ajouterArticle(ArticleVendu article, int noUtilisateur);

	ArticleVendu consulterArticleVenduParId(int noArticle);
}
