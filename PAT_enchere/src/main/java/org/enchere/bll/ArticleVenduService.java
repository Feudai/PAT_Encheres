package org.enchere.bll;

import java.util.List;

import org.enchere.bo.ArticleVendu;

public interface ArticleVenduService {

	List<ArticleVendu> ajouterArticle(ArticleVendu article, int noUtilisateur);

	List<ArticleVendu> consulterArticleVenduParId(int noArticle);

	List<ArticleVendu> getListeArticles();

	List<ArticleVendu> getListeArticlesEmpty();


	List<ArticleVendu> consulterArticleVenduEmptyParId(int noArticle);

	void modifierNomImage(String cheminImage, int noArticle);


	void modifierArticle(ArticleVendu article, int noArticle);

	void supprimerArtice(ArticleVendu article, int noArticle);

	

}
