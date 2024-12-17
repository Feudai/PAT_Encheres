package org.enchere.dal;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("sql")
public class CategorieDAOImpl implements CategorieDAO {

}
