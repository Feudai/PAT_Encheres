package org.enchere.security;


import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter.Directive;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		HeaderWriterLogoutHandler clearSiteData = new HeaderWriterLogoutHandler(
				new ClearSiteDataHeaderWriter(Directive.ALL));

		http.authorizeHttpRequests((authorize) -> authorize.requestMatchers("/login").permitAll()
				.requestMatchers("/index").permitAll()
				.requestMatchers("/inscription").permitAll()
				.requestMatchers("/createUser").permitAll()
				.requestMatchers("/enchere-en-cours").permitAll()
				.requestMatchers("/enchere-details").permitAll()
				.requestMatchers("/nouvelle-vente").permitAll()
				.requestMatchers("/profile").permitAll()
				.requestMatchers("/utilisateur").permitAll()
				.requestMatchers("/").permitAll()
				.requestMatchers("/css/**").permitAll()
				.requestMatchers("/images/**").permitAll()
				.requestMatchers("/films/enchere-gestion").hasAnyAuthority("ROLE_ADMIN").anyRequest()
				.authenticated())
				.httpBasic(Customizer.withDefaults()).formLogin(form -> form.loginPage("/login").permitAll())
				.logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
						.addLogoutHandler(clearSiteData) // nettoye

				);

		return http.build();
	}


	
	@Bean
	public UserDetailsService userDetailsService(DataSource dataSource) {
	    JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
	    
	    // Requête pour vérifier les informations d'utilisateur (pseudo, mot de passe, administrateur)
	    jdbcUserDetailsManager.setUsersByUsernameQuery("SELECT pseudo, mot_de_passe, administrateur FROM UTILISATEURS WHERE pseudo = ?");
	    
	    // Requête pour récupérer les autorités (rôles) de l'utilisateur
	    jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
	        "SELECT pseudo, CASE WHEN administrateur = 1 THEN 'ROLE_ADMIN' ELSE 'ROLE_USER' END AS authority FROM UTILISATEURS WHERE pseudo = ?"
	    );

	    return jdbcUserDetailsManager;
	}

}
