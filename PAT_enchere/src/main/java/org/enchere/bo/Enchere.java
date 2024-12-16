package org.enchere.bo;

import java.time.LocalDateTime;

public class Enchere {

		private LocalDateTime dateEnchere;
		private float montant_enchere;
		private Utilisateur encherit;
		private ArticleVendu concerne;
		
		
		public Enchere(LocalDateTime dateEnchere, float montant_enchere, Utilisateur encherit, ArticleVendu concerne) {
			super();
			this.dateEnchere = dateEnchere;
			this.montant_enchere = montant_enchere;
			this.encherit = encherit;
			this.concerne = concerne;
		}

		
		

		public LocalDateTime getDateEnchere() {
			return dateEnchere;
		}


		public void setDateEnchere(LocalDateTime dateEnchere) {
			this.dateEnchere = dateEnchere;
		}


		public float getMontant_enchere() {
			return montant_enchere;
		}


		public void setMontant_enchere(float montant_enchere) {
			this.montant_enchere = montant_enchere;
		}


		public Utilisateur getEncherit() {
			return encherit;
		}


		public void setEncherit(Utilisateur encherit) {
			this.encherit = encherit;
		}


		public ArticleVendu getConcerne() {
			return concerne;
		}


		public void setConcerne(ArticleVendu concerne) {
			this.concerne = concerne;
		}
		
		
}
