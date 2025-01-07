package org.enchere.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.enchere.bo.ArticleVendu;
import org.enchere.bo.Categorie;
import org.enchere.bo.Enchere;
import org.enchere.bo.Retrait;
import org.enchere.bo.Utilisateur;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ArticleVenduDAOImpl implements ArticleVenduDAO {
	


	private static final String CREATE = "INSERT INTO ARTICLES_VENDUS (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie, chemin_image) VALUES (:nom_article, :description, :date_debut_encheres, :date_fin_encheres, :prix_initial, :prix_vente, :no_utilisateur, :no_categorie, :chemin_image)";
	private static final String FIND_BY_ID = "SELECT a.no_article, nom_article, a.description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, a.no_utilisateur, a.no_categorie, chemin_image, c.libelle, e.montant_enchere, r.rue,r.code_postal, r.ville  FROM ARTICLES_VENDUS a LEFT JOIN ENCHERES e ON a.no_article = e.no_article INNER JOIN CATEGORIES c ON a.no_categorie = c.no_categorie LEFT JOIN RETRAITS r ON a.no_article = r.no_article WHERE a.no_article =:no_article";


	
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	public ArticleVenduDAOImpl(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	
	
	public List<ArticleVendu> findAll(){
	
	return null;
	}
	
	class ArticleRowMapper implements RowMapper<ArticleVendu>{

		@Override
		public ArticleVendu mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			ArticleVendu a = new ArticleVendu();
			Enchere e = new Enchere();
			Categorie c = new Categorie();
			Utilisateur u = new Utilisateur();
			Retrait r = new Retrait();
			
			List<Enchere> listeEncheres = new ArrayList<>();
			a.setCheminImage(rs.getString("chemin_image"));
			u.setNoUtilisateur(rs.getInt("no_utilisateur"));
			a.setCheminImage(rs.getString("chemin_image"));
			a.setCreateur(u);
			a.setNoArticle(rs.getInt("no_article"));
			a.setNomArticle(rs.getString("nom_article"));
			a.setDescription(rs.getString("description"));
			a.setDateDebutEncheres(rs.getTimestamp("date_debut_encheres").toLocalDateTime());
			a.setDateFinEncheres(rs.getTimestamp("date_fin_encheres").toLocalDateTime());
			a.setMiseAPrix(rs.getInt("prix_initial"));
			a.setPrixVente(rs.getInt("prix_vente"));
			c.setLibelle(rs.getString("libelle"));
			a.setCategorieArticle(c);
			e.setMontantEnchere(rs.getInt("montant_enchere"));
			e.setCreateur(u);
			e.setDateEnchere(rs.getTimestamp("date_debut_encheres").toLocalDateTime());
			r.setRue(rs.getString("rue"));
			r.setCodePostal(rs.getString("code_postal"));
			r.setVille(rs.getString("ville"));
			a.setLieuRetrait(r);
			e.setArticle(a);
			
			
			
			listeEncheres.add(e);
			a.setListeEncheres(listeEncheres);
			
			
			return a;
		}
		
		
		
		
	}
	
	
	
	public void create(ArticleVendu article, int noUtilisateur) {
		
		MapSqlParameterSource map = new MapSqlParameterSource();
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		map.addValue("nom_article", article.getNomArticle());
		map.addValue("description", article.getDescription());
		map.addValue("date_debut_encheres", article.getDateDebutEncheres());
		map.addValue("date_fin_encheres", article.getDateFinEncheres());
		map.addValue("prix_initial", article.getMiseAPrix());
		map.addValue("prix_vente", article.getMiseAPrix());
		map.addValue("no_utilisateur", noUtilisateur);
		map.addValue("no_categorie", article.getCategorieArticle().getNoCategorie());
		map.addValue("chemin_image", article.getCheminImage());
		
		jdbcTemplate.update(CREATE, map, keyHolder);
		

		if (keyHolder != null && keyHolder.getKey() != null) {
			article.setNoArticle(keyHolder.getKey().intValue());
			
		}

	}

	@Override
	public List<ArticleVendu> findById(int noArticle) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("no_article", noArticle);
		
		return jdbcTemplate.query(FIND_BY_ID, map, new ArticleRowMapper());
	}
}
