package org.enchere.dal;

import java.util.List;

import org.enchere.bo.ArticleVendu;
import org.enchere.bo.Retrait;

public interface RetraitDAO {

	void create(Retrait retrait);

	List<Retrait> findAll();

	void modifer(Retrait retrait, int noArticle);

	void supprimer(Retrait retrait, int noArticle);

}
