package org.enchere.bll;

import java.util.List;

import org.enchere.bo.Utilisateur;
import org.enchere.dal.UtilisateurDAO;
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

	@Override
	public Utilisateur consulterUtilisateurParId(int noUtilisateur) {
		Utilisateur utilisateur = this.utilisateurDao.read(noUtilisateur);

		return utilisateur;
	}

	@Override
	public List<Utilisateur> consulterUtilisateurs() {
		System.out.println(this.utilisateurDao.findAll());
		return this.utilisateurDao.findAll();
	}

<<<<<<< HEAD
<<<<<<< HEAD
=======

	
	
	
	
	
	
=======
>>>>>>> 86ff5cc22236183e1a9c91b252fd2fc95ca87710

>>>>>>> 6eb84023186edb6537516f76cd83a32f7b537616
	public Utilisateur findByUsername(String username) {
		Utilisateur utilisateur = this.utilisateurDao.read(username);

		return utilisateur;

	}

	@Override
	public void update(Utilisateur utilisateur) {
		utilisateurDao.update(utilisateur);

	}

	@Override
	public void deleteUser(int noUtilisateur) {
		utilisateurDao.deleteUser(noUtilisateur);
		
	}

}
