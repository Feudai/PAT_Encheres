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
<<<<<<< HEAD
import org.springframework.web.bind.annotation.ResponseBody;
=======
>>>>>>> d74599f96a09399e499c59192e742b0fa246978e

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

<<<<<<< HEAD
	@GetMapping("/profile")
=======
	@GetMapping("/utilisateur")
>>>>>>> d74599f96a09399e499c59192e742b0fa246978e
	public String affichageProfil(@ModelAttribute int noUtilisateur, Model model) {

		Utilisateur utilisateur = this.utilisateurService.consulterUtilisateurParId(noUtilisateur);

		model.addAttribute("utilisateur", utilisateur);

		return "utilisateur";
	}

<<<<<<< HEAD
=======
	@GetMapping("/profile")
	public String modifierProfil() {

		return "profile";

	}
>>>>>>> d74599f96a09399e499c59192e742b0fa246978e

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

<<<<<<< HEAD
	@GetMapping("/profil-detail")
	public String affichageUtilisateur() {

		return "profil-detail";
	}

	@PostMapping("/createUser")

=======
	@PostMapping("/createUser")
>>>>>>> d74599f96a09399e499c59192e742b0fa246978e
	public String createUser(@Valid @ModelAttribute Utilisateur utilisateur, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "/inscription";
		} else {
			this.utilisateurService.createUser(utilisateur);
			return "redirect:/profile";
		}

	}

}
