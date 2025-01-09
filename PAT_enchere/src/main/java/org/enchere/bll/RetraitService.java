package org.enchere.bll;


import org.enchere.bo.Retrait;

public interface RetraitService {
	
	public void ajouterRetrait(Retrait retrait);

	public void modifierRetrait(Retrait retrait, int noArticle);

}
