package org.enchere.controller;



import java.security.Principal;
import java.util.List;

import org.enchere.bll.ArticleVenduService;
import org.enchere.bll.CategorieService;
import org.enchere.bll.EnchereService;
import org.enchere.bll.RetraitService;
import org.enchere.bll.UtilisateurService;
import org.enchere.bo.ArticleVendu;
import org.enchere.bo.Enchere;
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
public class VenteController {

	private ArticleVenduService articleVenduService;
	private EnchereService enchereService;
	private RetraitService retraitService;
	private CategorieService categorieService;
	
	private UtilisateurService utilisateurService;


	@ResponseBody
	public String home(HttpSession session) {
		Object authenticatedUser = session.getAttribute("authenticatedUser");
		return "Authenticated User: " + authenticatedUser.toString();
	}
	
	public VenteController(ArticleVenduService articleVenduService, EnchereService enchereService,
			RetraitService retraitService, CategorieService categorieService,UtilisateurService utilisateurService) {
		this.articleVenduService = articleVenduService;
		this.enchereService = enchereService;
		this.retraitService = retraitService;
		this.categorieService = categorieService;
		this.utilisateurService = utilisateurService;

	}

	@GetMapping("/nouvelleVente")
	public String afficherCreationArticle(Model model) {

		model.addAttribute("article",new ArticleVendu());
		model.addAttribute("listeCategories",this.categorieService.getListeCategories());
		
		return "nouvelle-vente";
	}
	
	@PostMapping("/nouvelleVente")
	public String vendreUnArticle(@Valid @ModelAttribute ArticleVendu article,Principal principal, BindingResult br) {
		if(br.hasErrors()) {
//debug
			return "nouvelle-vente";
		}
		
		String username = principal.getName();
		Utilisateur authenticatedUser = utilisateurService.findByUsername(username);

		int noUtilisateur = authenticatedUser.getNoUtilisateur();
		
		this.articleVenduService.ajouterArticle(article,noUtilisateur);

		
		return "redirect:/accueil";
	}
	
	
	
	@GetMapping("/encheresEnCours")
	public String afficherEncheres(Model model) {
		System.out.println(this.enchereService.getListeEncheres().toString());
		model.addAttribute("listeEncheres",this.enchereService.getListeEncheres());
		return "encheres-en-cours";
	}
	
	@PostMapping("/encheresEnCours")
	public String filtrerEncheres(Model model, @RequestParam(name="categorie", defaultValue="-1")int noCategorie,@RequestParam(name="nomArticle")String nomArticle ) {
		List<Enchere> listeEncheres=null;
		if(noCategorie!=-1)
		listeEncheres =this.enchereService.getListeEncheres().stream().filter(e->e.getArticle().getCategorieArticle().getNoCategorie()==noCategorie).toList();
		
		if(!nomArticle.equals("")&&nomArticle!=null)
			listeEncheres =this.enchereService.getListeEncheres().stream().filter(e->e.getArticle().getNomArticle().contains(nomArticle)).toList();
			
		model.addAttribute("encheresFiltrees",listeEncheres);
		return "redirect:/encheresEnCours";
	}
	
	@GetMapping("/encheresDetails")
	public String afficherEncheresDetails() {
		
		
		
		return "encheres-details:";
	}
	
	@PostMapping("/encheresDetails")
	public String proposerPrixEnchere() {
		
		return "redirect:/encheres-gestion";
	}
	
	
	
}
