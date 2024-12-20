package org.enchere.controller.converter;

import org.enchere.bll.EnchereService;
import org.enchere.bo.Enchere;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToEnchereConverter implements Converter<String,Enchere> {

	EnchereService enchereService;
	
	public StringToEnchereConverter(EnchereService enchereService) {
		this.enchereService = enchereService;
	}


	@Override
	public Enchere convert(String source) {
		Enchere enchere = this.enchereService.getBestEnchere(Integer.parseInt(source));
		return null;
	}

}
