package com.scrum.ude.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Capitulo1Controller {

@GetMapping("/verCapitulo/{id}")

	public String verPaginas(Model model,@RequestParam("id")Long id) {

	//Pagina pagina= 	
	model.addAttribute("contenido", "<h1>inicio Scrum</h1>");
		
		return "/curso/paginaUno"; 

	}


}
