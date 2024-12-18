package org.enchere.dal;

import java.util.List;

import org.enchere.bo.ArticleVendu;
import org.springframework.stereotype.Repository;

@Repository
public class ArticleVenduDAOImpl implements ArticleVenduDAO {
	
	private static final String CREATE = "INSERT no_article, nom_article, description, date_debut_enchere, date_fin_enchere, prix_initial, prix_vente, no_utilisateur, no_categorie INTO ARTICLES_VENDUS VALUES (:no_article, :nom_article, :description, :date_debut_enchere, :date_fin_enchere, :prix_initial, :prix_vente, :no_utilisateur, :no_categorie)";

	//Ici les requêtes
	
	//constructeur jdbc
	
	
	public List<ArticleVendu> findAll(){
	
	return null;
	}
	
	public List<ArticleVendu> create(ArticleVendu article) {
		return null;
	}
}
