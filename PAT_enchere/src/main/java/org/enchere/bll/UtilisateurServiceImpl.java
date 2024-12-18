package org.enchere.bll;

import java.util.List;

import org.enchere.bo.Utilisateur;
import org.enchere.dal.UtilisateurDAO;
import org.enchere.exceptions.BusinessException;
import org.springframework.stereotype.Service;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

	private UtilisateurDAO utilisateurDao;

	public UtilisateurServiceImpl(UtilisateurDAO utilisateurDao) {
		this.utilisateurDao = utilisateurDao;
	}

	@Override
	public void createUser(Utilisateur utilisateur) throws BusinessException {
		BusinessException be = new BusinessException();

		boolean valide = validerEmailUnique(utilisateur.getEmail(), be);
		if (valide) {
			utilisateurDao.createUtilisateur(utilisateur);
		} else {
			throw be;
		}

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

	private boolean validerEmailUnique(String email, BusinessException be) {

		boolean emailExiste = utilisateurDao.validerEmailUnique(email);

		if (emailExiste) {
			be.addMessage("Vous ne pouvez pas créer un utilisateur avec un email déjà existant");
		}

		return !emailExiste;
	}

}
