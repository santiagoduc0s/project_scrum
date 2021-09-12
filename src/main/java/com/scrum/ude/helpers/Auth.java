package com.scrum.ude.helpers;

import com.scrum.ude.dao.IUsuarioDAO;
import com.scrum.ude.entity.Usuario;
import com.scrum.ude.service.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


public class Auth {

    public static Usuario retornarUsuarioLogueado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        String username = userDetail.getUsername();

        UsuarioServiceImpl service = new UsuarioServiceImpl();



        Usuario usuario = service.findOne(username);

        return usuario;
    }

}
