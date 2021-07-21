package com.scrum.ude.service;

import java.util.ArrayList ;    
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.scrum.ude.config.WebSecurityConfig;
import com.scrum.ude.controller.UsuarioController;
import com.scrum.ude.dao.IUsuarioDAO;
import com.scrum.ude.entity.Inscripto;
import com.scrum.ude.entity.Tarea;
import com.scrum.ude.entity.Usuario;
import com.scrum.ude.service.Interfaces.IService;

@Service
public class UsuarioServiceImpl implements UserDetailsService,  IService {

	private static final Logger log = LogManager.getLogger(UsuarioServiceImpl.class);
    @Autowired
     private IUsuarioDAO iUsuarioDAO;
    @Autowired
    private WebSecurityConfig ws;
    
    @PersistenceContext
	private EntityManager em;
	
    @Override
     public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		
     //Buscar el usuario con el repositorio y si no existe lanzar una exepcion
     com.scrum.ude.entity.Usuario appUser = 
                 iUsuarioDAO.findByUserName(userName);
//     RedirectAttributes flash = null;
//     flash.addAttribute("danger","Incorrecto el logueo");
     UserDetails user=null;
    
     if(appUser==null) {
    
    	 return user;
     }
	    //Mapear nuestra lista de Authority con la de spring security 
    
    List  rol = new ArrayList<>();
   	 
        // ROLE_USER, ROLE_ADMIN,..
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(appUser.getRol());
            rol.add(grantedAuthority);
		
    //Crear El objeto UserDetails que va a ir en sesion y retornarlo.
     user = (UserDetails) new User(appUser.getUserName(), appUser.getPassword(),rol);
     return user;
	
    }
    
    @Override
	public Usuario findOne(String userName) {

		
		Query query = em.createQuery("select u from Usuario u where u.userName=:user");
		query.setParameter("user", userName);

		Usuario usuario = null;
		try {
			log.info("Chequear ");
			usuario = (Usuario) query.getSingleResult();
		} catch (Exception nre) {
			 log.info("No se ha encontrado  usuarios");

		}
		return usuario;

	}
    
    
    
    @Override
	public Usuario buscarPorId(Long id) {

		
		Query query = em.createQuery("select u from Usuario u where u.id=:id");
		query.setParameter("id", id);

		Usuario usuario = null;
		try {
			log.info("Chequear ");
			usuario = (Usuario) query.getSingleResult();
		} catch (Exception nre) {
			 log.info("No se ha encontrado  usuarios");

		}
		return usuario;

	}
    
	
	@Override
   	public Inscripto buscarUsuarioIncripto(Long id) {

   		
   		Query query = em.createQuery("select i from Inscripto i where i.usuario.id=:id");
   		query.setParameter("id",id);

   		Inscripto inscripto = null;
   		try {
   			log.info("Chequear ");
   			inscripto = (Inscripto) query.getSingleResult();
   			
   			
   			
   		} catch (Exception nre) {
   			 log.info("No se ha encontrado   usuario inscripto");

   		}
   		return inscripto;

   	}
    
	

	@Override
  	public Usuario buscarPorCedula(Long cedula) {

  		
  		Query query = em.createQuery("select u from Usuario u where u.cedula=:cedula");
  		query.setParameter("cedula", cedula);

  		Usuario usuario = null;
  		try {
  			log.info("Chequear ");
  			usuario = (Usuario) query.getSingleResult();
  		} catch (Exception nre) {
  			 log.info("No se ha encontrado  usuarios");

  		}
  		return usuario;

  	}
    
    
	@Override
	public void agregarUsuario(Usuario usuario) {
		String password =ws.passwordEncoder().encode(usuario.getPassword());
		usuario.setPassword(password);
		
		iUsuarioDAO.save(usuario);

	}
	
    
    
    
}