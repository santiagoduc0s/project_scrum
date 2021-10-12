package com.scrum.ude.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.scrum.ude.entity.Capitulo;
import com.scrum.ude.entity.Pagina;
import com.scrum.ude.entity.Usuario;
import com.scrum.ude.service.CapituloServiceImpl;
import com.scrum.ude.service.UsuarioServiceImpl;

@Controller
public class Capitulo1Controller {

	@Autowired
	private UsuarioController usuarioController;
	
	@Autowired
    private UsuarioServiceImpl usuarioService;

    @Autowired
    public CapituloServiceImpl capituloImpl;

    @GetMapping("/verCapitulo/{id}")

    public String verPaginas(Model model, @PathVariable(value = "id") Long id) {

        Authentication auth = usuarioController.retornarUsuarioLogueado();

        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        Usuario user= usuarioService.findOne(auth.getName());

        model.addAttribute("autoridad", auth.getAuthorities().toString());

        Capitulo capitulo = capituloImpl.buscarPaginas(id);

        model.addAttribute("contenido", "<h1>inicio Scrum</h1>");
        model.addAttribute("pagina", capitulo);
        model.addAttribute("usuario",user );


        return "/curso/paginaUno";

    }


//@GetMapping("/verCapitulo")
//
//public String verPaginas(Model model) {
//
//List<Capitulo> capitulo= capituloImpl.buscarPaginas();
//
//model.addAttribute("contenido", "<h1>inicio Scrum</h1>");
//	model.addAttribute("pagina",capitulo);
//	
//	
//	return "/curso/paginaUno"; 
//
//}


}



