package org.enchere.controller;

import org.enchere.bll.UtilisateurService;


import org.enchere.bo.Utilisateur;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class LoginController {

	private UtilisateurService utilisateurService;

	public LoginController(UtilisateurService utilisateurService) {
		this.utilisateurService = utilisateurService;
	}

	@GetMapping("/connexion")
	public String affichageConnexion() {

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


		

	@GetMapping("/nouvelle-vente")
	public String affichagePageVente() {


		return "nouvelle-vente";
	}

	@GetMapping("/inscription")
	public String affichageInscription(Model model) {
		model.addAttribute("utilisateur", new Utilisateur());
		return "inscription";
	}

	@GetMapping("/utilisateur")
	public String affichageUtilisateur() {

		return "utilisateur";
	}

	@PostMapping("/createUser")
	public String createUser(@ModelAttribute Utilisateur utilisateur) {

		this.utilisateurService.createUser(utilisateur);
		return "redirect:/profile";
	}

}
