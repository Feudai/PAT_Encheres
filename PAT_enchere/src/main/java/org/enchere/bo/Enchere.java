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
		
		
}
