package org.enchere.bo;

import java.util.List;

public class Categorie {

		private int noCategorie;
		private String libelle;
		private List<ArticleVendu> articles;
		
		
		public Categorie(int noCategorie, String libelle, List<ArticleVendu> articles) {
			super();
			this.noCategorie = noCategorie;
			this.libelle = libelle;
			this.articles = articles;
		}
		
		
}
