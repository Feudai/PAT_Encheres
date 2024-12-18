package org.enchere.dal;

import java.util.ArrayList;
import java.util.List;

import org.enchere.bo.Categorie;
import org.springframework.stereotype.Repository;

@Repository
public class CategorieDAOImpl implements CategorieDAO {

	
	public List<Categorie> create(Categorie categorie){
		
		return null;
	}

		public List<Categorie> findAll(){
			//TEMPORAIRE
			List<Categorie> exemple = new ArrayList<Categorie>();
			exemple.add(new Categorie(1,"Informatique",null));
			exemple.add(new Categorie(1,"Ameublement",null));
			exemple.add(new Categorie(1,"Vêtements",null));
			exemple.add(new Categorie(1,"Sport&Loisirs",null));
			exemple.add(new Categorie(1,"ZiziLOL",null));
			exemple.add(new Categorie(1,"PAUL-SENPAI",null));
			exemple.add(new Categorie(1,"ANTOINE-DESU",null));
			exemple.add(new Categorie(1,"KONO-TIMO",null));


			return exemple;
		}
}
