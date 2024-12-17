package org.enchere.bo;

import java.util.List;

import org.enchere.bll.UtilisateurService;

import jakarta.validation.constraints.Pattern;

public class Utilisateur {

	private int noUtilisateur;
	private String pseudo;
	private String nom;
	private String prenom;
	private String email;
	private String telephone;

	private String rue;
	private String codePostal;
	private String ville;

	private String motDePasse;
	private int credit;
	private boolean administrateur;

	private List<ArticleVendu> articlesAVendre;
	private List<Enchere> articlesAEncherir;
	private List<ArticleVendu> articleAAcheter;


	public Utilisateur(int noUtilisateur,
			@Pattern(regexp = "^[a-zA-Z0-9]+$\r\n", message = "Merci de n'entrer que des chiffres et des lettres") String pseudo,
			String nom, String prenom, String email, String telephone, String rue, String codePostal, String ville,
			String motDePasse, int credit, boolean administrateur) {

		this.noUtilisateur = noUtilisateur;
		this.pseudo = pseudo;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.telephone = telephone;
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
		this.motDePasse = motDePasse;
		this.credit = credit;
		this.administrateur = administrateur;
		this.articlesAVendre = articlesAVendre;
		this.articlesAEncherir = articlesAEncherir;
		this.articleAAcheter = articleAAcheter;
	}

	
	
	public Utilisateur() {
		super();
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

	public String getRue() {
		return rue;
	}

	public void setRue(String rue) {
		this.rue = rue;
	}

	public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
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

	public void setCredit(int f) {
		this.credit = f;
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
