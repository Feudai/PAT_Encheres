package org.enchere.dal;

import java.util.List;

import org.enchere.bo.ArticleVendu;
import org.enchere.bo.Retrait;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class RetraitDAOImpl implements RetraitDAO {

	private static final String FIND_ALL="SELECT rue, code_postal, ville FROM RETRAITS";
	private static final String CREATE = "INSERT INTO RETRAITS (no_article, rue, code_postal, ville) VALUES (:no_article, :rue, :code_postal, :ville)";
	
	private NamedParameterJdbcTemplate jdbcTemplate;
	
    public RetraitDAOImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
	@Override
	public void create(Retrait retrait) {
		
		MapSqlParameterSource map = new MapSqlParameterSource();
		
		map.addValue("no_article", retrait.getArticle().getNoArticle());
		System.out.println(retrait.getArticle().getNoArticle());
		map.addValue("rue", retrait.getRue());
		map.addValue("code_postal", retrait.getCodePostal());
		map.addValue("ville", retrait.getVille());
		
		jdbcTemplate.update(CREATE, map);
			
	}

	@Override
	public List<Retrait> findAll() {
	
		return jdbcTemplate.query(FIND_ALL, new BeanPropertyRowMapper<>(Retrait.class)) ;
	}

}
