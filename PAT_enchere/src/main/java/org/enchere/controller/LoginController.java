package org.enchere.controller;

import java.util.List;

import org.enchere.bll.UtilisateurService;
import org.enchere.bo.Utilisateur;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;


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
	public String affichageProfil(@RequestParam("noUtilisateur")int noUtilisateur, Model model) {
		
		Utilisateur u = this.utilisateurService.consulterUtilisateurParId(noUtilisateur);
		
		model.addAttribute("utilisateur", u);
		
		
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
	public String createUser(@ModelAttribute Utilisateur utilisateur) {

		this.utilisateurService.createUser(utilisateur);
		return "redirect:/profile";
	}

}
