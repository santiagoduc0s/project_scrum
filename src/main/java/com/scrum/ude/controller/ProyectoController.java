package com.scrum.ude.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.scrum.ude.dao.IProyectoDAO;
import com.scrum.ude.dao.IUsuarioDAO;
import com.scrum.ude.entity.Proyecto;
import com.scrum.ude.entity.Usuario;
import com.scrum.ude.service.UsuarioServiceImpl;

@Controller
public class ProyectoController {

	
	@Autowired
	private IUsuarioDAO usuarioDAO;
	@Autowired
	private UsuarioServiceImpl usuarioImpl;
	
	
	@Autowired
	private IProyectoDAO proyectoDAO;
	
@GetMapping("/vistaProyecto")
public String  vistaProyecto(Model model) {
	Proyecto proyecto= new Proyecto();
	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	
	model.addAttribute("proyecto",proyecto);
	 List<Proyecto> proyectos= (List<Proyecto>) proyectoDAO.findAll();
		
		model.addAttribute("proyectos", proyectos);
		model.addAttribute("autoridad",auth.getAuthorities().toString());
	
	return"/crearProyecto";
}


@PostMapping("/crearProyecto")
public String generarCodigo(Model model,Proyecto proyecto) {
	
	 Authentication auth = SecurityContextHolder
             .getContext()
             .getAuthentication();
     UserDetails userDetail = (UserDetails) auth.getPrincipal();
	
	 Usuario user=usuarioDAO.findByUserName(userDetail.getUsername());
	 proyecto.setUsuario(user);
	 
	 proyectoDAO.save(proyecto);
	 
	 List<Proyecto> proyectos= (List<Proyecto>) usuarioImpl.buscarProyectoPorUsuario(proyecto.getId());
		
		model.addAttribute("proyectos", proyectos);
	
		return"/crearProyecto";
}

@GetMapping("/verProyectoTarea/{id}")
public String verProyectoWithTarea(@PathVariable(value = "id") Long id,Model model) {
	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	Proyecto proyecto= usuarioImpl.buscarPorIdProyecto(id);
	
	model.addAttribute("proyecto",proyecto);
	model.addAttribute("autoridad",auth.getAuthorities().toString());
	
	return"/proyectoConTarea";
	
}

}
