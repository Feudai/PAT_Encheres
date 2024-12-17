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


		public int getMontant_enchere() {
			return montantEnchere;
		}


		public void setMontant_enchere(int montantEnchere) {
			this.montantEnchere = montantEnchere;
		}


		public Utilisateur getEncherit() {
			return createur;
		}


		public void setEncherit(Utilisateur encherit) {
			this.createur = encherit;
		}


		public ArticleVendu getConcerne() {
			return article;
		}


		public void setConcerne(ArticleVendu concerne) {
			this.article = concerne;
		}
		
		
}
