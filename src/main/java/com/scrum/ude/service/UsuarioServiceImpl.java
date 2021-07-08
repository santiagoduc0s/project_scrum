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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.scrum.ude.config.WebSecurityConfig;
import com.scrum.ude.dao.IUsuarioDAO;
import com.scrum.ude.entity.CodigoRegistro;
import com.scrum.ude.entity.Proyecto;
import com.scrum.ude.entity.Tarea;
import com.scrum.ude.entity.Usuario;


@Service
public class UsuarioServiceImpl implements UserDetailsService, IService {

	
	private static final Logger log = LogManager.getLogger(UsuarioServiceImpl.class);
    @Autowired
    IUsuarioDAO iUsuarioDAO;
    @Autowired
    private WebSecurityConfig ws;
    
    @PersistenceContext
	private EntityManager em;
	
    @Override
     public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		
     //Buscar el usuario con el repositorio y si no existe lanzar una exepcion
     com.scrum.ude.entity.Usuario appUser = 
                 iUsuarioDAO.findByUserName(userName);
     
     UserDetails users=null;
	    //Mapear nuestra lista de Authority con la de spring security 
    
     
    List  rol = new ArrayList<>();
   	 
        // ROLE_USER, ROLE_ADMIN,..
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(appUser.getRol());
            rol.add(grantedAuthority);
		
    //Crear El objeto UserDetails que va a ir en sesion y retornarlo.
     users = (UserDetails) new User(appUser.getUserName(), appUser.getPassword(),rol);
     return users;
	
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
  	public CodigoRegistro findCodigo(String codigo) {

  		
  		Query query = em.createQuery("select c from CodigoRegistro c where c.codigo=:codigo");
  		query.setParameter("codigo", codigo);

  		CodigoRegistro codigoRegistro = null;
  		try {
  			log.info("Chequear ");
  			codigoRegistro = (CodigoRegistro) query.getSingleResult();
  		} catch (Exception nre) {
  			 log.info("No se ha encontrado  codigoRegistro");

  		}
  		return codigoRegistro;

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
   	public List<Proyecto> buscarProyectoPorUsuario(Long id) {

   		
   		Query query = em.createQuery("select p from Proyecto p where p.usuario.id=:id");
   		query.setParameter("id", id);

   		List<Proyecto> proyectos = null;
   		try {
   			log.info("Chequear ");
   			proyectos = (List<Proyecto>) query.getResultList();
   		} catch (Exception nre) {
   			 log.info("No se ha encontrado  usuarios");

   		}
   		return proyectos;

   	}
	
	
	
	@Override
   	public Proyecto buscarProyectoPorUsuarioWithTitulo(Long id,String titulo) {

   		
   		Query query = em.createQuery("select p from Proyecto p where p.usuario.id=:id and p.titulo=:titulo");
   		query.setParameter("id", id);
   		query.setParameter("titulo", titulo);

   		Proyecto proyecto = null;
   		try {
   			log.info("Chequear ");
   			proyecto = (Proyecto) query.getSingleResult();
   		} catch (Exception nre) {
   			 log.info("No se ha encontrado  usuarios");

   		}
   		return proyecto;

   	}
    
    @Override
   	public Proyecto buscarPorIdProyecto(Long id) {

   		
   		Query query = em.createQuery("select p from Proyecto p where p.id=:id");
   		query.setParameter("id", id);

   		Proyecto proyecto = null;
   		try {
   			log.info("Chequear ");
   			proyecto = (Proyecto) query.getSingleResult();
   		} catch (Exception nre) {
   			 log.info("No se ha encontrado  proyecto");

   		}
   		return proyecto;

   	}
    
    
    
	@Override
   	public Tarea buscarPorNombreTarea(String nombre) {

   		
   		Query query = em.createQuery("select t from Tarea t where t.nombre=:nombre");
   		query.setParameter("nombre", nombre);

   		Tarea tarea = null;
   		try {
   			log.info("Chequear ");
   			tarea = (Tarea) query.getSingleResult();
   		} catch (Exception nre) {
   			 log.info("No se ha encontrado  nombre con dicha tarea");

   		}
   		return tarea;

   	}
    
    
    

//	@Override
//  	public Usuario buscarPorCedula(Long cedula) {
//
//  		
//  		Query query = em.createQuery("select u from Usuario u where u.cedula=:cedula");
//  		query.setParameter("cedula", cedula);
//
//  		Usuario usuario = null;
//  		try {
//  			log.info("Chequear ");
//  			usuario = (Usuario) query.getSingleResult();
//  		} catch (Exception nre) {
//  			 log.info("No se ha encontrado  usuarios");
//
//  		}
//  		return usuario;
//
//  	}
    
    
	@Override
	public void agregarUsuario(Usuario usuario) {
		String password =ws.passwordEncoder().encode(usuario.getPassword());
		usuario.setPassword(password);
		
		iUsuarioDAO.save(usuario);

	}
	
    
    
    
}