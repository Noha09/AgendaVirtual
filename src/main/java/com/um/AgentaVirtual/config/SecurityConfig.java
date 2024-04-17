package com.um.AgentaVirtual.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private DataSource dataSource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth)	throws Exception {
		auth.
			jdbcAuthentication()
				.usersByUsernameQuery("SELECT email, password, activo FROM usuario WHERE email = ?")
				.authoritiesByUsernameQuery("SELECT U.email, R.nombre FROM usuario U INNER JOIN role_usuario RU ON(U.idusuario = RU.id_usuario) INNER JOIN role R ON(RU.id_role = R.idrole) WHERE U.EMAIL = ?")
				.dataSource(dataSource)
				.passwordEncoder(bCryptPasswordEncoder);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {		
		http
			.csrf().disable()
				.authorizeHttpRequests()
				.antMatchers("/").permitAll()
				.antMatchers("/login*").permitAll()
				.antMatchers("/registrar").permitAll()
				.antMatchers("/roles").permitAll()
				.antMatchers("/admin/**")
				.hasAuthority("Admin")
				.anyRequest().authenticated()
			.and().formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/inicio",true)
				.failureUrl("/login?error=true")
				.usernameParameter("usuario")
				.passwordParameter("password")
			.and().logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))				
				.logoutSuccessUrl("/").and().exceptionHandling()
				.accessDeniedPage("/access-denied")
			.and().sessionManagement();
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web
	       .ignoring()
	       .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/fonts/**", "/images/**");
	}

    @Bean
    HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }
}
