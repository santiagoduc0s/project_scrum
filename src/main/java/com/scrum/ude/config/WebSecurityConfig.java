package com.scrum.ude.config;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.scrum.ude.entity.Usuario;
import com.scrum.ude.service.UsuarioServiceImpl;



@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	//Necesario para evitar que la seguridad se aplique a los resources
    //Como los css, imagenes y javascripts
    String[] resources = new String[]{
            "/include/**","/css/**","/icons/**","/img/**","/js/**","/layer/**"
    };
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
    	 http
         .authorizeRequests()
	        .antMatchers(resources).permitAll()  
	        .antMatchers("/","/login","/registroUsuario").permitAll()
	        .antMatchers("/").access("hasRole('ADMIN')")
	        .antMatchers("/default*").access("hasRole('USER')")
             .anyRequest()
             .authenticated()
             .and()
         .formLogin()
             .loginPage("/login")
             .permitAll()
             .defaultSuccessUrl("/menu")
             .and()
         .logout()
             .permitAll()
             .logoutSuccessUrl("/login?logout");
    	
       
    }
    BCryptPasswordEncoder bCryptPasswordEncoder;
   
    //Crea el encriptador de contraseñas	
    @Bean
    public PasswordEncoder passwordEncoder() {
		 BCryptPasswordEncoder  encoder = new BCryptPasswordEncoder();
       return encoder;
    }
    @Autowired
    UsuarioServiceImpl userDetailsService;
	
    //Registra el service para usuarios y el encriptador de contrasena
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception { 
 
       //setea la password codificada a la base
       
    	auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());  
        
    }
}