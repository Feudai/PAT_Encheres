package org.enchere.bll;

import org.enchere.bo.Utilisateur;
import org.enchere.dal.UtilisateurDAO;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

	
	private UtilisateurDAO utilisateurDao;

	public UtilisateurServiceImpl(UtilisateurDAO utilisateurDao) {
		this.utilisateurDao = utilisateurDao;
	}

	@Override
	public void createUser(Utilisateur utilisateur) {
		utilisateurDao.createUtilisateur(utilisateur);
		
	}
	
	
}
