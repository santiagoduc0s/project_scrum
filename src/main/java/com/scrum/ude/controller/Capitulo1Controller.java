package com.scrum.ude.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Capitulo1Controller {

@GetMapping("/inicio-scrum")

	public String pagina1(Model model) {

		model.addAttribute("contenido", "<h1>inicio Scrum</h1>");
		
		return "pagina"; 

	}

@GetMapping("/valores")

public String pagina2(Model model) {

	model.addAttribute("titulo", "<h1>Valores</h1>");
	
	model.addAttribute("contenido", "<p>Bievenidos  a valores scrum</p>");
	
	
	return "pagina"; 

}


}
