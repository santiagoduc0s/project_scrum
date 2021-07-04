package com.scrum.ude.controller;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scrum.ude.entity.Usuario;
import com.scrum.ude.service.UsuarioServiceImpl;

@Controller
@Scope("session")
public class LoginController {

    @GetMapping({"/","/login", ""})
    public String index() {
        return "index";
    }

    @RequestMapping("/menu")
    public String defaultAfterLogin(HttpServletRequest request, Model model) {

        Authentication auth = SecurityContextHolder
                .getContext()
                .getAuthentication();

        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        model.addAttribute("autoridad",userDetail.getAuthorities().toString());

        return "menu";
    }


//	
//	@GetMapping("/menu")
//	public String menu() {
//		return "menu";
//	}

//	@GetMapping("/user")
//	public String user() {
//		return "user";
//	}
//	
//	@GetMapping("/admin")
//	public String admin() {
//		return "admin";
//	}
}
