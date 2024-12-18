package org.enchere.bll;

import java.util.List;

import org.enchere.bo.Utilisateur;
import org.enchere.exceptions.BusinessException;

public interface UtilisateurService {

	void createUser(Utilisateur utilisateur) throws BusinessException;

	Utilisateur consulterUtilisateurParId(int noUtilisateur);

	List<Utilisateur> consulterUtilisateurs();

	Utilisateur findByUsername(String username);

	void update(Utilisateur utilisateur);

	void deleteUser(int noUtilisateur);

	
}
