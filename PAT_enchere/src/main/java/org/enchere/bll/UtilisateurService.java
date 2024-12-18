package org.enchere.bll;

import java.util.List;

import org.enchere.bo.Utilisateur;

public interface UtilisateurService {

	void createUser(Utilisateur utilisateur);

	Utilisateur consulterUtilisateurParId(int noUtilisateur);

	List<Utilisateur> consulterUtilisateurs();

	Utilisateur findByUsername(String username);

	void update(Utilisateur utilisateur);

<<<<<<< HEAD
<<<<<<< HEAD
	void deleteUser(int noUtilisateur);
=======

>>>>>>> 6eb84023186edb6537516f76cd83a32f7b537616
=======

>>>>>>> 86ff5cc22236183e1a9c91b252fd2fc95ca87710

}
