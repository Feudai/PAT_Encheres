package org.enchere.controller.converter;

import org.enchere.bll.UtilisateurService;
import org.enchere.bo.Utilisateur;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToUtilisateurConverter implements Converter<String,Utilisateur> {
	private UtilisateurService utilisateurService;
	
	
	public StringToUtilisateurConverter(UtilisateurService utilisateurService) {
		this.utilisateurService = utilisateurService;
	}

	@Override
	public Utilisateur convert(String source) {
		Utilisateur utilisateur = this.utilisateurService.consulterUtilisateurParId(Integer.parseInt(source));
		return utilisateur;
	}

}
