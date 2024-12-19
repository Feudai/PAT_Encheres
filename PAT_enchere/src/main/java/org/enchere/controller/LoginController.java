package org.enchere.controller;

import java.security.Principal;
import java.util.List;

import org.enchere.bll.UtilisateurService;
import org.enchere.bo.Utilisateur;
import org.enchere.exceptions.BusinessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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
	public String affichageConnexion(@RequestParam(value = "session", required = false) String session, Model model) {

        if ("expired".equals(session)) {
            model.addAttribute("errorMessage", "Votre session a expiré. Veuillez vous reconnecter.");
        }
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


	@GetMapping("/inscription")
	public String affichageInscription(Model model) {
		model.addAttribute("utilisateur", new Utilisateur());
		return "inscription";
	}

	@PostMapping("/createUser")
	public String createUser(@Valid @ModelAttribute Utilisateur utilisateur, BindingResult bindingResult) {
		
	    // Vérification de la correspondance des mots de passe
	    if (!utilisateur.getMotDePasse().equals(utilisateur.getConfirmationMotDePasse())) {
	        bindingResult.rejectValue("confirmationMotDePasse", "error.utilisateur", 
	                                "Les mots de passe ne correspondent pas");
	        return "inscription";
	    }
		
		if (bindingResult.hasErrors()) {
			return "inscription";
		} else {
			try {
				this.utilisateurService.createUser(utilisateur);
				return "redirect:/login";
			} catch (BusinessException e) {
				// block executé lorsque la validation du formateur ne respecte pas toutes les
				// règles
				e.printStackTrace(); // affiche les erreurs dans la console
				// boucle sur l'ensemble des erreurs de validation pour les transmettre à la vue
				e.getListeMessage().forEach(m -> {
					ObjectError error = new ObjectError("globalError", m);
					bindingResult.addError(error);
				});
				return "inscription";
			}

		}

	}

	@GetMapping("profil/deleteUser")
	public String deleteUser(@RequestParam(name = "noUtilisateur", required = true) Integer noUtilisateur,
			Principal principal, Model model) {

		Utilisateur utilisateur = this.utilisateurService.consulterUtilisateurParId(noUtilisateur);
		int idUtilisateur = utilisateur.getNoUtilisateur();
		model.addAttribute("utilisateur", utilisateur);
		this.utilisateurService.deleteUser(idUtilisateur);

		return "redirect:/logout";
	}

	@GetMapping("/listeUtilisateurs")
	public String afficherListeUtilisateurModerateur(Model model) {
		List<Utilisateur> utilisateurs = this.utilisateurService.consulterUtilisateurs();
		model.addAttribute("utilisateurs", utilisateurs);
		return "listeUtilisateurs";
	}
	
	@GetMapping("listeUtilisateurs/deleteUser")
	public String deleteUserAdmin(@RequestParam("noUtilisateur") Integer noUtilisateur, Model model) {

		Utilisateur utilisateur = this.utilisateurService.consulterUtilisateurParId(noUtilisateur);
		int idUtilisateur = utilisateur.getNoUtilisateur();
		
		model.addAttribute("utilisateur", utilisateur);
		
		this.utilisateurService.deleteUser(idUtilisateur);

		return "redirect:/accueil";
	}
	
	
}
