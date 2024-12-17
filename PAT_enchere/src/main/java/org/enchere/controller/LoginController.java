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
<<<<<<< HEAD
<<<<<<< HEAD

	
	@GetMapping("/nouvelleVente")
	public String affichagePageVente () {
		
		return "nouvelleVente";
	}


=======
>>>>>>> da7419dff246e81f34f0e3eeeb53fcc6b386b7c0
=======
>>>>>>> dee982cb3ee89f0ddb96166d81a72f026a7df6e6
	@GetMapping("/inscription")
	public String affichageInscription() {
		
		return "inscription";
	}
<<<<<<< HEAD
<<<<<<< HEAD

=======
	
>>>>>>> da7419dff246e81f34f0e3eeeb53fcc6b386b7c0
=======
	
>>>>>>> dee982cb3ee89f0ddb96166d81a72f026a7df6e6
}
