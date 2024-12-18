package org.enchere.bll;

import java.util.List;

import org.enchere.bo.Utilisateur;


public interface UtilisateurService {

	void createUser(Utilisateur utilisateur);

	Utilisateur consulterUtilisateurParId(int noUtilisateur);

	List<Utilisateur> consulterUtilisateurs();
	
	void deleteUser (int noUtilisateur);
}
