package org.enchere.dal;

import java.util.List;

import org.enchere.bo.Utilisateur;

public interface UtilisateurDAO {

	void createUtilisateur(Utilisateur utilisateur);
	
	Utilisateur read (int noUtilisateur);
	Utilisateur read (String pseudo);
	
	List<Utilisateur>findAll();
	List<Utilisateur>findUtilisateur(int noUtilisateur);


	void update(Utilisateur utilisateur);

	void deleteUser(int noUtilisateur);
	


	
	
	

}
