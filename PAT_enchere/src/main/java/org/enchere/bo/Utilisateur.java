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
}
