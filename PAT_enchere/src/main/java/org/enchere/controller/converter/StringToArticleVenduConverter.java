package org.enchere.controller.converter;

import org.enchere.bll.ArticleVenduService;
import org.enchere.bo.ArticleVendu;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToArticleVenduConverter implements Converter<String,ArticleVendu> {
	
	private ArticleVenduService articleVenduService;

	public StringToArticleVenduConverter(ArticleVenduService articleVenduService) {
		this.articleVenduService = articleVenduService;
	}



	@Override
	public ArticleVendu convert(String source) {
		ArticleVendu article = this.articleVenduService.consulterArticleVenduParId(Integer.parseInt(source));
		return article;
	}

}
