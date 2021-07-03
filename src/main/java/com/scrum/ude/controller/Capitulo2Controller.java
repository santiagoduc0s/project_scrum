package com.scrum.ude.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class Capitulo2Controller {

	@GetMapping("/inicio-scrum")

	public String pagina1(Model model) {

		model.addAttribute("contenido", "<h1>inicio Scrum</h1>");
		
		return "pagina"; 

	}
	
	
}
