package org.enchere.controller.converter;

import org.enchere.bll.CategorieService;
import org.enchere.bo.Categorie;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToCategorieConverter implements Converter<String,Categorie>{
	private CategorieService categorieService;
	
	public StringToCategorieConverter(CategorieService categorieService) {
		this.categorieService = categorieService;
	}

	@Override
	public Categorie convert(String source) {
		Categorie categorie = this.categorieService.getCategorieById(Integer.parseInt(source));
		return categorie;
	}

}
