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
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ArticleVenduDAOImpl implements ArticleVenduDAO {

	private static final String CREATE = "INSERT INTO ARTICLES_VENDUS (nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie, chemin_image) VALUES (:nom_article, :description, :date_debut_encheres, :date_fin_encheres, :prix_initial, :prix_vente, :no_utilisateur, :no_categorie, :chemin_image)";
	private static final String FIND_BY_ID = "SELECT a.no_article, nom_article, a.description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, a.no_utilisateur, a.no_categorie, chemin_image, u.pseudo, c.libelle, e.montant_enchere, e.id_enchere, r.rue,r.code_postal, r.ville  FROM ARTICLES_VENDUS a LEFT JOIN ENCHERES e ON a.no_article = e.no_article INNER JOIN CATEGORIES c ON a.no_categorie = c.no_categorie LEFT JOIN RETRAITS r ON a.no_article = r.no_article INNER JOIN UTILISATEURS u ON a.no_utilisateur=u.no_utilisateur WHERE a.no_article =:no_article";
	private static final String MODIF_NOM = "UPDATE [ENCHERES].[dbo].[ARTICLES_VENDUS] SET [chemin_image] = :chemin_image WHERE [no_article] = :no_article ";
	private static final String FIND_ALL = "SELECT a.no_article, a.nom_article, a.description, a.date_debut_encheres, a.date_fin_encheres, a.prix_initial, a.prix_vente, a.no_utilisateur, a.no_categorie, a.chemin_image, u.pseudo, c.libelle, e.montant_enchere, e.id_enchere, r.rue,r.code_postal, r.ville  FROM ARTICLES_VENDUS a INNER JOIN ENCHERES e ON a.no_article = e.no_article INNER JOIN CATEGORIES c ON a.no_categorie = c.no_categorie INNER JOIN RETRAITS r ON a.no_article = r.no_article INNER JOIN UTILISATEURS u ON a.no_utilisateur=u.no_utilisateur";
	private static final String FIND_ALL_EMPTY = "SELECT a.no_article, a.nom_article, a.description, a.date_debut_encheres, a.date_fin_encheres, a.prix_initial, a.prix_vente, a.no_utilisateur, a.no_categorie, a.chemin_image, u.pseudo, c.libelle, r.rue,r.code_postal, r.ville  FROM ARTICLES_VENDUS a INNER JOIN CATEGORIES c ON a.no_categorie = c.no_categorie INNER JOIN RETRAITS r ON a.no_article = r.no_article INNER JOIN UTILISATEURS u ON a.no_utilisateur=u.no_utilisateur";
	private static final String FIND_BY_ID_EMPTY = "SELECT a.no_article, nom_article, a.description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, a.no_utilisateur, a.no_categorie, chemin_image, u.pseudo, c.libelle, r.rue,r.code_postal, r.ville  FROM ARTICLES_VENDUS a INNER JOIN CATEGORIES c ON a.no_categorie = c.no_categorie LEFT JOIN RETRAITS r ON a.no_article = r.no_article INNER JOIN UTILISATEURS u ON a.no_utilisateur=u.no_utilisateur WHERE a.no_article =:no_article";
	private static final String MODIF_ARTICLE = "UPDATE ARTICLES_VENDUS SET date_debut_encheres = :date_debut_encheres, date_fin_encheres = :date_fin_encheres, description = :description, no_categorie = :no_categorie, nom_article = :nom_article, prix_initial = :prix_initial, prix_vente = :prix_vente , chemin_image = :chemin_image WHERE no_article = :no_article";

	
	private NamedParameterJdbcTemplate jdbcTemplate;

	public ArticleVenduDAOImpl(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<ArticleVendu> findAll() {

		return jdbcTemplate.query(FIND_ALL, new ArticleRowMapper());
	}
	
	//For articles with no bids
	public List<ArticleVendu> findAllEmpty() {
		return jdbcTemplate.query(FIND_ALL_EMPTY, new ArticleEmptyRowMapper());
	}
	
	

	class ArticleRowMapper implements RowMapper<ArticleVendu> {

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
			u.setPseudo(rs.getString("pseudo"));
			a.setCreateur(u);
			a.setNoArticle(rs.getInt("no_article"));
			a.setNomArticle(rs.getString("nom_article"));
			a.setDescription(rs.getString("description"));
			a.setDateDebutEncheres(rs.getTimestamp("date_debut_encheres").toLocalDateTime());
			a.setDateFinEncheres(rs.getTimestamp("date_fin_encheres").toLocalDateTime());
			a.setMiseAPrix(rs.getInt("prix_initial"));
			a.setPrixVente(rs.getInt("prix_vente"));
			c.setLibelle(rs.getString("libelle"));
			c.setNoCategorie(rs.getInt("no_categorie"));
			a.setCategorieArticle(c);
			e.setMontantEnchere(rs.getInt("montant_enchere"));
			e.setCreateur(u);
			e.setDateEnchere(rs.getTimestamp("date_debut_encheres").toLocalDateTime());
			e.setIdEnchere(rs.getInt("id_enchere"));
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
	
	class ArticleEmptyRowMapper implements RowMapper<ArticleVendu> {

		@Override
		public ArticleVendu mapRow(ResultSet rs, int rowNum) throws SQLException {

			ArticleVendu a = new ArticleVendu();
			Categorie c = new Categorie();
			Utilisateur u = new Utilisateur();
			Retrait r = new Retrait();

			a.setCheminImage(rs.getString("chemin_image"));
			u.setNoUtilisateur(rs.getInt("no_utilisateur"));
			u.setPseudo(rs.getString("pseudo"));
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
			r.setRue(rs.getString("rue"));
			r.setCodePostal(rs.getString("code_postal"));
			r.setVille(rs.getString("ville"));
			a.setLieuRetrait(r);
						
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
	
	//For articles with no bids
	@Override
	public List<ArticleVendu> findByIdEmpty(int noArticle) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("no_article", noArticle);

		return jdbcTemplate.query(FIND_BY_ID_EMPTY, map, new ArticleEmptyRowMapper());
	}

	@Override
	public void modifierNomImage(String cheminImage,int noArticle) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("chemin_image", cheminImage);
		map.addValue("no_article", noArticle);
		
		this.jdbcTemplate.update(MODIF_NOM, map);
		
	}

	@Override
	public void modifierArticle(ArticleVendu article, int noArticle) {
		
		MapSqlParameterSource map = new MapSqlParameterSource();

		map.addValue("no_article", noArticle);
		map.addValue("nom_article", article.getNomArticle());
		map.addValue("description", article.getDescription());
		map.addValue("date_debut_encheres", article.getDateDebutEncheres());
		map.addValue("date_fin_encheres", article.getDateFinEncheres());
		map.addValue("prix_initial", article.getMiseAPrix());
		map.addValue("prix_vente", article.getMiseAPrix());
		map.addValue("no_categorie", article.getCategorieArticle().getNoCategorie());
		map.addValue("chemin_image", article.getCheminImage());

		this.jdbcTemplate.update(MODIF_ARTICLE, map);
	}
}
