package org.enchere.bo;

import java.time.LocalDate;
import java.util.List;

public class ArticleVendu {
	
	private int noArticle;
	private String nomArticle;
	private String description;
	private LocalDate dateDebutEncheres;
	private LocalDate dateFinEncheres;
	private float miseAPrix;
	private float prixVente;
	private boolean etatVente;
	
	private List<Enchere> listeEncheres;
	private Retrait lieuRetrait;
	private Categorie categorieArticle;
	
}
