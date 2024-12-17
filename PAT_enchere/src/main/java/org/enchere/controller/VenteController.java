package org.enchere.controller;



import org.enchere.bll.ArticleVenduService;
import org.enchere.bll.CategorieService;
import org.enchere.bll.EnchereService;
import org.enchere.bll.RetraitService;
import org.enchere.bo.ArticleVendu;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Profile("dev")
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
		
		model.addAttribute("ArticleVendu",new ArticleVendu());

		return "nouvelle-vente";
	}
	
	@PostMapping("/nouvelleVente")
	public String vendreUnArticle(@ModelAttribute ArticleVendu articleVendu) {
		this.articleVenduService.ajouterArticle();
		//ici un th:object, donc besoin que des infos sur le vendeur,
		//le reste sera automatiquement relié aux fields de l'article vendu
		
		//. html mettre aussi un selecteur multiple avec les categories
		
		return "redirect:/encheres";
	}
	
	@GetMapping("/encheresEnCours")
	public String afficherEncheres() {
		
		return "encheres-en-cours";
	}
	@GetMapping("/encheresEnCours")
	public String filtrerEncheres() {
		
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
