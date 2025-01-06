package org.enchere.controller;



import java.security.Principal;
import java.time.LocalDateTime;
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
import org.enchere.bo.Retrait;
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
		
		Retrait retrait =new Retrait();
		ArticleVendu article = new ArticleVendu();
		retrait.setArticle(article);
		article.setLieuRetrait(retrait);

		model.addAttribute("article",article);
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
		
		Enchere initialize = new Enchere(LocalDateTime.now(),article.getMiseAPrix(),authenticatedUser,article);
		System.err.println(article.getNoArticle());
		
		this.enchereService.ajouterEnchere(initialize);

		
		Retrait retrait = article.getLieuRetrait();
		retrait.setArticle(article);
		
		this.retraitService.ajouterRetrait(retrait);

		
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
				List<Enchere> remove = new ArrayList<>();
				for (Enchere enchere : listeEncheres) {
					if(l.getArticle().getNoArticle()==enchere.getArticle().getNoArticle()) {
						if(l.getMontantEnchere()<=enchere.getMontantEnchere())addEnchere = false;
						else remove.add(enchere);
						}
					}
				for (Enchere enchere : remove) {
					listeEncheres.remove(enchere);
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
	public String filtrerEncheres(@RequestParam(name="categorie", defaultValue="-1")int noCategorie,@RequestParam(name="nomArticle", defaultValue="")String nomArticle, Model model) {
		List<Enchere> listeEncheres=new ArrayList<Enchere>();
		List<Enchere> tempList=null;
		if(noCategorie!=-1)
		tempList =this.enchereService.getListeEncheres().stream().filter(e->e.getArticle().getCategorieArticle().getNoCategorie()==noCategorie).toList();
		tempList.forEach(e->listeEncheres.add(e));

		if(!nomArticle.equals("")&&nomArticle!=null)
		tempList =this.enchereService.getListeEncheres().stream().filter(e->e.getArticle().getNomArticle().contains(nomArticle)).toList();
		tempList.forEach(e->listeEncheres.add(e));
		//debug
		model.addAttribute("encheresFiltrees",listeEncheres);
		return "redirect:/accueil";
	}
	
	@GetMapping("/encheresDetails")
	public String afficherEncheresDetails(Model model,@RequestParam("noArticle")int noArticle) {
		


		ArticleVendu  articleVendu = this.articleVenduService.consulterArticleVenduParId(noArticle).get(0);
		Utilisateur utilisateur = this.utilisateurService.consulterUtilisateurParId(articleVendu.getCreateur().getNoUtilisateur());
		List<Enchere> encheres = this.enchereService.getEncheresByIdArticle(noArticle);
		articleVendu.setCreateur(utilisateur);
		
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
	public String proposerPrixEnchere(@ModelAttribute ArticleVendu articleVendu,@RequestParam("proposition") String proposition,Principal principal) {
		
		String username = principal.getName();
		Utilisateur authenticatedUser = utilisateurService.findByUsername(username);
		
		int montant = Integer.parseInt(proposition);
		Enchere nouvelleEnchere = new Enchere(LocalDateTime.now(),montant,authenticatedUser,articleVendu);
		
		if (nouvelleEnchere.getMontantEnchere()>this.enchereService.getBestEnchere(articleVendu.getNoArticle()).getMontantEnchere()&&this.enchereService.getBestEnchere(articleVendu.getNoArticle()).getCreateur().getNoUtilisateur()!=nouvelleEnchere.getCreateur().getNoUtilisateur()) {
			this.enchereService.ajouterEnchere(nouvelleEnchere);
		}
		else System.err.println("caca");
		
		
		
		return "redirect:/accueil";
	}
	
	
	
	
	
}
