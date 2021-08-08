package com.scrum.ude.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.scrum.ude.entity.Pagina;
import com.scrum.ude.service.CapituloServiceImpl;

@Controller
public class Capitulo1Controller {
	
	@Autowired
	public CapituloServiceImpl capituloImpl;

@GetMapping("/verCapitulo/{id}")

	public String verPaginas(Model model,@PathVariable(value = "id") Long id) {

	List<Pagina> pagina= capituloImpl.buscarPaginas(id);
	
	model.addAttribute("contenido", "<h1>inicio Scrum</h1>");
		model.addAttribute("pagina",pagina);
		
		return "/curso/paginaUno"; 

	}


}
