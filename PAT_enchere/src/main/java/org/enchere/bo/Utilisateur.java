package org.enchere.bo;

import java.util.List;

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
	private float credit;
	private boolean administrateur;
	
	private List<ArticleVendu> articlesAVendre;
	private List<Enchere> articlesAEncherir;
	private List<ArticleVendu> articleAAcheter;
}
