package org.enchere.dal;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.enchere.bo.ArticleVendu;
import org.enchere.bo.Enchere;
import org.enchere.bo.Utilisateur;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EnchereDAOImpl implements EnchereDAO {
	
	private static final String CREATE = "INSERT INTO ENCHERES (no_utilisateur, no_article, date_enchere, montant_enchere) VALUES (:no_utilisateur, :no_article, :date_enchere, :montant_enchere)";
	private static final String FIND_BY_ID="SELECT e.no_utilisateur, e.no_article, e.date_enchere, e.montant_enchere, a.nom_article, a.description, a.date_debut_encheres, a.date_fin_encheres, a.prix_initial, a.prix_vente, no_categorie FROM ENCHERES e INNER JOIN ARTICLES_VENDUS a ON a.no_article = e.no_article WHERE e.id=:id";
	private static final String FIND_ALL = "SELECT (no_utilisateur, no_article, date_enchere, montant_enchere) FROM ENCHERES";
	
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	
	public EnchereDAOImpl(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public Enchere findById(int id) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("id", id);
		
		return jdbcTemplate.queryForObject(FIND_BY_ID, map, new BeanPropertyRowMapper<>(Enchere.class));
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
			
			return jdbcTemplate.query(FIND_ALL, new BeanPropertyRowMapper<>(Enchere.class));
		}
		


}
