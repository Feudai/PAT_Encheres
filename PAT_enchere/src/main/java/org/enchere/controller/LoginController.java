package org.enchere.controller;

import org.enchere.bll.UtilisateurService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class LoginController {

	private UtilisateurService utilisateurService;
	
	@GetMapping("/connexion")
	public String affichageConnexion () {
		
		return "connexion";
	}
	@GetMapping("/profile")
	public String affichageProfil() {
		
		return "profile";
	}

	@GetMapping("/accueil")
	public String affichageAccueil() {
		
		return "accueil";
	}

	
	@GetMapping("/nouvelleVente")
	public String affichagePageVente () {
		
		return "nouvelleVente";
	}



	@GetMapping("/inscription")
	public String affichageInscription() {
		
		return "inscription";
	}

	@GetMapping("/utilisateur")
	public String affichageUtilisateur () {
		
		return "utilisateur";
	}

}
