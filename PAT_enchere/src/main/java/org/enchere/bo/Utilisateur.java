package org.enchere.bo;

import java.util.List;

public class Utilisateur {

	private int noUtilisateur;
	private String pseudo;
	private String nom;
	private String prenom;
	private String email;
	private String telephone;
	private Adresse adresse;

	private String motDePasse;
	private float credit;
	private boolean administrateur;
	
	private List<ArticleVendu> articlesAVendre;
	private List<Enchere> articlesAEncherir;
	private List<ArticleVendu> articleAAcheter;
	
	
	public Utilisateur(int noUtilisateur, String pseudo, String nom, String prenom, String email, String telephone,
			Adresse adresse, String motDePasse, float credit, boolean administrateur,
			List<ArticleVendu> articlesAVendre, List<Enchere> articlesAEncherir, List<ArticleVendu> articleAAcheter) {
		super();
		this.noUtilisateur = noUtilisateur;
		this.pseudo = pseudo;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.telephone = telephone;
		this.adresse = adresse;
		this.motDePasse = motDePasse;
		this.credit = credit;
		this.administrateur = administrateur;
		this.articlesAVendre = articlesAVendre;
		this.articlesAEncherir = articlesAEncherir;
		this.articleAAcheter = articleAAcheter;
	}

	public Utilisateur() {
	
}
	
	

	public int getNoUtilisateur() {
		return noUtilisateur;
	}

	public void setNoUtilisateur(int noUtilisateur) {
		this.noUtilisateur = noUtilisateur;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Adresse getAdresse() {
		return adresse;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	public float getCredit() {
		return credit;
	}

	public void setCredit(float credit) {
		this.credit = credit;
	}

	public boolean isAdministrateur() {
		return administrateur;
	}

	public void setAdministrateur(boolean administrateur) {
		this.administrateur = administrateur;
	}

	public List<ArticleVendu> getArticlesAVendre() {
		return articlesAVendre;
	}

	public void setArticlesAVendre(List<ArticleVendu> articlesAVendre) {
		this.articlesAVendre = articlesAVendre;
	}

	public List<Enchere> getArticlesAEncherir() {
		return articlesAEncherir;
	}

	public void setArticlesAEncherir(List<Enchere> articlesAEncherir) {
		this.articlesAEncherir = articlesAEncherir;
	}

	public List<ArticleVendu> getArticleAAcheter() {
		return articleAAcheter;
	}

	public void setArticleAAcheter(List<ArticleVendu> articleAAcheter) {
		this.articleAAcheter = articleAAcheter;
	}
	
	
	
}
