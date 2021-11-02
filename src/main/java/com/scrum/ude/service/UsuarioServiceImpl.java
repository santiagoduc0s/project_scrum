package com.scrum.ude.service;

import java.util.ArrayList;
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
import com.scrum.ude.dao.IUsuarioDAO;
import com.scrum.ude.entity.Proyecto;
import com.scrum.ude.entity.Usuario;
import com.scrum.ude.service.Interfaces.IService;

@Service
public class UsuarioServiceImpl implements UserDetailsService, IService {

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
        com.scrum.ude.entity.Usuario appUser = iUsuarioDAO.findByUserName(userName);
//     RedirectAttributes flash = null;
//     flash.addAttribute("danger","Incorrecto el logueo");
        UserDetails user = null;

        if (appUser == null) {

            return user;
        }
        //Mapear nuestra lista de Authority con la de spring security

        List rol = new ArrayList<>();

        // ROLE_USER, ROLE_ADMIN,..
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(appUser.getRol());
        rol.add(grantedAuthority);

        //Crear El objeto UserDetails que va a ir en sesion y retornarlo.
        user = (UserDetails) new User(appUser.getUserName(), appUser.getPassword(), rol);
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
    public Boolean ExisteUsuario(String userName) {


        Query query = em.createQuery("select u from Usuario u where u.userName=:user");
        query.setParameter("user", userName);

        boolean existe = false;
        try {
            log.info("Chequear ");

            if (query.getSingleResult() != null) {
                existe = true;
            }


        } catch (Exception nre) {
            log.info("No se ha encontrado  usuarios");

        }
        return existe;

    }

    @Override
    public Boolean ExisteMail(String email) {


        Query query = em.createQuery("select u from Usuario u where u.mail=:email");
        query.setParameter("email", email);

        boolean existe = false;
        try {
            log.info("Chequear ");

            if (query.getSingleResult() != null) {
                existe = true;
            }


        } catch (Exception nre) {
            log.info("No se ha encontrado  usuarios");

        }
        return existe;

    }
    
	@Override
    public Usuario buscarPorMail(String email) {


        Query query = em.createQuery("select u from Usuario u where u.mail=:email");
        query.setParameter("email", email);

        Usuario user= new Usuario();
        try {
            log.info("Chequear ");

             user=(Usuario) query.getSingleResult();
        } catch (Exception nre) {
            log.info("No se ha encontrado  usuarios");

        }
        return user;

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
    public Usuario buscarUsuarioPorUsername(String  username) {


        Query query = em.createQuery("select u from Usuario u where u.userName=:username");
        query.setParameter("username", username);

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
        String password = ws.passwordEncoder().encode(usuario.getPassword());
        usuario.setPassword(password);

        iUsuarioDAO.save(usuario);

    }
    
    @Override
   	public List<Usuario> buscarProyectosoVinculadosPorUsuario(Long id) {

		//String sql="SELECT p.* FROM proyecto p, usuario u WHERE u.id=p.id AND  u.id=1";
   		//Query query =em.createNativeQuery(sql);
		Query query = em.createQuery("select u from  Usuario u JOIN FETCH u.proyecto p where  p.id=:id");
   		query.setParameter("id", id);

   		List<Usuario> usuarios = null;
   		try {
   			log.info("Chequear ");
   			usuarios = (List<Usuario>) query.getResultList();
   		} catch (Exception nre) {
   			 log.info("No se ha encontrado  usuarios");

   		}
   		return usuarios;

   	}
    
}