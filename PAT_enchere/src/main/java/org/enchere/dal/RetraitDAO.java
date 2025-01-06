package org.enchere.dal;

import java.util.List;

import org.enchere.bo.ArticleVendu;
import org.enchere.bo.Retrait;

public interface RetraitDAO {

	void create(Retrait retrait);

	List<Retrait> findAll();

}
