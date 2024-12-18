package org.enchere.dal;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.enchere.bo.ArticleVendu;
import org.enchere.bo.Categorie;
import org.enchere.bo.Enchere;
import org.enchere.bo.Utilisateur;
import org.springframework.stereotype.Repository;

@Repository
public class EnchereDAOImpl implements EnchereDAO {
	
	

	public List<Enchere> create(Enchere enchere){
		
		return null;
	}

		public List<Enchere> findAll(){
			List<Enchere> exemple = new ArrayList<>();
			exemple.add(new Enchere(LocalDateTime.now(),1,new Utilisateur(), new ArticleVendu()));
			exemple.add(new Enchere(LocalDateTime.now(),2,new Utilisateur(), new ArticleVendu()));
			exemple.add(new Enchere(LocalDateTime.now(),3,new Utilisateur(), new ArticleVendu()));
			exemple.add(new Enchere(LocalDateTime.now(),4,new Utilisateur(), new ArticleVendu()));
			
			return exemple;
		}
		


}
