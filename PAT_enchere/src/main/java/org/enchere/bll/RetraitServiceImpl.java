package org.enchere.bll;



import org.enchere.dal.RetraitDAO;

import org.springframework.stereotype.Service;

@Service
public class RetraitServiceImpl implements RetraitService {

	private RetraitDAO retraitDao;

	public RetraitServiceImpl(RetraitDAO retraitDao) {
		this.retraitDao = retraitDao;
	}
	
}
