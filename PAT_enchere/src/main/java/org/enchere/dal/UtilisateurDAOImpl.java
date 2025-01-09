package org.enchere.dal;

import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.List;

import org.enchere.bo.Utilisateur;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class UtilisateurDAOImpl implements UtilisateurDAO {

	private static final String FIND_ALL = "SELECT no_utilisateur, pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur FROM UTILISATEURS";
	private static final String FIND_BY_ID = "SELECT no_utilisateur, pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur FROM UTILISATEURS WHERE no_utilisateur = :no_utilisateur";
	private static final String FIND_BY_PSEUDO = "SELECT no_utilisateur, pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur FROM UTILISATEURS WHERE pseudo = :pseudo";
	private static final String UPDATE = "UPDATE UTILISATEURS SET pseudo = :pseudo, nom = :nom, prenom = :prenom, email = :email, telephone = :telephone, rue = :rue, credit = :credit, code_postal = :code_postal, ville = :ville, mot_de_passe = :mot_de_passe WHERE no_utilisateur = :no_utilisateur";
	private static final String CREATE_UTILISATEUR = "INSERT INTO UTILISATEURS (pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe,administrateur,credit)VALUES (:pseudo,:nom,:prenom, :email, :telephone, :rue, :code_postal, :ville, :mot_de_passe,0,0)";
	private static final String COUNT_EMAIL = "SELECT COUNT(*) FROM UTILISATEURS WHERE email = :email";
	private static final String COUNT_PSEUDO = "SELECT COUNT(*) FROM UTILISATEURS WHERE pseudo = :pseudo";
	private static final String DELETE_UTILISATEUR = "DELETE FROM ENCHERES WHERE no_utilisateur = :no_utilisateur; DELETE FROM RETRAITS WHERE no_article IN (SELECT no_article FROM ARTICLES_VENDUS WHERE no_utilisateur = :no_utilisateur);DELETE FROM ARTICLES_VENDUS WHERE no_utilisateur = :no_utilisateur; DELETE FROM UTILISATEURS WHERE no_utilisateur = :no_utilisateur";

	private static final String RECUP_MDP = "SELECT mot_de_passe FROM UTILISATEURS WHERE no_utilisateur = :no_utilisateur";
	
	private NamedParameterJdbcTemplate jdbcTemplate;
	private final PasswordEncoder passwordEncoder;

	class UtilisateurRowMapper implements org.springframework.jdbc.core.RowMapper<Utilisateur> {

		@Override
		public Utilisateur mapRow(ResultSet rs, int rowNum) throws SQLException {
			Utilisateur u = new Utilisateur();

			u.setNoUtilisateur(rs.getInt("no_utilisateur"));
			u.setPseudo(rs.getString("pseudo"));
			u.setNom(rs.getString("nom"));
			u.setPrenom(rs.getString("prenom"));
			u.setEmail(rs.getString("email"));
			u.setTelephone(rs.getString("telephone"));
			u.setRue(rs.getString("rue"));
			u.setCodePostal(rs.getString("code_postal"));
			u.setVille(rs.getString("ville"));
			u.setMotDePasse(rs.getString("mot_de_passe"));
			u.setCredit(rs.getInt("credit"));
			u.setAdministrateur(rs.getBoolean("administrateur"));

			return u;
		}

	}

	public UtilisateurDAOImpl(NamedParameterJdbcTemplate jdbcTemplate, PasswordEncoder passwordEncoder) {
		super();
		this.jdbcTemplate = jdbcTemplate;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void createUtilisateur(Utilisateur utilisateur) {

		KeyHolder keyHolder = new GeneratedKeyHolder();
		MapSqlParameterSource map = new MapSqlParameterSource();

		String motDePasseEncode = passwordEncoder.encode(utilisateur.getMotDePasse());

		map.addValue("pseudo", utilisateur.getPseudo());
		map.addValue("nom", utilisateur.getNom());
		map.addValue("prenom", utilisateur.getPrenom());
		map.addValue("email", utilisateur.getEmail());
		map.addValue("telephone", utilisateur.getTelephone());
		map.addValue("rue", utilisateur.getRue());
		map.addValue("code_postal", utilisateur.getCodePostal());
		map.addValue("ville", utilisateur.getVille());
		map.addValue("mot_de_passe", motDePasseEncode);

		jdbcTemplate.update(CREATE_UTILISATEUR, map, keyHolder);

		if (keyHolder != null && keyHolder.getKey() != null) {
			utilisateur.setNoUtilisateur(keyHolder.getKey().intValue());
			;
		}

	}

	@Override
	public Utilisateur read(int noUtilisateur) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("no_utilisateur", noUtilisateur);

		return jdbcTemplate.queryForObject(FIND_BY_ID, map, new UtilisateurRowMapper());
	}

	@Override
	public Utilisateur read(String pseudo) {
		MapSqlParameterSource map = new MapSqlParameterSource();
		map.addValue("pseudo", pseudo);

		return jdbcTemplate.queryForObject(FIND_BY_PSEUDO, map, new UtilisateurRowMapper());
	}

	@Override
	public List<Utilisateur> findAll() {

		return jdbcTemplate.query(FIND_ALL, new UtilisateurRowMapper());
	}

	@Override
	public List<Utilisateur> findUtilisateur(int noUtilisateur) {
		// TODO Auto-generated method stub
		return null;
	}


	public void update(Utilisateur utilisateur) {	

    MapSqlParameterSource map = new MapSqlParameterSource("no_utilisateur", utilisateur.getNoUtilisateur());
   
    System.out.println("Début update - ID utilisateur : " + utilisateur.getNoUtilisateur());
    System.out.println("Mot de passe reçu : " + utilisateur.getMotDePasse());
    String motDePasseEncode = null;
	if (utilisateur.getMotDePasse() != null && !utilisateur.getMotDePasse().isEmpty()) {
		 System.out.println("Nouveau mot de passe détecté - Encodage en cours");
       motDePasseEncode = passwordEncoder.encode(utilisateur.getMotDePasse());
        
        System.out.println("Mot de passe encodé : " + motDePasseEncode);
    } else {
    	System.out.println("Pas de nouveau mot de passe - Récupération de l'ancien");
     motDePasseEncode = jdbcTemplate.queryForObject(RECUP_MDP, map, String.class);
     System.out.println("Ancien mot de passe récupéré : " + motDePasseEncode);
    }
	
	map.addValue("mot_de_passe", motDePasseEncode);
	map.addValue("pseudo", utilisateur.getPseudo());
	map.addValue("nom", utilisateur.getNom());
	map.addValue("prenom", utilisateur.getPrenom());
	map.addValue("email", utilisateur.getEmail());
	map.addValue("telephone", utilisateur.getTelephone());
	map.addValue("rue", utilisateur.getRue());
	map.addValue("code_postal", utilisateur.getCodePostal());
	map.addValue("ville", utilisateur.getVille());
	map.addValue("credit", utilisateur.getCredit());
		
		
	this.jdbcTemplate.update(UPDATE, map);
	}


@Override
public void deleteUser(int noUtilisateur) {
	MapSqlParameterSource map = new MapSqlParameterSource();
	map.addValue("no_utilisateur", noUtilisateur);
	this.jdbcTemplate.update(DELETE_UTILISATEUR, map);
	
}

@Override
public boolean validerEmailUnique(String email) {
	MapSqlParameterSource map = new MapSqlParameterSource();
	map.addValue("email", email);
	int nbEmail = jdbcTemplate.queryForObject(COUNT_EMAIL, map, Integer.class);
	return nbEmail > 0 ? true : false;
}

@Override
public boolean validerPseudoUnique(String pseudo) {
	MapSqlParameterSource map = new MapSqlParameterSource();
	map.addValue("pseudo", pseudo);
	int nbPseudo = jdbcTemplate.queryForObject(COUNT_PSEUDO, map, Integer.class);
	return nbPseudo > 0 ? true : false;
}




}
