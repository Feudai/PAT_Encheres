package org.enchere.dal;

import org.enchere.bo.Utilisateur;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("sql")
public class UtilisateurDAOImpl implements UtilisateurDAO {

	
	@Override
	public void createUtilisateur(Utilisateur utilisateur) {
		// TODO Auto-generated method stub
		
	}

}
