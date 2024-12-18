package org.enchere.bll;

import java.util.List;

import org.enchere.bo.Utilisateur;


public interface UtilisateurService {

	void createUser(Utilisateur utilisateur);

	Utilisateur consulterUtilisateurParId(int noUtilisateur);

	List<Utilisateur> consulterUtilisateurs();
<<<<<<< HEAD

	Utilisateur findByUsername(String username);

	void update(Utilisateur utilisateur);
=======
>>>>>>> f3c48479e8f1d2c86e4c5bbc518641d956219dc6
	
	void deleteUser (int noUtilisateur);
}
