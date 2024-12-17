package org.enchere.bll;



import org.enchere.dal.EnchereDAO;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class EnchereServiceImpl implements EnchereService {

	private EnchereDAO enchereDao;

	public EnchereServiceImpl(EnchereDAO enchereDao) {
		this.enchereDao = enchereDao;
	}
	
	
}
