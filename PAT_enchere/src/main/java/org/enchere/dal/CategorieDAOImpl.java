package org.enchere.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.enchere.bo.Categorie;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CategorieDAOImpl implements CategorieDAO {
	
	private static final String FIND_ALL="SELECT no_categorie,libelle FROM CATEGORIES";
	
	private NamedParameterJdbcTemplate jdbcTemplate;

	class CategorieRowMapper implements RowMapper<Categorie>{

		@Override
		public Categorie mapRow(ResultSet rs, int rowNum) throws SQLException {
			
			Categorie c = new Categorie();
			
			c.setNoCategorie(rs.getInt("no_categorie"));
			c.setLibelle(rs.getString("libelle"));

			return c;
		}
		
	}
	
	public CategorieDAOImpl(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<Categorie> create(Categorie categorie){
		
		return null;
	}

		public List<Categorie> findAll(){
			
			return jdbcTemplate.query(FIND_ALL, new CategorieRowMapper());
		}

		@Override
		public Categorie findById(int id) {
			Categorie categorie = this.findAll().stream().filter(c->c.getNoCategorie()==id).findFirst().orElse(null);
			return categorie;
		}
}
