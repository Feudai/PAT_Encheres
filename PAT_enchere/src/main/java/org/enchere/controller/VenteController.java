package org.enchere.controller;



import java.util.List;

import org.enchere.bll.ArticleVenduService;
import org.enchere.bll.CategorieService;
import org.enchere.bll.EnchereService;
import org.enchere.bll.RetraitService;
import org.enchere.bo.ArticleVendu;
import org.enchere.bo.Enchere;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

@Controller
public class VenteController {

	private ArticleVenduService articleVenduService;
	private EnchereService enchereService;
	private RetraitService retraitService;
	private CategorieService categorieService;
	
	public VenteController(ArticleVenduService articleVenduService, EnchereService enchereService,
			RetraitService retraitService, CategorieService categorieService) {
		this.articleVenduService = articleVenduService;
		this.enchereService = enchereService;
		this.retraitService = retraitService;
		this.categorieService = categorieService;
	}

	@GetMapping("/nouvelleVente")
	public String afficherCreationArticle(Model model) {
		model.addAttribute("article",new ArticleVendu());
		
		return "nouvelle-vente";
	}
	
	@PostMapping("/nouvelleVente")
	public String vendreUnArticle( @Valid @ModelAttribute("article") ArticleVendu article, BindingResult br) {
		if(br.hasErrors()) {
			//debug
			return "redirect:/nouvelleVente";
		}
		
		this.articleVenduService.ajouterArticle(article);

		//ici un th:object, donc besoin que des infos sur le vendeur,
		//le reste sera automatiquement relié aux fields de l'article vendu
		
		//. html mettre aussi un selecteur multiple avec les categories
		
		return "redirect:/encheres-gestion";
	}
	
	
	
	@GetMapping("/encheresEnCours")
	public String afficherEncheres(Model model) {
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
