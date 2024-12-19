package org.enchere.security;

import javax.sql.DataSource;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	// Bean pour l'encodeur de mot de passe
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// Configuration de la sécurité HTTP
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		// Configurer les directives de nettoyage du stockage local, des cookies, etc.
		ClearSiteDataHeaderWriter.Directive[] directives = { ClearSiteDataHeaderWriter.Directive.COOKIES,
				ClearSiteDataHeaderWriter.Directive.CACHE, ClearSiteDataHeaderWriter.Directive.STORAGE };
		

		HeaderWriterLogoutHandler clearSiteData = new HeaderWriterLogoutHandler(
				new ClearSiteDataHeaderWriter(directives));
		

		http.csrf(csrf -> csrf.disable())
		
				.authorizeHttpRequests(authorize -> authorize.requestMatchers("/profil/deleteUser").authenticated()
						.anyRequest().permitAll() // Les autres pages restent publiques
				)
				
	            
	            .formLogin(login -> login
	                    .loginPage("/login")
	                    .defaultSuccessUrl("/accueil", true)
	                    .permitAll()
	                )
	            
				.logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
						.addLogoutHandler(clearSiteData)	                    .logoutUrl("/logout")
	                    .logoutSuccessUrl("/login?logout")
	                    .invalidateHttpSession(true)
	                    .deleteCookies("JSESSIONID")
	                    .permitAll()
	                )
	            .sessionManagement(session -> session
	                    .invalidSessionUrl("/login?session=expired")
	                    .maximumSessions(1) // Facultatif : limite d'une session par utilisateur
	                );


		return http.build();
	}

	// Service pour récupérer les informations de l'utilisateur depuis la base de
	// données
	@Bean
	public UserDetailsService userDetailsService(DataSource dataSource) {
		JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

		// Requête pour récupérer les informations d'utilisateur (pseudo, mot de passe,
		// administrateur)
		jdbcUserDetailsManager.setUsersByUsernameQuery(
				"SELECT pseudo, mot_de_passe, administrateur FROM UTILISATEURS WHERE pseudo = ?");

		// Requête pour récupérer les rôles de l'utilisateur
		jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
				"SELECT pseudo, CASE WHEN administrateur = 1 THEN 'ROLE_ADMIN' ELSE 'ROLE_USER' END AS authority FROM UTILISATEURS WHERE pseudo = ?");

		return jdbcUserDetailsManager;
	}
}
