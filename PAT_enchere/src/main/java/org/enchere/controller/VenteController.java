package org.enchere.controller;



import org.enchere.bll.ArticleVenduService;
import org.enchere.bll.CategorieService;
import org.enchere.bll.EnchereService;
import org.enchere.bll.RetraitService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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

	@GetMapping
	public String vendreUnArticle() {
		
		
		return "nouvelle-vente";
	}
	
}
