package org.enchere.dal;

import java.util.List;

import org.enchere.bo.ArticleVendu;
import org.enchere.dal.ArticleVenduDAOImpl.ArticleRowMapper;

public interface ArticleVenduDAO {

	
	List<ArticleVendu> findAll();
	
	void create(ArticleVendu article, int noUtilisateur);

	List<ArticleVendu> findById(int noArticle);

	void modifierNomImage(String cheminImage,int noArticle);

	
	List<ArticleVendu> findAllEmpty();
	
	List<ArticleVendu> findByIdEmpty(int noArticle);



	void modifierArticle(ArticleVendu article, int noArticle);

	void supprimerArticle(ArticleVendu article, int noArticle);

}
