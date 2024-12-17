package org.enchere.bll;

import java.util.List;

import org.enchere.bo.Enchere;

public interface EnchereService {

	
	public List<Enchere> ajouterEnchere(Enchere enchere);
	
	public List<Enchere> getListeEncheres();


}
