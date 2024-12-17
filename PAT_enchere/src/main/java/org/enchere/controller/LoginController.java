package org.enchere.controller;

import java.util.List;

import org.enchere.bll.UtilisateurService;
import org.enchere.bo.Utilisateur;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;


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

	@PostMapping("/utilisateur")
	public String affichageProfil(@ModelAttribute int noUtilisateur, Model model) {
		
		Utilisateur utilisateur = this.utilisateurService.consulterUtilisateurParId(noUtilisateur);
		
		model.addAttribute("utilisateur", utilisateur);
		
		
		return "profile";
	}

	@PostMapping("/profile")
	public String modifierProfil() {

		return "redirect:/profile";

	}

	@GetMapping("/accueil")
	public String affichageTousUtilisateurs(Model model) {
		List<Utilisateur> utilisateurs = this.utilisateurService.consulterUtilisateurs();
		model.addAttribute("utilisateurs", utilisateurs);
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
<<<<<<< HEAD
	public String createUser(@ModelAttribute Utilisateur utilisateur) {

		this.utilisateurService.createUser(utilisateur);
		return "redirect:/accueil";
=======
	public String createUser(@Valid @ModelAttribute Utilisateur utilisateur, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "/inscription";
		} else {
			this.utilisateurService.createUser(utilisateur);
			return "redirect:/profile";
		}
>>>>>>> aff8b61bcc7571d62e58237224fed88c60552b5a
	}

}
