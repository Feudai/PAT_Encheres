package org.enchere.dal;

import java.util.List;

import org.enchere.bo.ArticleVendu;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ArticleVenduDAOImpl implements ArticleVenduDAO {
	
	private static final String CREATE = "INSERT INTO ARTICLES_VENDUS (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie) VALUES (:nom_article, :description, :date_debut_encheres, :date_fin_encheres, :prix_initial, :prix_vente, :no_utilisateur, :no_categorie)";

	private static final String FIND_BY_ID = "SELECT a.no_article, nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, a.no_utilisateur, no_categorie FROM ARTICLES_VENDUS a INNER JOIN ENCHERES e ON a.no_article = e.no_article WHERE a.no_article =:no_article";
	

	
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	public ArticleVenduDAOImpl(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	//Ici les requêtes
	
	//constructeur jdbc
	
	
	public List<ArticleVendu> findAll(){
	
	return null;
	}
	
	public void create(ArticleVendu article, int noUtilisateur) {
		
		MapSqlParameterSource map = new MapSqlParameterSource();
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		map.addValue("nom_article", article.getNomArticle());
		map.addValue("description", article.getDescription());
		map.addValue("date_debut_encheres", article.getDateDebutEncheres());
		map.addValue("date_fin_encheres", article.getDateFinEncheres());
		map.addValue("prix_initial", article.getMiseAPrix());
		map.addValue("prix_vente", article.getPrixVente());
		map.addValue("no_utilisateur", noUtilisateur);
		map.addValue("no_categorie", article.getCategorieArticle().getNoCategorie());
		
		if (keyHolder != null && keyHolder.getKey() != null) {
			article.setNoArticle(keyHolder.getKey().intValue());
			;
		}
		
		jdbcTemplate.update(CREATE, map, keyHolder);
		
		
	}

	@Override
	public ArticleVendu findById(int noArticle) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("no_article", noArticle);
		
		return jdbcTemplate.queryForObject(FIND_BY_ID, map, new BeanPropertyRowMapper<>(ArticleVendu.class));
	}
}
