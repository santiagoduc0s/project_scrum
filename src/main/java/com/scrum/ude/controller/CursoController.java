package com.scrum.ude.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.scrum.ude.dao.IInscriptoDAO;
import com.scrum.ude.dao.IUsuarioDAO;
import com.scrum.ude.entity.Curso;
import com.scrum.ude.entity.Inscripto;
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

//	// navegar a vista Curso y busco por el usuario si se ha inscripto al curso
	@GetMapping("/verCurso")
	public String vistaCursos(Model model) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();

		Usuario user = usuarioDAO.findByUserName(userDetail.getUsername());
		
		Inscripto inscripto= usuarioImpl.buscarUsuarioIncripto(user.getId());
		model.addAttribute("inscripto",inscripto);

		return "/curso/cursos";
	}
	
	//Luego de inscribirse cuando nanega hacia la lista  de cursos toca el boton continuar y sigue donde se quedo
	@GetMapping("/continuacionCurso")
	public String vistaContinuacionCurso(Model model) {
		

		return "/curso/iniciarCurso";
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
			
		inscriptoDAO.save(inscripto);
		}

    	 return "redirect:/iniciarCurso";
	}

	// vista del curso Scrum todo el contenido
	@GetMapping("/iniciarCurso")
	public String verProyectoWithTarea( Model model) {

		return "/curso/iniciarCurso";

	}


}
