package org.enchere.dal;

import java.util.List;

import org.enchere.bo.Utilisateur;

public interface UtilisateurDAO {

	void createUtilisateur(Utilisateur utilisateur);
	
	Utilisateur read (int noUtilisateur);
	Utilisateur read (String pseudo);
	
	List<Utilisateur>findAll();
	List<Utilisateur>findUtilisateur(int noUtilisateur);
<<<<<<< HEAD

	void update(Utilisateur utilisateur);
	
=======
	void deleteUser (int noUtilisateur);
>>>>>>> f3c48479e8f1d2c86e4c5bbc518641d956219dc6
	
	
	

}
