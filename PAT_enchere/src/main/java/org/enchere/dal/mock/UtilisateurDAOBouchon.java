package org.enchere.dal.mock;

import java.util.ArrayList;
import java.util.List;

import org.enchere.bo.Utilisateur;
import org.enchere.dal.UtilisateurDAO;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class UtilisateurDAOBouchon implements UtilisateurDAO {

	public static List<Utilisateur> listeUtilisateur = new ArrayList<>();

	public UtilisateurDAO utilisateurDAO;
	
	public UtilisateurDAOBouchon() {
		listeUtilisateur.add(new Utilisateur(1, "jeanjean", "dupont", "Jean", "jean@email.fr", "0601020304", "25 rue des lilas", "56000", "dev", "ICI C QUOI", 0, false));
	}

	public UtilisateurDAOBouchon(UtilisateurDAO utilisateurDAO) {
		this.utilisateurDAO = utilisateurDAO;
	}

	@Override
	public void createUtilisateur(Utilisateur utilisateur) {
		listeUtilisateur.add(utilisateur);
	}

}
