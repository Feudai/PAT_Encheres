package org.enchere.bll;



import org.enchere.dal.RetraitDAO;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class RetraitServiceImpl implements RetraitService {

	private RetraitDAO retraitDao;

	public RetraitServiceImpl(RetraitDAO retraitDao) {
		this.retraitDao = retraitDao;
	}
	
}
