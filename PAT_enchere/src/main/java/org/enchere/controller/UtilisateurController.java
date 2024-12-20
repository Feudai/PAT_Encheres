package org.enchere.controller;

import java.security.Principal;

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

@Controller
public class UtilisateurController {

	@ResponseBody
	public String home(HttpSession session) {
		Object authenticatedUser = session.getAttribute("authenticatedUser");
		return "Authenticated User: " + authenticatedUser.toString();
	}

	private UtilisateurService utilisateurService;

	public UtilisateurController(UtilisateurService utilisateurService) {
		this.utilisateurService = utilisateurService;
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
	public String mettreAJourUtilisateur(@ModelAttribute Utilisateur utilisateur, Principal principal) {

		String username = principal.getName();
		Utilisateur authenticatedUser = utilisateurService.findByUsername(username);

		utilisateur.setNoUtilisateur(authenticatedUser.getNoUtilisateur());

		this.utilisateurService.update(utilisateur);

		return "redirect:/logout";
	}

	@GetMapping("/")
	public String acceuilDefaut() {
		return "accueil";

	}
	
	@GetMapping("/recuperationMotDePasse")
	public String affichageRecupMotDePasse (Model model) {
		model.addAttribute("utilisateur", new Utilisateur());
		return "recup-mot-de-passe";
	}
	
	@PostMapping("recuperationMotDePasse/recupMotDePasse")
	public String recupMotDePasse() {

		return "redirect:/login";
	}
	
	@GetMapping("/kowalski")
	public String hiddenKowalski () {
		return "kowalski";
	}
}
