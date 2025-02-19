package org.enchere.bo;


import java.util.List;



import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Utilisateur {

	private int noUtilisateur;
	
	@Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Seuls les caractères alphanumériques sont autorisés")@NotEmpty
	private String pseudo;
	@NotEmpty 
	private String nom;
	@NotEmpty
	private String prenom;
	@Email @NotEmpty @Size(max = 100)
	private String email;
	@Pattern(regexp = "^(?:(?:\\+|00)33|0)\\s*[1-9](?:[\\s.-]*\\d{2}){4}$", message = "Format de téléphone invalide")
	private String telephone;
	@NotEmpty @Pattern(regexp = "^[0-9]{1,4}\\s+[a-zA-ZÀ-ÿ\\s'-]+$", message = "L'adresse doit commencer par un numéro suivi du nom de la rue")
	private String rue;
	@NotEmpty @Pattern(regexp = "^\\d{5}$", message = "Le code postal doit contenir exactement 5 chiffres")
	private String codePostal;
	@NotEmpty
	private String ville;
	
	

	@NotEmpty
	private String motDePasse;

	private String confirmationMotDePasse;
	
	private int credit;
	private boolean administrateur;



	public Utilisateur(int noUtilisateur,
			@Pattern(regexp = "^[a-zA-Z0-9]+$\r\n", message = "Merci de n'entrer que des chiffres et des lettres") String pseudo,
			String nom, String prenom, String email, String telephone, String rue, String codePostal, String ville,
			String motDePasse, int credit, boolean administrateur) {

		this.noUtilisateur = noUtilisateur;
		this.pseudo = pseudo;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.telephone = telephone;
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
		this.motDePasse = motDePasse;
		this.credit = credit;
		this.administrateur = administrateur;

	}

	
	
	public Utilisateur() {
		super();
	}




	public int getNoUtilisateur() {
		return noUtilisateur;
	}

	public void setNoUtilisateur(int noUtilisateur) {
		this.noUtilisateur = noUtilisateur;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getRue() {
		return rue;
	}

	public void setRue(String rue) {
		this.rue = rue;
	}

	public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	public int getCredit() {
		return credit;
	}

	public void setCredit(int f) {
		this.credit = f;
	}

	public boolean isAdministrateur() {
		return administrateur;
	}

	public void setAdministrateur(boolean administrateur) {
		this.administrateur = administrateur;
	}




	public String getConfirmationMotDePasse() {
		return confirmationMotDePasse;
	}



	public void setConfirmationMotDePasse(String confirmationMotDePasse) {
		this.confirmationMotDePasse = confirmationMotDePasse;
	}
	
	

}
