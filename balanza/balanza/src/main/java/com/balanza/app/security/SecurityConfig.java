package com.balanza.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.balanza.app.service.UsuarioService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	public UsuarioService usuarioServicio;
	
	public void configureGlobal (AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(usuarioServicio).passwordEncoder( new BCryptPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		
		http.headers().frameOptions().sameOrigin().and()
			.authorizeRequests()
				.antMatchers("/css/", "/js/", "/img/")
				.permitAll()
			.and().formLogin()
				.loginPage("/login")
					.loginProcessingUrl("/logincheck")
					.usernameParameter("email")
					.passwordParameter("password")
					.defaultSuccessUrl("/inicio")
					.permitAll()
			.and().logout()
					.logoutUrl("/logout")
					.logoutSuccessUrl("/")
					.permitAll();
		

	
		http.authorizeRequests().antMatchers("/css/*", "/js/*", "/img/*", "/**").permitAll().and().formLogin()
				.loginPage("/login").loginProcessingUrl("/logincheck").usernameParameter("email")
				.passwordParameter("password").defaultSuccessUrl("/inicio").failureUrl("/login?error=error").permitAll().and()
				.logout().logoutUrl("/logout").logoutSuccessUrl("/").permitAll().and().csrf().disable();
		//http.authorizeRequests().antMatchers("/css/*", "/js/*", "/img/*", "/**").permitAll();

	}
	
	
	
}
