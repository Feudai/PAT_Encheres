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
			exemple.add(new Categorie(2,"Ameublement",null));
			exemple.add(new Categorie(3,"Vêtements",null));
			exemple.add(new Categorie(4,"Sport&Loisirs",null));
			exemple.add(new Categorie(5,"ZiziLOL",null));
			exemple.add(new Categorie(6,"PAUL-SENPAI",null));
			exemple.add(new Categorie(7,"ANTOINE-DESU",null));
			exemple.add(new Categorie(8,"KONO-TIMO",null));


			return exemple;
		}
		
		
		public Categorie findById(int id) {
			Categorie categorie = this.findAll().stream().filter(c->c.getNoCategorie()==id).findFirst().orElse(null);
			
			return categorie;
		}
		
}
