package com.scrum.ude.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.scrum.ude.entity.Pagina;
import com.scrum.ude.service.PaginaServiceImpl;
@Controller
public class PaginaController {
	
	@Autowired
	public PaginaServiceImpl paginaImpl;



@GetMapping("/verPag/{id}")

public String verPagina(Model model,@PathVariable(value = "id") Long id) {

	Pagina pagina=null;
	pagina= paginaImpl.obtenerContenido(id);

	model.addAttribute("pagina",pagina);
	
	
	return "/curso/pagina"; 

}


}

