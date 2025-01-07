package org.enchere.controller;



import java.security.Principal;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.enchere.bll.ArticleVenduService;
import org.enchere.bll.CategorieService;
import org.enchere.bll.EnchereService;
import org.enchere.bll.ImageService;
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
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class VenteController {

	private ArticleVenduService articleVenduService;
	private EnchereService enchereService;
	private RetraitService retraitService;
	private CategorieService categorieService;
	private ImageService imageService;
	
	private UtilisateurService utilisateurService;


	@ResponseBody
	public String home(HttpSession session) {
		Object authenticatedUser = session.getAttribute("authenticatedUser");
		return "Authenticated User: " + authenticatedUser.toString();
	}
	@GetMapping("/")
	public String acceuilDefaut() {
		return "redirect:/accueil";

	}

	
	public VenteController(ArticleVenduService articleVenduService, EnchereService enchereService,
			RetraitService retraitService, CategorieService categorieService,UtilisateurService utilisateurService,ImageService imageService) {
		this.articleVenduService = articleVenduService;
		this.enchereService = enchereService;
		this.retraitService = retraitService;
		this.categorieService = categorieService;
		this.utilisateurService = utilisateurService;
		this.imageService = imageService;

	}

	@GetMapping("/nouvelleVente")
	public String afficherCreationArticle(Model model, Principal principal) {
		
		Retrait retrait =new Retrait();
		ArticleVendu article = new ArticleVendu();
		retrait.setArticle(article);
		article.setLieuRetrait(retrait);
		
		String username = principal.getName();
        Utilisateur authenticatedUser = utilisateurService.findByUsername(username);
        
        
        //Filling by default the retrait
        if (article.getLieuRetrait().getRue() == null || article.getLieuRetrait().getRue().isEmpty()) {
        	article.getLieuRetrait().setRue(authenticatedUser.getRue());
        }
        if (article.getLieuRetrait().getVille() == null || article.getLieuRetrait().getVille().isEmpty()) {
        	article.getLieuRetrait().setVille(authenticatedUser.getVille());
        }
        if (article.getLieuRetrait().getCodePostal() == null || article.getLieuRetrait().getCodePostal().isEmpty()) {
        	article.getLieuRetrait().setCodePostal(authenticatedUser.getCodePostal());
        }
       
		
        model.addAttribute("utilisateur",authenticatedUser);
		model.addAttribute("article",article);
		model.addAttribute("listeCategories",this.categorieService.getListeCategories());
		
		return "nouvelle-vente";
	}
	
	@PostMapping("/nouvelleVente")
	public String vendreUnArticle(
	        @Valid @ModelAttribute ArticleVendu article,
	        @RequestParam("image") MultipartFile imageFile,
	        Principal principal,
	        BindingResult br) {
		
	    if (br.hasErrors()) {
	        return "nouvelle-vente";
	    }


	    try {
	        // Récupérer l'utilisateur connecté
	        String username = principal.getName();
	        Utilisateur authenticatedUser = utilisateurService.findByUsername(username);
	        int noUtilisateur = authenticatedUser.getNoUtilisateur();

	        
	        // Sauvegarder l'article
	       this.articleVenduService.ajouterArticle(article, noUtilisateur);
	        
	        // Sauvegarder l'image
	        String cheminImage = imageService.sauvegarderImage(imageFile, article.getNoArticle());
	        System.err.println(cheminImage);
	        article.setCheminImage(cheminImage);
	        

	        // Modifier le nom de l'image
	        articleVenduService.modifierNomImage(cheminImage , article.getNoArticle());
	        
	    	Retrait retrait = article.getLieuRetrait();

			retrait.setArticle(article);
			
			this.retraitService.ajouterRetrait(retrait);
			
	        // Instanciation de la première enchère automatique
	        List<Enchere> initList = new ArrayList<>();
	        
	        ArticleVendu retrieve = this.articleVenduService.consulterArticleVenduParId(article.getNoArticle()).get(0);
	        Enchere init = new Enchere(retrieve.getDateDebutEncheres(), retrieve.getMiseAPrix(), retrieve.getCreateur(), retrieve);
	        
	        this.enchereService.ajouterEnchere(init);

	    } catch (Exception e) {
	        e.printStackTrace();
	        return "nouvelle-vente";
	    }

	    return "redirect:/accueil";

	}
	
	@GetMapping("/accueil")
	public String afficherEncheres(Model model) {
		List<Utilisateur> utilisateurs = this.utilisateurService.consulterUtilisateurs();
		List<ArticleVendu> EncheresArticlesTriees = this.sortEncheresArticles();
		
		model.addAttribute("utilisateurs", utilisateurs);
		model.addAttribute("listeArticles",EncheresArticlesTriees);
		model.addAttribute("listeCategories", this.categorieService.getListeCategories());
//		model.addAttribute("article",this.articleVenduService.consulterArticleVenduParId(1));


		return "accueil";
	}
	
	
	@PostMapping("/accueil")
	public String filtrerEncheres(
			@RequestParam(name="idCategorie", defaultValue="-1")String idCategorie,
			@RequestParam(name="search", defaultValue="")String nomArticle, 
			@RequestParam(name="enchOuv") boolean encheresOuvertes, 
			@RequestParam(name="enchCours") boolean encheresEnCours ,
			@RequestParam(name="enchRemp") boolean encheresRemportees ,
			@RequestParam(name="venCours") boolean ventesEnCours ,
			@RequestParam(name="venPrep") boolean ventesAVenir ,
			@RequestParam(name="venTerm") boolean ventesTerminees,
			Model model, 
			Principal principal) {
		List<ArticleVendu> EncheresArticlesTriees=this.sortEncheresArticles();
		List<ArticleVendu> listeArticles=new ArrayList<>();
		List<ArticleVendu> tempList=null;
		List<Utilisateur> utilisateurs = this.utilisateurService.consulterUtilisateurs();

		 String username = principal.getName();
	        Utilisateur authenticatedUser = utilisateurService.findByUsername(username);
	        int noUtilisateur = authenticatedUser.getNoUtilisateur();
		
		int noCategorie= Integer.parseInt(idCategorie);
		
		if(noCategorie!=-1) {
		tempList =EncheresArticlesTriees.stream().filter(a->a.getCategorieArticle().getNoCategorie()==noCategorie).toList();
		tempList.forEach(e->listeArticles.add(e));}

		if(!nomArticle.equals("")&&nomArticle!=null) {
			
		tempList =EncheresArticlesTriees.stream().filter(a->a.getNomArticle().toLowerCase().contains(nomArticle.toLowerCase())).toList();
		tempList.forEach(e->listeArticles.add(e));}


		if(tempList==null) {
		tempList =EncheresArticlesTriees;
		tempList.forEach(a->listeArticles.add(a));}
		
		
		//checkboxes
		if(encheresOuvertes) {
			tempList = EncheresArticlesTriees.stream().filter(a->LocalDateTime.now().compareTo(a.getDateDebutEncheres())>=0).toList();
		}
		if(encheresEnCours) { //incomplet, nécessite articlesaacheter
			tempList = EncheresArticlesTriees.stream().filter(a->LocalDateTime.now().compareTo(a.getDateDebutEncheres())>=0&&LocalDateTime.now().compareTo(a.getDateFinEncheres())<0&&a.getCreateur().getNoUtilisateur()==noUtilisateur).toList();
		}
		if(encheresRemportees) {
			tempList = EncheresArticlesTriees.stream().filter(a->LocalDateTime.now().compareTo(a.getDateDebutEncheres())>=0&&LocalDateTime.now().compareTo(a.getDateFinEncheres())<0).toList();
		}
		if(ventesEnCours) {
			tempList = EncheresArticlesTriees.stream().filter(a->LocalDateTime.now().compareTo(a.getDateDebutEncheres())>=0&&LocalDateTime.now().compareTo(a.getDateFinEncheres())<0&&a.getCreateur().getNoUtilisateur()==noUtilisateur).toList();

		}
		if(ventesAVenir) {
			
		}
		if(ventesTerminees) {
			
		}	
		//chantier
		
		model.addAttribute("utilisateurs", utilisateurs);
		model.addAttribute("listeArticles",listeArticles);
		model.addAttribute("listeCategories", this.categorieService.getListeCategories());
		
		return "accueil";
	}
	
	//Function for sorting Encheres  
		public List<ArticleVendu> sortEncheresArticles (){
			List<ArticleVendu> temp = this.articleVenduService.getListeArticles();
			List<ArticleVendu> articles = new ArrayList<>();
			
			//debug
			//temp.forEach(a->System.err.println(a.getListeEncheres().size()));

			//Only one instance of article by noArticle
			temp.forEach(a->{
				List<ArticleVendu> stream = articles.stream().filter(b->b.getNoArticle()==a.getNoArticle()).toList();
				if(stream.size()==0)articles.add(a);
				else stream.get(0).getListeEncheres().add(a.getListeEncheres().get(0));
			});
			
			articles.forEach(a->{
				List<Enchere> enchereParArticle = new ArrayList<>();
				a.getListeEncheres().forEach(e->enchereParArticle.add(e));
				a.setListeEncheres(this.sort(enchereParArticle));
				
				//Nouvelle fonction pour trier les encheres au sein de chaque article
	
				});

			
			
			return articles;
		}
		
	public List<Enchere> sort(List<Enchere> encheres){
		Collections.sort(encheres, (ench1, ench2) -> {
			  Enchere a = (Enchere) ench1;
			  Enchere b = (Enchere) ench2;
			  if (a.getMontantEnchere() > b.getMontantEnchere()) return -1;
			  if (a.getMontantEnchere() < b.getMontantEnchere()) return 1;
			  return 0;
			});
		return encheres;
	}
	
	@GetMapping("/encheresDetails")
	public String afficherEncheresDetails(Model model,@RequestParam("noArticle")int noArticle) {
		


		ArticleVendu  articleVendu = this.articleVenduService.consulterArticleVenduParId(noArticle).get(0);
		Utilisateur utilisateur = this.utilisateurService.consulterUtilisateurParId(articleVendu.getCreateur().getNoUtilisateur());
		List<Enchere> encheres = this.enchereService.getEncheresByIdArticle(noArticle);
		articleVendu.setCreateur(utilisateur);
		
	    articleVendu.setListeEncheres(this.sort(encheres)); 

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
	
	
	
	//Deprecated function that kept only the best bid
//	List<Enchere> listeEncheres = new ArrayList<>();

//		tempListeEncheres.forEach(l->{ 
//			boolean addEnchere = true;
//			List<Enchere> remove = new ArrayList<>();
//			for (Enchere enchere : listeEncheres) {
//				if(l.getArticle().getNoArticle()==enchere.getArticle().getNoArticle()) {
//					if(l.getMontantEnchere()<=enchere.getMontantEnchere())addEnchere = false;
//					else remove.add(enchere);
//					}
//				}
//			for (Enchere enchere : remove) {
//				listeEncheres.remove(enchere);
//			}
//			if(addEnchere)listeEncheres.add(l);
//			}
//			);	
	
	
}
