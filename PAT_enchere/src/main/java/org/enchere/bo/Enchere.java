package org.enchere.bo;

import java.time.LocalDateTime;

public class Enchere {

		private LocalDateTime dateEnchere;
		private int montantEnchere;
		private Utilisateur createur;
		private ArticleVendu article;
		
		
		public Enchere(LocalDateTime dateEnchere, int montantEnchere, Utilisateur createur, ArticleVendu article) {
			super();
			this.dateEnchere = dateEnchere;
			this.montantEnchere = montantEnchere;
			this.createur = createur;
			this.article = article;
		}


		public LocalDateTime getDateEnchere() {
			return dateEnchere;
		}


		public void setDateEnchere(LocalDateTime dateEnchere) {
			this.dateEnchere = dateEnchere;
		}


		public int getMontantEnchere() {
			return montantEnchere;
		}


		public void setMontantEnchere(int montantEnchere) {
			this.montantEnchere = montantEnchere;
		}


		public Utilisateur getCreateur() {
			return createur;
		}


		public void setCreateur(Utilisateur createur) {
			this.createur = createur;
		}


		public ArticleVendu getArticle() {
			return article;
		}


		public void setArticle(ArticleVendu article) {
			this.article = article;
		}

		
		

		
		
}
