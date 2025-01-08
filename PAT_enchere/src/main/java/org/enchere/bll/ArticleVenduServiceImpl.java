package org.enchere.bll;

import java.util.List;

import org.enchere.bo.ArticleVendu;
import org.enchere.dal.ArticleVenduDAO;
import org.springframework.stereotype.Service;

@Service
public class ArticleVenduServiceImpl implements ArticleVenduService {

	private ArticleVenduDAO articleVenduDAO;

	public ArticleVenduServiceImpl(ArticleVenduDAO articleVenduDAO) {
		this.articleVenduDAO = articleVenduDAO;
	}
	
	public 	List<ArticleVendu> 	getListeArticles(){
		return this.articleVenduDAO.findAll();
	}
	
	public 	List<ArticleVendu> 	getListeArticlesEmpty(){
		return this.articleVenduDAO.findAllEmpty();
	}


	public List<ArticleVendu> ajouterArticle(ArticleVendu article, int noUtilisateur) {
		this.articleVenduDAO.create(article, noUtilisateur);
		return this.articleVenduDAO.findAll();
	}
	
	@Override
	public void modifierArticle(ArticleVendu article, int noUtilisateur) {
		this.articleVenduDAO.modifierArticle(article, noUtilisateur);
		
	}


	@Override
	public List<ArticleVendu> consulterArticleVenduParId(int noArticle) {
		return this.articleVenduDAO.findById(noArticle);
	}
	
	
	@Override
	public List<ArticleVendu> consulterArticleVenduEmptyParId(int noArticle) {
		return this.articleVenduDAO.findByIdEmpty(noArticle);
	}


	@Override
	public void modifierNomImage(String cheminImage, int noArticle) {
		this.articleVenduDAO.modifierNomImage(cheminImage,noArticle);
		
	}




}
