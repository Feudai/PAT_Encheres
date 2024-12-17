package org.enchere.dal.mock;

import java.util.ArrayList;
import java.util.List;

import org.enchere.bo.Utilisateur;
import org.enchere.dal.UtilisateurDAO;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("dev")
public class UtilisateurDAOBouchon implements UtilisateurDAO {

	public static List<Utilisateur> listeUtilisateur = new ArrayList<>();

	public UtilisateurDAO utilisateurDAO;

	public UtilisateurDAOBouchon(UtilisateurDAO utilisateurDAO) {
		this.utilisateurDAO = utilisateurDAO;
	}

	@Override
	public void createUtilisateur(Utilisateur utilisateur) {
		listeUtilisateur.add(utilisateur);
	}

}
