package org.enchere.dal;

import java.util.List;

import org.enchere.bo.Utilisateur;

public interface UtilisateurDAO {

	void createUtilisateur(Utilisateur utilisateur);
	
	Utilisateur read (int noUtilisateur);
	
	List<Utilisateur>findAll();
	
	

}
