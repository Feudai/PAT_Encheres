package org.enchere.dal;

import java.util.List;

import org.enchere.bo.Enchere;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

public interface EnchereDAO {

	List<Enchere> findByIdArticle(int idArticle);

	void create(Enchere enchere);

	List<Enchere> findAll();

	void update(Enchere enchere);

}
