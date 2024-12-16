package org.enchere.bo;

import java.time.LocalDateTime;
import java.util.List;

public class ArticleVendu {
	
	private int noArticle;
	private String nomArticle;
	private String description;
	private LocalDateTime dateDebutEncheres;
	private LocalDateTime dateFinEncheres;
	private float miseAPrix;
	private float prixVente;
	private boolean etatVente;
	
	private List<Enchere> listeEncheres;
	private Retrait lieuRetrait;
	private Categorie categorieArticle;
	
	
	public ArticleVendu(int noArticle, String nomArticle, String description, LocalDateTime dateDebutEncheres,
			LocalDateTime dateFinEncheres, float miseAPrix, float prixVente, boolean etatVente,
			List<Enchere> listeEncheres, Retrait lieuRetrait, Categorie categorieArticle) {
		super();
		this.noArticle = noArticle;
		this.nomArticle = nomArticle;
		this.description = description;
		this.dateDebutEncheres = dateDebutEncheres;
		this.dateFinEncheres = dateFinEncheres;
		this.miseAPrix = miseAPrix;
		this.prixVente = prixVente;
		this.etatVente = etatVente;
		this.listeEncheres = listeEncheres;
		this.lieuRetrait = lieuRetrait;
		this.categorieArticle = categorieArticle;
	}
	
	public ArticleVendu() {}
	
}
