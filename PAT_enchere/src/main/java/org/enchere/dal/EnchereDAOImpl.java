package org.enchere.dal;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.enchere.bo.ArticleVendu;
import org.enchere.bo.Categorie;
import org.enchere.bo.Enchere;
import org.enchere.bo.Utilisateur;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EnchereDAOImpl implements EnchereDAO {
	
	private static final String CREATE = "INSERT INTO ENCHERES (no_utilisateur, no_article, date_enchere, montant_enchere) VALUES (:no_utilisateur, :no_article, :date_enchere, :montant_enchere)";
	private static final String FIND_BY_ID="SELECT e.no_utilisateur, e.no_article, e.date_enchere, e.montant_enchere, a.nom_article, a.description, a.date_debut_encheres, a.date_fin_encheres, a.prix_initial, a.prix_vente, a.no_categorie, c.no_categorie, c.libelle, u.no_utilisateur, u.pseudo, u.nom, u.prenom, u.email, u.telephone, u.rue, u.code_postal, u.ville, u.mot_de_passe, u.credit, u.administrateur FROM ENCHERES e INNER JOIN ARTICLES_VENDUS a ON a.no_article = e.no_article INNER JOIN CATEGORIES c ON a.no_categorie = c.no_categorie INNER JOIN UTILISATEURS u ON e.no_utilisateur = u.no_utilisateur WHERE e.no_article=:no_article";
	private static final String FIND_ALL = "SELECT e.no_utilisateur, e.no_article, e.date_enchere, e.montant_enchere, a.nom_article, a.description, a.date_debut_encheres, a.date_fin_encheres, a.prix_initial, a.prix_vente, a.no_categorie, c.no_categorie, c.libelle, u.no_utilisateur, u.pseudo, u.nom, u.prenom, u.email, u.telephone, u.rue, u.code_postal, u.ville, u.mot_de_passe, u.credit, u.administrateur FROM ENCHERES e INNER JOIN ARTICLES_VENDUS a ON a.no_article = e.no_article INNER JOIN CATEGORIES c ON a.no_categorie = c.no_categorie INNER JOIN UTILISATEURS u ON e.no_utilisateur = u.no_utilisateur";
	
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	class EnchereRowMapper implements org.springframework.jdbc.core.RowMapper<Enchere> {

		@Override
		public Enchere mapRow(ResultSet rs, int rowNum) throws SQLException {
			Enchere enchere = new Enchere();
			ArticleVendu article = new ArticleVendu();
			Categorie categorie = new Categorie();
			Utilisateur utilisateur = new Utilisateur();
			
			List<Enchere> listeEncheres = new ArrayList<Enchere>();
			
			categorie.setNoCategorie(rs.getInt("no_categorie"));
			categorie.setLibelle(rs.getString("libelle"));

			article.setCategorieArticle(categorie);
			article.setDateDebutEncheres(rs.getTimestamp("date_debut_encheres").toLocalDateTime());
			article.setDateFinEncheres(rs.getTimestamp("date_fin_encheres").toLocalDateTime());
			article.setDescription(rs.getString("description"));
//			article.setEtatVente(rs.getBoolean(""));
//			article.setLieuRetrait(null);    A IMPLEMENTER
			article.setMiseAPrix(rs.getInt("prix_initial"));
			article.setNoArticle(rs.getInt("no_article"));
			article.setNomArticle(rs.getString("nom_article"));
			article.setPrixVente(rs.getInt("prix_vente"));

			utilisateur.setNoUtilisateur(rs.getInt("no_utilisateur"));
			utilisateur.setPseudo(rs.getString("pseudo"));
			utilisateur.setNom(rs.getString("nom"));
			utilisateur.setPrenom(rs.getString("prenom"));
			utilisateur.setEmail(rs.getString("email"));
			utilisateur.setTelephone(rs.getString("telephone"));
			utilisateur.setRue(rs.getString("rue"));
			utilisateur.setCodePostal(rs.getString("code_postal"));
			utilisateur.setVille(rs.getString("ville"));
			utilisateur.setMotDePasse(rs.getString("mot_de_passe"));
			utilisateur.setCredit(rs.getInt("credit"));
			utilisateur.setAdministrateur(rs.getBoolean("administrateur"));
			
			
			enchere.setArticle(article);
			enchere.setCreateur(utilisateur);
			enchere.setDateEnchere(rs.getTimestamp("date_enchere").toLocalDateTime());
			enchere.setMontantEnchere(rs.getInt("montant_enchere"));

//			listeEncheres.add(enchere);
//			article.setListeEncheres(listeEncheres);

			
			return enchere;
		}
		
	}
	
	public EnchereDAOImpl(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public Enchere findByIdArticle(int idArticle) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("no_article", idArticle);
		
		return jdbcTemplate.queryForObject(FIND_BY_ID, map, new EnchereRowMapper());
	}

	public void create(Enchere enchere){
		MapSqlParameterSource  map = new MapSqlParameterSource();
		
		map.addValue("no_utilisateur", enchere.getCreateur().getNoUtilisateur());
		map.addValue("no_article", enchere.getArticle().getNoArticle());
		map.addValue("date_enchere", enchere.getDateEnchere());
		map.addValue("montant_enchere", enchere.getMontantEnchere());

		
		map.addValue(CREATE, map);
	}

	
	
		public List<Enchere> findAll(){
			
			return jdbcTemplate.query(FIND_ALL, new EnchereRowMapper());
		}
		


}
