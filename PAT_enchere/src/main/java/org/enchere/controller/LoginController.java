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


	@GetMapping("/profil-detail")
	public String affichageUtilisateur(@RequestParam(name = "noUtilisateur") int noUtilisateur, Model model) {
		Utilisateur utilisateur = this.utilisateurService.consulterUtilisateurParId(noUtilisateur);
		model.addAttribute("utilisateur", utilisateur);
		return "profil-detail";
	}

	@GetMapping("/profil")
	public String affichageUtilisateur(@RequestParam(name = "noUtilisateur", required = false) Integer noUtilisateur,
			Principal principal, Model model) {

		// Récupérer l'utilisateur connecté via le Principal
		String username = principal.getName();
		Utilisateur authenticatedUser = utilisateurService.findByUsername(username);

		if (noUtilisateur == null) {
			noUtilisateur = authenticatedUser.getNoUtilisateur();
		}

		Utilisateur utilisateur = this.utilisateurService.consulterUtilisateurParId(noUtilisateur);
		model.addAttribute("utilisateur", utilisateur);

		return "profil";
	}

	@PostMapping("/profil")
	public String mettreAJourUtilisateur(
	    @ModelAttribute Utilisateur utilisateur, 
	    Principal principal) {

	    String username = principal.getName();
	    Utilisateur authenticatedUser = utilisateurService.findByUsername(username);

	    utilisateur.setNoUtilisateur(authenticatedUser.getNoUtilisateur());
	    

	    this.utilisateurService.update(utilisateur);

	    return "redirect:/login";
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
			return "redirect:/login";
		}

	}

	@GetMapping("profil/deleteUser")
	public String deleteUser(@RequestParam(name = "noUtilisateur", required = true) Integer noUtilisateur,
			Principal principal, Model model) {

		Utilisateur utilisateur = this.utilisateurService.consulterUtilisateurParId(noUtilisateur);
		int idUtilisateur = utilisateur.getNoUtilisateur();
		model.addAttribute("utilisateur", utilisateur);
		this.utilisateurService.deleteUser(idUtilisateur);


		return "redirect:/login";
	}

}
