package com.scrum.ude.controller;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.scrum.ude.config.WebSecurityConfig;
import com.scrum.ude.dao.IUsuarioDAO;
import com.scrum.ude.entity.Usuario;
import com.scrum.ude.service.UsuarioServiceImpl;

@Controller
@Scope("session")
public class LoginController {

    @Autowired
    private IUsuarioDAO usuarioDAO;

    @Autowired
    private UsuarioController usuarioController;

    @Autowired
    private UsuarioServiceImpl usuarioService;

    @Autowired
    private WebSecurityConfig ws;

    //primer acceso a la aplicacion login
    @GetMapping("/login")
    public String index() {
        return "/login/index";
    }

    @PostMapping("/login")
    public String buscar(@RequestParam(value = "username") String user, @RequestParam(value = "password") String password, RedirectAttributes flash) {

        UserDetails us = usuarioService.loadUserByUsername(user);

        if (us == null) {
            return "/login/index";
        }

        if (ws.passwordEncoder().matches(password, us.getPassword())) {

            System.out.println("CONTRASEÑAS IGUALES");

            Authentication authentication = new UsernamePasswordAuthenticationToken(us, null, us.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);

        } else {
            flash.addFlashAttribute("success", "error password o usuario");
            return "/login/index";
        }

        return "";
    }

    @PostMapping("/")
    public String logout() {
        SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
        return "redirect:/menu";

    }


    //toma Usuario y autoridad  lo manda a la seccion del usuario en el  menu
    @RequestMapping("/menu")
    public String defaultAfterLogin(Authentication auth, HttpServletRequest request, Model model, RedirectAttributes flash) {

        request.getCookies();

        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        Usuario user = usuarioDAO.findByUserName(userDetail.getUsername());
        model.addAttribute("autoridad", userDetail.getAuthorities().toString());
        model.addAttribute("usuario", user);

        return "/menu/menu";
    }


    //toma Usuario y autoridad  lo manda a la seccion del usuario en el  menu
    @RequestMapping("/menuCambiado/{user}")
    public String defaultpassword(HttpServletRequest request, @PathVariable(value = "user") String us, Model model) {

        UserDetails userDetail = usuarioService.loadUserByUsername(us);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Usuario user = usuarioDAO.findByUserName(userDetail.getUsername());
        model.addAttribute("autoridad", userDetail.getAuthorities().toString());
        model.addAttribute("usuario", user);

        return "/menu/menu";
    }

	@GetMapping("/recuperar-contraseña")
	public String recoverPassword() {
		return "login/recover-password";
	}


}

