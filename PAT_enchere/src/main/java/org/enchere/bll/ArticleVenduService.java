package org.enchere.bll;

import java.util.List;

import org.enchere.bo.ArticleVendu;

public interface ArticleVenduService {

	
	List<ArticleVendu> ajouterArticle(ArticleVendu article, int noUtilisateur);

	List<ArticleVendu> consulterArticleVenduParId(int noArticle);

	void modifierNomImage(String cheminImage,int noArticle);
}
