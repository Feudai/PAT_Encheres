package org.enchere.controller;



import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
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
	
	@GetMapping("/accueil")
	public String afficherEncheres(Model model) {
		List<Utilisateur> utilisateurs = this.utilisateurService.consulterUtilisateurs();
		model.addAttribute("utilisateurs", utilisateurs);

		List<Enchere> tempListeEncheres = this.enchereService.getListeEncheres();
		List<Enchere> listeEncheres = new ArrayList<>();

		
			tempListeEncheres.forEach(l->{ 
				boolean addEnchere = true;
				for (Enchere enchere : listeEncheres) {
					if(l.getArticle().getNoArticle()==enchere.getArticle().getNoArticle())addEnchere = false;
					}
				if(addEnchere)listeEncheres.add(l);
				}
				);				
		
		
		
		model.addAttribute("listeEncheres",listeEncheres);
		model.addAttribute("listeCategories", this.categorieService.getListeCategories());
//		model.addAttribute("article",this.articleVenduService.consulterArticleVenduParId(1));


		return "accueil";
	}
	
	@PostMapping("/accueil")
	public String filtrerEncheres(Model model, @RequestParam(name="categorie", defaultValue="-1")int noCategorie,@RequestParam(name="nomArticle", defaultValue="")String nomArticle ) {
		List<Enchere> listeEncheres=null;
		//if(noCategorie!=-1)
		//listeEncheres =this.enchereService.getListeEncheres().stream().filter(e->e.getArticle().getCategorieArticle().getNoCategorie()==noCategorie).toList();
		listeEncheres=this.enchereService.getListeEncheres();
		//if(!nomArticle.equals("")&&nomArticle!=null)
		//listeEncheres =this.enchereService.getListeEncheres().stream().filter(e->e.getArticle().getNomArticle().contains(nomArticle)).toList();
		//debug
		//model.addAttribute("encheresFiltrees",listeEncheres);
		return "redirect:/accueil";
	}
	
	@GetMapping("/encheresDetails")
	public String afficherEncheresDetails(@RequestParam("noArticle")int noArticle, Model model) {
		

		ArticleVendu  articleVendu = this.articleVenduService.consulterArticleVenduParId(noArticle).get(0);
		
		List<Enchere> encheres = this.enchereService.getEncheresByIdArticle(noArticle);
		
		Collections.sort(encheres, (ench1, ench2) -> {
			  Enchere a = (Enchere) ench1;
			  Enchere b = (Enchere) ench2;
			  if (a.getMontantEnchere() > b.getMontantEnchere()) return -1;
			  if (a.getMontantEnchere() < b.getMontantEnchere()) return 1;
			  return 0;
			});
		
	    articleVendu.setListeEncheres(encheres); 
	    
	    	 
		model.addAttribute("articleVendu", articleVendu);
	
	
		return "encheres-details";
	}
	
	@PostMapping("/encheresDetails")
	public String proposerPrixEnchere() {
		
		
		return "redirect:/accueil";
	}
	
	
	
}
