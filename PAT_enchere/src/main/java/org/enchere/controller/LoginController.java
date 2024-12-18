package org.enchere.controller;

import java.security.Principal;
import java.util.List;

import org.enchere.bll.UtilisateurService;
import org.enchere.bo.Utilisateur;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


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


	@GetMapping("/profil-detail")
	public String affichageUtilisateur(@RequestParam(name= "noUtilisateur") int noUtilisateur, Model model) {
		Utilisateur utilisateur = this.utilisateurService.consulterUtilisateurParId(noUtilisateur);
		model.addAttribute("utilisateur", utilisateur);
		return "profil-detail";
	}

	@GetMapping("/profil")
	public String affichageUtilisateur(
	    @RequestParam(name = "noUtilisateur", required = false) Integer noUtilisateur,
	    Principal principal,
	    Model model) {

	    // Récupérer l'utilisateur connecté via le Principal
	    String username = principal.getName();
	    Utilisateur authenticatedUser = utilisateurService.findByUsername(username);

	    if (noUtilisateur == null) {
	        noUtilisateur = authenticatedUser.getNoUtilisateur();
	    }

	    Utilisateur utilisateur = this.utilisateurService.consulterUtilisateurParId(noUtilisateur);
	    model.addAttribute("utilisateur", utilisateur);

	    return "profil-detail";
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





	@PostMapping("/createUser")

	public String createUser(@Valid @ModelAttribute Utilisateur utilisateur, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "/inscription";
		} else {
			this.utilisateurService.createUser(utilisateur);
			return "redirect:/profil";
		}

	}

}
