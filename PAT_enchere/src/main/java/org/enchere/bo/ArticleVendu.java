package org.enchere.bo;

import java.time.LocalDateTime;

import java.util.List;


public class ArticleVendu {
	
	private int noArticle;
	private String nomArticle;
	private String description;
	private LocalDateTime dateDebutEncheres;
	private LocalDateTime dateFinEncheres;
	private int miseAPrix;
	private int prixVente;
	private boolean etatVente;
	private Utilisateur createur;
	private String cheminImage;
	
	private List<Enchere> listeEncheres;
	private Retrait lieuRetrait;
	private Categorie categorieArticle;
	
	
	public ArticleVendu(int noArticle, String nomArticle, String description, LocalDateTime dateDebutEncheres,
			LocalDateTime dateFinEncheres, int miseAPrix, int prixVente, boolean etatVente,
			List<Enchere> listeEncheres, Retrait lieuRetrait, Categorie categorieArticle, Utilisateur createur) {
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
	
	
	
	public ArticleVendu(int noArticle, String nomArticle, String description, LocalDateTime dateDebutEncheres,
			LocalDateTime dateFinEncheres, int miseAPrix, int prixVente, boolean etatVente, Utilisateur createur,
			String cheminImage, List<Enchere> listeEncheres, Retrait lieuRetrait, Categorie categorieArticle) {
		super();
		this.noArticle = noArticle;
		this.nomArticle = nomArticle;
		this.description = description;
		this.dateDebutEncheres = dateDebutEncheres;
		this.dateFinEncheres = dateFinEncheres;
		this.miseAPrix = miseAPrix;
		this.prixVente = prixVente;
		this.etatVente = etatVente;
		this.createur = createur;
		this.cheminImage = cheminImage;
		this.listeEncheres = listeEncheres;
		this.lieuRetrait = lieuRetrait;
		this.categorieArticle = categorieArticle;
	}



	public String getCheminImage() {
		return cheminImage;
	}



	public void setCheminImage(String cheminImage) {
		this.cheminImage = cheminImage;
	}



	public ArticleVendu() {}
	
	

	public int getNoArticle() {
		return noArticle;
	}

	public void setNoArticle(int noArticle) {
		this.noArticle = noArticle;
	}

	public String getNomArticle() {
		return nomArticle;
	}

	public void setNomArticle(String nomArticle) {
		this.nomArticle = nomArticle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getDateDebutEncheres() {
		return dateDebutEncheres;
	}

	public void setDateDebutEncheres(LocalDateTime dateDebutEncheres) {
		this.dateDebutEncheres = dateDebutEncheres;
	}

	public LocalDateTime getDateFinEncheres() {
		return dateFinEncheres;
	}

	public void setDateFinEncheres(LocalDateTime dateFinEncheres) {
		this.dateFinEncheres = dateFinEncheres;
	}

	public int getMiseAPrix() {
		return miseAPrix;
	}

	public void setMiseAPrix(int miseAPrix) {
		this.miseAPrix = miseAPrix;
	}

	public float getPrixVente() {
		return prixVente;
	}

	public void setPrixVente(int prixVente) {
		this.prixVente = prixVente;
	}

	public boolean isEtatVente() {
		return etatVente;
	}

	public void setEtatVente(boolean etatVente) {
		this.etatVente = etatVente;
	}

	public List<Enchere> getListeEncheres() {
		return listeEncheres;
	}

	public void setListeEncheres(List<Enchere> listeEncheres) {
		this.listeEncheres = listeEncheres;
	}

	public Retrait getLieuRetrait() {
		return lieuRetrait;
	}

	public void setLieuRetrait(Retrait lieuRetrait) {
		this.lieuRetrait = lieuRetrait;
	}

	public Categorie getCategorieArticle() {
		return categorieArticle;
	}

	public void setCategorieArticle(Categorie categorieArticle) {
		this.categorieArticle = categorieArticle;
	}

	public Utilisateur getCreateur() {
		return createur;
	}

	public void setCreateur(Utilisateur createur) {
		this.createur = createur;
	}



	@Override
	public String toString() {
		return "ArticleVendu [noArticle=" + noArticle + ", nomArticle=" + nomArticle + ", description=" + description
				+ ", dateDebutEncheres=" + dateDebutEncheres + ", dateFinEncheres=" + dateFinEncheres + ", miseAPrix="
				+ miseAPrix + ", prixVente=" + prixVente + ", etatVente=" + etatVente + ", createur=" + createur
				+ ", cheminImage=" + cheminImage + ", listeEncheres=" + listeEncheres + ", lieuRetrait=" + lieuRetrait
				+ ", categorieArticle=" + categorieArticle + "]";
	}
	
	
	
	
}
