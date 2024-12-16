package org.enchere.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class LoginController {

	
	
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

	
	@GetMapping("/nouvelleVente")
	public String affichagePageVente () {
		
		return "nouvelleVente";
	}


=======
>>>>>>> da7419dff246e81f34f0e3eeeb53fcc6b386b7c0
	@GetMapping("/inscription")
	public String affichageInscription() {
		
		return "inscription";
	}
<<<<<<< HEAD

=======
	
>>>>>>> da7419dff246e81f34f0e3eeeb53fcc6b386b7c0
}
