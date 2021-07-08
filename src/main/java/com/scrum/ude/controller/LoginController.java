package com.scrum.ude.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Scope("session")
public class LoginController {

   //primer acceso a la aplicacion login
	@GetMapping({"/","/login", ""})
    public String index() {
        
		return "/login/index";
    }

	
	//toma Usuario y autoridad  lo manda a la seccion del usuario en el  menu
    @RequestMapping("/menu")
    public String defaultAfterLogin(HttpServletRequest request, Model model) {

        
    	request.getCookies();
    	
    	Authentication auth = SecurityContextHolder
                .getContext()
                .getAuthentication();

        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        model.addAttribute("autoridad",userDetail.getAuthorities().toString());

        return "/menu/menu";
    }

}
