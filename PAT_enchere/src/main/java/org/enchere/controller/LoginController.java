package org.enchere.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

public class LoginController {

	
	@GetMapping("/connexion")
	public String affichageConnexion () {
		
		return "connexion";
	}
	
	
	@GetMapping("/profile")
	public String affichageProfil() {
		
		return "profil";
	}
	
}
