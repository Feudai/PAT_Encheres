package org.enchere.controller;

import org.enchere.bll.UtilisateurService;
import org.enchere.bo.Utilisateur;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

@Controller
public class LoginController {

	private UtilisateurService utilisateurService;

	public LoginController(UtilisateurService utilisateurService) {
		this.utilisateurService = utilisateurService;
	}

	@GetMapping("/login")
	public String affichageConnexion() {

		return "login";
	}

	@GetMapping("/profile")
	public String affichageProfil() {

		return "profile";
	}

	@PostMapping("/profile")
	public String modifierProfil() {

		return "redirect:/profile";

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
	public String createUser(@Valid @ModelAttribute Utilisateur utilisateur, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "/inscription";
		} else {
			this.utilisateurService.createUser(utilisateur);
			return "redirect:/profile";
		}
	}

}
