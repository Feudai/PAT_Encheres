package org.enchere.bll;

import java.util.List;

import org.enchere.bo.Enchere;

public interface EnchereService {

	
	public void ajouterEnchere(Enchere enchere);
	
	public List<Enchere> getListeEncheres();

	public List<Enchere> getEncheresByIdArticle(int idArticle);
	
	public Enchere getBestEnchere (int idArticle);

}
