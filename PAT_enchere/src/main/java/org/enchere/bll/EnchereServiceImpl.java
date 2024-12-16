package org.enchere.bll;

import org.enchere.dal.EnchereDAO;
import org.springframework.stereotype.Service;

@Service
public class EnchereServiceImpl implements EnchereService {

	private EnchereDAO enchereDao;

	public EnchereServiceImpl(EnchereDAO enchereDao) {
		this.enchereDao = enchereDao;
	}
	
	
}
