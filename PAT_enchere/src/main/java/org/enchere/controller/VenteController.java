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
import org.enchere.bo.Categorie;
import org.enchere.bo.Enchere;
import org.enchere.bo.Retrait;
import org.enchere.bo.Utilisateur;
import org.enchere.exceptions.ImageTropGrandException;
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
			RetraitService retraitService, CategorieService categorieService, UtilisateurService utilisateurService,
			ImageService imageService) {
		this.articleVenduService = articleVenduService;
		this.enchereService = enchereService;
		this.retraitService = retraitService;
		this.categorieService = categorieService;
		this.utilisateurService = utilisateurService;
		this.imageService = imageService;

	}

	@GetMapping("/nouvelleVente")
	public String afficherCreationArticle(Model model, Principal principal) {

		Retrait retrait = new Retrait();
		ArticleVendu article = new ArticleVendu();
		retrait.setArticle(article);
		article.setLieuRetrait(retrait);

		String username = principal.getName();
		Utilisateur authenticatedUser = utilisateurService.findByUsername(username);

		// Filling by default the retrait
		if (article.getLieuRetrait().getRue() == null || article.getLieuRetrait().getRue().isEmpty()) {
			article.getLieuRetrait().setRue(authenticatedUser.getRue());
		}
		if (article.getLieuRetrait().getVille() == null || article.getLieuRetrait().getVille().isEmpty()) {
			article.getLieuRetrait().setVille(authenticatedUser.getVille());
		}
		if (article.getLieuRetrait().getCodePostal() == null || article.getLieuRetrait().getCodePostal().isEmpty()) {
			article.getLieuRetrait().setCodePostal(authenticatedUser.getCodePostal());
		}

		model.addAttribute("utilisateur", authenticatedUser);
		model.addAttribute("article", article);
		model.addAttribute("listeCategories", this.categorieService.getListeCategories());

		return "nouvelle-vente";
	}

	@PostMapping("/nouvelleVente")
	public String vendreUnArticle(@Valid @ModelAttribute ArticleVendu article, BindingResult br, 
	                            @RequestParam("image") MultipartFile imageFile, 
	                            Principal principal, 
	                            
	                            Model model) {

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
	        articleVenduService.modifierNomImage(cheminImage, article.getNoArticle());

	        Retrait retrait = article.getLieuRetrait();
	        retrait.setArticle(article);
	        this.retraitService.ajouterRetrait(retrait);

	        return "redirect:/accueil";
	        
	    } catch (ImageTropGrandException e) {
	        // Ajouter l'erreur au BindingResult pour utiliser le fragment
	        br.rejectValue("cheminImage", "image.tooLarge", "L'image est trop grande (maximum 5MB)");
	        return "redirect:/nouvelle-vente";
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	        br.rejectValue("cheminImage", "image.error", "Une erreur est survenue lors de l'enregistrement de l'image");
	        return "nouvelle-vente";
	    }
	}

	@GetMapping("/accueil")
	public String afficherEncheres(Model model) {
		List<Utilisateur> utilisateurs = this.utilisateurService.consulterUtilisateurs();
		List<ArticleVendu> EncheresArticlesTriees = this.sortEncheresArticles();

		// Updating all articles state
		LocalDateTime now = LocalDateTime.now();
		EncheresArticlesTriees.forEach(a -> {

			if (a.getDateFinEncheres().compareTo(now) <= 0)
				a.setEtatVente(1);
			else if (a.getDateDebutEncheres().compareTo(now) <= 0)
				a.setEtatVente(0);
			else
				a.setEtatVente(-1);

		});

		model.addAttribute("utilisateurs", utilisateurs);
		model.addAttribute("listeArticles", EncheresArticlesTriees);
		model.addAttribute("listeCategories", this.categorieService.getListeCategories());
//		model.addAttribute("article",this.articleVenduService.consulterArticleVenduParId(1));

		return "accueil";
	}

	@PostMapping("/accueil")
	public String filtrerEncheres(@RequestParam(name = "idCategorie", defaultValue = "-1") String idCategorie,
			@RequestParam(name = "search", defaultValue = "") String nomArticle,
			@RequestParam(name = "choix", defaultValue = "false") boolean choix,
			@RequestParam(name = "enchOuv", required = false) boolean encheresOuvertes,
			@RequestParam(name = "enchCours", required = false) boolean encheresEnCours,
			@RequestParam(name = "enchRemp", required = false) boolean encheresRemportees,
			@RequestParam(name = "venCours", required = false) boolean ventesEnCours,
			@RequestParam(name = "venPrep", required = false) boolean ventesAVenir,
			@RequestParam(name = "venTerm", required = false) boolean ventesTerminees, Model model,
			Principal principal) {


		int nbFiltres = 8;
		List<ArticleVendu> EncheresArticlesTriees = this.sortEncheresArticles();
		List<ArticleVendu> listeArticles = new ArrayList<>();
		List<ArticleVendu> tempList = new ArrayList<>();
		String message = "Aucun élément ne correspond à votre recherche";
		boolean error = false;
		List<Utilisateur> utilisateurs = this.utilisateurService.consulterUtilisateurs();
		List<Boolean> filtres = new ArrayList<>();
		for (int a = 0; a < nbFiltres; a++)
			filtres.add(false);

		String username = principal.getName();
		Utilisateur authenticatedUser = utilisateurService.findByUsername(username);
		int noUtilisateur = authenticatedUser.getNoUtilisateur();

		int noCategorie = Integer.parseInt(idCategorie);

		if (!nomArticle.equals("") && nomArticle != null) {
			filtres.set(0, true);
		} else
			filtres.set(0, false);

		if (noCategorie != -1) {
			filtres.set(1, true);
		} else
			filtres.set(1, false);

		filtres.set(2, encheresOuvertes);

		filtres.set(3, encheresEnCours);

		filtres.set(4, encheresRemportees);

		filtres.set(5, ventesEnCours);

		filtres.set(6, ventesAVenir);

		filtres.set(7, ventesTerminees);

		System.err.println(choix);
		;

		tempList = EncheresArticlesTriees.stream().filter(a -> {
			boolean result = true;

			if (filtres.get(0)) {
				result = result && a.getNomArticle().toLowerCase().contains(nomArticle.toLowerCase());
			}

			if (filtres.get(1)) {
				result = result && a.getCategorieArticle().getNoCategorie() == noCategorie;
			}

			if (filtres.get(2)) {
				result = result && LocalDateTime.now().compareTo(a.getDateDebutEncheres()) >= 0;
			}

			if (filtres.get(3)) {
				result = result && LocalDateTime.now().compareTo(a.getDateDebutEncheres()) >= 0
						&& LocalDateTime.now().compareTo(a.getDateFinEncheres()) < 0 && a.getListeEncheres() != null
						&& !a.getListeEncheres().isEmpty()
						&& a.getListeEncheres().get(0).getCreateur().getNoUtilisateur() == noUtilisateur;
			}

			if (filtres.get(4)) {
				result = result && LocalDateTime.now().compareTo(a.getDateFinEncheres()) >= 0;
			}

			if (filtres.get(5)) {
				result = result && LocalDateTime.now().compareTo(a.getDateDebutEncheres()) >= 0
						&& LocalDateTime.now().compareTo(a.getDateFinEncheres()) < 0
						&& a.getCreateur().getNoUtilisateur() == noUtilisateur;
			}

			if (filtres.get(6)) {
				result = result && LocalDateTime.now().compareTo(a.getDateDebutEncheres()) < 0
						&& a.getCreateur().getNoUtilisateur() == noUtilisateur;
			}

			if (filtres.get(7)) {
				result = result && LocalDateTime.now().compareTo(a.getDateFinEncheres()) >= 0
						&& a.getCreateur().getNoUtilisateur() == noUtilisateur;
			}

			if (choix)

				result = result && (a.getListeEncheres()!=null&&(a.getCreateur().getNoUtilisateur() != noUtilisateur&&a.getListeEncheres().stream().anyMatch(e->e.getCreateur().getNoUtilisateur()==noUtilisateur)));
			else
				result = result &&a.getCreateur().getNoUtilisateur() == noUtilisateur;

			return result;
		}).toList();

		tempList.forEach(a -> System.err.println("oui"));

		if (tempList.isEmpty()) {
			tempList = EncheresArticlesTriees;
			error = true;
			System.err.println("error");
		}
		tempList.forEach(e -> listeArticles.add(e));
		
		
		model.addAttribute("achatsChecked", encheresOuvertes || encheresEnCours || encheresRemportees);
		model.addAttribute("ventesChecked", ventesEnCours || ventesAVenir || ventesTerminees);

		model.addAttribute("enchOuv", encheresOuvertes);
		model.addAttribute("enchCours", encheresEnCours);
		model.addAttribute("enchRemp", encheresRemportees);
		model.addAttribute("venCours", ventesEnCours);
		model.addAttribute("venPrep", ventesAVenir);
		model.addAttribute("venTerm", ventesTerminees);
		model.addAttribute("choix",choix);

		model.addAttribute("error", error);
		model.addAttribute("message", message);

		model.addAttribute("utilisateurs", utilisateurs);
		model.addAttribute("listeArticles", listeArticles);
		model.addAttribute("listeCategories", this.categorieService.getListeCategories());

		return "accueil";
	}

	public List<Enchere> sort(List<Enchere> encheres) {
		Collections.sort(encheres, (ench1, ench2) -> {
			Enchere a = (Enchere) ench1;
			Enchere b = (Enchere) ench2;
			if (a.getMontantEnchere() > b.getMontantEnchere())
				return -1;
			if (a.getMontantEnchere() < b.getMontantEnchere())
				return 1;
			return 0;
		});
		return encheres;
	}

	@GetMapping("/encheresDetails")
	public String afficherEncheresDetails(Model model, @RequestParam("noArticle") int noArticle, Principal principal) {

		ArticleVendu articleVendu = this.articleVenduService.consulterArticleVenduParId(noArticle).get(0);
		Utilisateur utilisateur = this.utilisateurService
				.consulterUtilisateurParId(articleVendu.getCreateur().getNoUtilisateur());
		List<Enchere> encheres = this.enchereService.getEncheresByIdArticle(noArticle);

		articleVendu.setCreateur(utilisateur);
		articleVendu.setListeEncheres(this.sort(encheres));
		
		boolean utilisateurIsAuthentificate = false;
		boolean dateEnchereDebutDepasse = false;
		if (principal != null) {
			String username = principal.getName();
			Utilisateur authenticatedUser = utilisateurService.findByUsername(username);
			if (authenticatedUser != null
					&& authenticatedUser.getNoUtilisateur() == articleVendu.getCreateur().getNoUtilisateur()) {
				utilisateurIsAuthentificate = true;

			}
			if (LocalDateTime.now().isAfter(articleVendu.getDateDebutEncheres())) {
				dateEnchereDebutDepasse = true;
			}
		}
		if (encheres != null && !encheres.isEmpty())
			articleVendu.setListeEncheres(this.sort(encheres));

		model.addAttribute("listCategorie", this.categorieService.getListeCategories());
		model.addAttribute("utilisateurIsAuthentificate", utilisateurIsAuthentificate);
		model.addAttribute("dateEnchereDebutDepasse", dateEnchereDebutDepasse);
		model.addAttribute("articleVendu", articleVendu);

		return "encheres-details";
	}

	@PostMapping("/encheresDetails")
	public String proposerPrixEnchere(@ModelAttribute ArticleVendu articleVendu,
			@RequestParam(name = "proposition", required = false) String proposition,@RequestParam(name = "suppresion", required = false, defaultValue = "false") boolean suppresion, Principal principal) {

		String username = principal.getName();
		Utilisateur authenticatedUser = utilisateurService.findByUsername(username);
		int noArticle = articleVendu.getNoArticle();
		this.articleVenduService.modifierArticle(articleVendu, noArticle);
		

		Retrait retrait = articleVendu.getLieuRetrait();
		retrait.setArticle(articleVendu);
		this.retraitService.modifierRetrait(retrait, noArticle);

		if (suppresion ==true) {
			this.retraitService.supprimerRetrait(retrait, noArticle);
			this.articleVenduService.supprimerArtice(articleVendu, noArticle);
		}
		
		if (proposition != null) {
			int montant = Integer.parseInt(proposition);
			Enchere nouvelleEnchere = new Enchere(LocalDateTime.now(), montant, authenticatedUser, articleVendu);

			if (articleVendu.getListeEncheres() != null && !articleVendu.getListeEncheres().isEmpty()) {
				if (nouvelleEnchere.getMontantEnchere() > this.enchereService
						.getBestEnchere(articleVendu.getNoArticle()).getMontantEnchere()
						&& this.enchereService.getBestEnchere(articleVendu.getNoArticle()).getCreateur()
								.getNoUtilisateur() != nouvelleEnchere.getCreateur().getNoUtilisateur()) {
					this.enchereService.ajouterEnchere(nouvelleEnchere);
				}
			} else if (nouvelleEnchere.getMontantEnchere() > articleVendu.getMiseAPrix())
				this.enchereService.ajouterEnchere(nouvelleEnchere);
		}

		return "redirect:/accueil";

	}

	// Function for sorting Encheres
	public List<ArticleVendu> sortEncheresArticles() {
		List<ArticleVendu> temp = this.articleVenduService.getListeArticles();
		List<ArticleVendu> tempEmpty = this.articleVenduService.getListeArticlesEmpty(); // Those without bids
		List<ArticleVendu> articles = new ArrayList<>();

		// debug
		// temp.forEach(a->System.err.println(a.getListeEncheres().size()));

		// Only one instance of article by noArticle
		temp.forEach(a -> {
			List<ArticleVendu> stream = articles.stream().filter(b -> b.getNoArticle() == a.getNoArticle()).toList();
			if (stream.size() == 0)
				articles.add(a);
			else if (a.getListeEncheres().get(0) != null)
				stream.get(0).getListeEncheres().add(a.getListeEncheres().get(0));
		});
		System.err.println(tempEmpty.size());

		tempEmpty.forEach(a -> {
			if (articles.stream().noneMatch(b -> b.getNoArticle() == a.getNoArticle()))
				articles.add(a);
		});

		articles.forEach(a -> {
			if (a.getListeEncheres() != null && !a.getListeEncheres().isEmpty()) {
				List<Enchere> enchereParArticle = new ArrayList<>();
				a.getListeEncheres().forEach(e -> enchereParArticle.add(e));
				a.setListeEncheres(this.sort(enchereParArticle));
			}
			// Nouvelle fonction pour trier les encheres au sein de chaque article

		});

		return articles;
	}

}
