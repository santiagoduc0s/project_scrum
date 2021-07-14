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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.scrum.ude.dao.ICursoDAO;
import com.scrum.ude.dao.IInscriptoDAO;
import com.scrum.ude.dao.IProyectoDAO;
import com.scrum.ude.dao.ITareaDAO;
import com.scrum.ude.dao.IUsuarioDAO;
import com.scrum.ude.entity.Curso;
import com.scrum.ude.entity.Inscripto;
import com.scrum.ude.entity.Proyecto;
import com.scrum.ude.entity.Usuario;
import com.scrum.ude.service.UsuarioServiceImpl;
//
@Controller
public class CursoController {
//
	@Autowired
	private IUsuarioDAO usuarioDAO;
	
	@Autowired
	private IInscriptoDAO inscriptoDAO;
	
	@Autowired
	private UsuarioServiceImpl usuarioImpl; 
//	@Autowired
//	private UsuarioController usuarioController;
//	
//	@Autowired
//	private ITareaDAO tareaDAO;
//
//	@Autowired
//	private IProyectoDAO proyectoDAO;
//
//	// navegar a vista de Proyectos y busco los proyectos para mostrar del usuario
	@GetMapping("/verCurso")
	public String vistaProyecto(Model model) {
	
//
		return "/curso/cursos";
	}
	// proceso de validacion de curso e iniciar
	@PostMapping("/validarCurso")
	public String crearProyecto(Model model,@RequestParam(value="codigo")int codigoCurso, RedirectAttributes flash) {
//
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();

		Usuario user = usuarioDAO.findByUserName(userDetail.getUsername());
		
		Curso curso= usuarioImpl.codigoCurso(codigoCurso);
    	
	Inscripto inscripto= new Inscripto();
	if(curso!=null) {
		
		inscripto.setCurso(curso);
		inscripto.setUsuario(user);
			
		inscriptoDAO.save(inscripto)	;
		}

    	 return "redirect:/iniciarCurso";
	}
//
	// vista de cada Proyecto son sus respectivas tareas
	@GetMapping("/iniciarCurso")
	public String verProyectoWithTarea( Model model) {

		return "/curso/iniciarCurso";
//
	}
//	
//	// ver el proyecto para ser modificado
//		@GetMapping("/verProyecto/{id}")
//		public String verProyectoParaModificar(@PathVariable(value = "id") Long id, Model model) {
//			
//			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//			
//			Proyecto proyecto = usuarioImpl.buscarPorIdProyecto(id);
//
//			model.addAttribute("proyecto", proyecto);
//			
//			model.addAttribute("autoridad", auth.getAuthorities().toString());
//			
//		   //buscar tareas con el proyecto asociado
//			//Tarea tarea=tareaDAO.
//
//			return "/proyecto/modificarProyecto";
//
//		}
//	
//	
//	// aca elimino un  proyecto
//		@GetMapping(value = "/eliminarProyecto/{id}")
//		public String eliminarProyecto(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
//
//			if (id > 0) {
//				proyectoDAO.deleteById(id);
//				flash.addFlashAttribute("success", "Proyecto  eliminado con éxito!");
//			}
//			return "redirect:/vistaProyecto";
//		}
//
	
	
	@GetMapping(value="/verCuestionario")
public String verCuestionario() {
		
		
		return "/curso/verCuestionario";
	}
}
//
