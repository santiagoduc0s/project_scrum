package com.scrum.ude.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.scrum.ude.dao.ITareaDAO;
import com.scrum.ude.dao.IUsuarioDAO;
import com.scrum.ude.entity.Proyecto;
import com.scrum.ude.entity.Tarea;
import com.scrum.ude.service.UsuarioServiceImpl;

@Controller
public class TareaController {
	
	@Autowired
	private UsuarioController usuarioController;

	@Autowired
	private IUsuarioDAO usuarioDAO;
	@Autowired
	private UsuarioServiceImpl usuarioServiceImp;
	
	@Autowired
	private ITareaDAO tareaDAO;

	// navego hacia el formulario de la creacion de la tarea con el id del proyecto
	
	@GetMapping("/crearTarea/{id}")
	public String verTarea(Model model,@PathVariable(value = "id") Long id ) {

		Tarea tarea= new Tarea();
		model.addAttribute("ide",id);
		model.addAttribute("tarea",tarea);

		return"/proyecto/crearTarea";
	}
	@PostMapping("/crearTarea")
	public String crearTarea(Model model,@RequestParam(value="pass",defaultValue="0")Long idProyecto, Tarea tarea) {

		Proyecto proyecto=usuarioServiceImp.buscarPorIdProyecto(idProyecto);
		tarea.setProyecto(proyecto);
		Tarea tareaBase=usuarioServiceImp.buscarPorNombreTarea(tarea.getNombre());
		
		if(tareaBase==null) {
			tareaDAO.save(tarea);
		} else {
			// ya existe dicha tarea 
			
		 }
		
    	 return "redirect:/verProyectoTarea/"+idProyecto+"";
	}
	
	
}
