package org.enchere.bo;

public class Retrait {
	
	private Adresse adresse;
	private ArticleVendu article;
	
	public Retrait(Adresse adresse, ArticleVendu article) {
		super();
		this.adresse = adresse;
		this.article = article;
	}
	
	

	public Adresse getAdresse() {
		return adresse;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}

	public ArticleVendu getArticle() {
		return article;
	}

	public void setArticle(ArticleVendu article) {
		this.article = article;
	}
	
	
	
	
}	
