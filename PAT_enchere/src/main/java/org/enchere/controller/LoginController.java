package org.enchere.controller;

import java.util.List;
import java.util.Random;

import org.enchere.bll.UtilisateurService;
import org.enchere.bo.Utilisateur;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller

public class LoginController {

	@ResponseBody
	public String home(HttpSession session) {
		Object authenticatedUser = session.getAttribute("authenticatedUser");
		return "Authenticated User: " + authenticatedUser.toString();
	}

	private UtilisateurService utilisateurService;

	public LoginController(UtilisateurService utilisateurService) {
		this.utilisateurService = utilisateurService;
	}

	@GetMapping("/login")
	public String affichageConnexion() {

		return "login";
	}

	@GetMapping("/profile")
	public String affichageProfil(@RequestParam(name = "noUtilisateur") int noUtilisateur, Model model) {

		Utilisateur utilisateur = this.utilisateurService.consulterUtilisateurParId(noUtilisateur);

		model.addAttribute("utilisateur", utilisateur);

		return "profile";
	}

	@GetMapping("/profil-detail")
	public String affichageUtilisateur(@RequestParam(name = "noUtilisateur") int noUtilisateur, Model model) {
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
			return "redirect:/profile";
		}
		
	}
	
	@GetMapping("profile/deleteUser")
	public String deleteUser(@RequestParam(name = "noUtilisateur", required = true) int noUtilisateur, Model model) {

		this.utilisateurService.deleteUser(noUtilisateur);

		model.addAttribute("utilisateur", noUtilisateur );

		return "accueil";
	}


}
