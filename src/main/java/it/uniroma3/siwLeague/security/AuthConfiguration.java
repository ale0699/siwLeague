package it.uniroma3.siwLeague.security;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class AuthConfiguration {
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
		.dataSource(dataSource)
		.authoritiesByUsernameQuery("SELECT username, ruolo from credenziali WHERE username=?")
		.usersByUsernameQuery("SELECT username, password, 1 as enabled FROM credenziali WHERE username=?");
	}

	@Bean
	protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
		
		http
		.csrf().and().cors().disable()
		.authorizeHttpRequests()
		.requestMatchers(HttpMethod.GET, "/admin/**").hasAnyAuthority("ADMIN")
		.requestMatchers(HttpMethod.POST, "/admin/**").hasAnyAuthority("ADMIN")
		.requestMatchers(HttpMethod.GET, "/manager/**").hasAnyAuthority("DEFAULT", "ADMIN")
        .requestMatchers(HttpMethod.POST, "/manager/**").hasAnyAuthority("DEFAULT", "ADMIN")
		.anyRequest().permitAll()
		.and().formLogin()
		.loginPage("/login").failureUrl("/login/error").defaultSuccessUrl("/")
		.and().logout()
		.logoutSuccessUrl("/").invalidateHttpSession(true).deleteCookies("JSESSIONID")
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).clearAuthentication(true).permitAll();
		
		return http.build();
	}
	
	@Bean
	protected PasswordEncoder passwordEncoder(){
		
		return new BCryptPasswordEncoder();
	}

}
