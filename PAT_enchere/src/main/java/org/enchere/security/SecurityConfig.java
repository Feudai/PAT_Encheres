package org.enchere.security;


import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
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
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		HeaderWriterLogoutHandler clearSiteData = new HeaderWriterLogoutHandler(
				new ClearSiteDataHeaderWriter(Directive.ALL));

		http.authorizeHttpRequests((authorize) -> authorize.requestMatchers("/login").permitAll()
				.requestMatchers("/index").permitAll()
				.requestMatchers("/inscription").permitAll()
				.requestMatchers("/enchere-en-cours").permitAll()
				.requestMatchers("/enchere-details").permitAll()
				.requestMatchers("/nouvelleVente").permitAll()
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
		// configuration de la requete permettant de verifier que l'utilisateur est
		// autorisé à se connecter
		jdbcUserDetailsManager.setUsersByUsernameQuery("SELECT pseudo,password,administrateur FROM MEMBRE WHERE pseudo = ?");
		jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
				"SELECT UTILISATEURS.pseudo AS username, ROLES.role AS authority FROM MEMBRE INNER JOIN ROLES ON UTILISATEURS78"
				+ "-3"
				+ ".administrateur = ROLES.IS_ADMIN WHERE MEMBRE.email = ?");

		return jdbcUserDetailsManager;
	}

}
