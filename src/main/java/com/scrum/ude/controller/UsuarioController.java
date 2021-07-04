package com.scrum.ude.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.scrum.ude.config.WebSecurityConfig;
import com.scrum.ude.dao.IUsuarioDAO;
import com.scrum.ude.entity.Usuario;
import com.scrum.ude.service.UsuarioServiceImpl;

@Controller
public class UsuarioController {
	
	@Autowired
	IUsuarioDAO usuarioDAO;
	@Autowired
	WebSecurityConfig seguridad;
	
	@Autowired
	UsuarioServiceImpl usuarioService;
	
	
	@GetMapping("/registroUsuario")
	public String redirigir(Model model) {
		Usuario usuario = new Usuario();

		model.addAttribute("usuario", usuario);

		return "registroUsuario";
	}
	
	
	@PostMapping("/registroUsuario")
	public String procesar(Usuario usuarioDTO, Model model, BindingResult result,@RequestParam String repeatPassword) {

		if (result.hasErrors()) {

			return "registroUsuario";
		}
		if(!usuarioDTO.getPassword().equals(repeatPassword)) {
		
			return "registroUsuario";
			
		}

		Usuario usuario = (Usuario) usuarioService.findOne(usuarioDTO.getUserName());

		if (usuario == null) {
			
			usuarioDTO.setRol("ROLE_USER");
			usuarioService.agregarUsuario(usuarioDTO);

			return "/index";
		} else {
			model.addAttribute("respuesta", "Este Usuario ya esta Registrado" + usuario.getUserName());

		}

		return "registroUsuario";	
		}
	
	
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/listadoUsuarios")
	public String verAlumnos(Model model) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth.getAuthorities().toString().equals("[ROLE_ADMIN]")) {
			
		
			List<Usuario> usuarios= (List<Usuario>) usuarioDAO.findAll();
			
			model.addAttribute("usuarios", usuarios);
			Usuario usuario= new Usuario();
			
			model.addAttribute("usuario", usuario);
			return "/listadoUsuarios";
			
		}
		model.addAttribute("autoridad",auth.getAuthorities().toString());
		
		return "/menu";
	}
	
	@GetMapping(value = "/eliminar/{id}")
	public String eliminarUsuario(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

		if (id > 0) {

			usuarioDAO.deleteById(id);
			flash.addFlashAttribute("success", "Usuario  eliminado con éxito!");
		}
		return "redirect:/listadoUsuarios";
	}
	

	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/listadoUsuarios")
	public String buscarAlumno(Model model,Usuario usuario,BindingResult result) {
		if(!usuario.getCedula().equals(null)) {

		Usuario usu= usuarioService.buscarPorCedula(usuario.getCedula());
				
		
		model.addAttribute("usuarios", usu);
		}else {
		
		List<Usuario> usuarios= (List<Usuario>) usuarioDAO.findAll();
		
		model.addAttribute("usuarios", usuarios);
		
		}
		
		return "/listadoUsuarios";
	}
	
	@GetMapping(value = "/modificar/{id}")
	public String modificarUsuario(@PathVariable(value = "id") Long id, RedirectAttributes flash, Model model) {
		Optional<Usuario> usuario = Optional.ofNullable(new Usuario());
		//ModelAndView mv= new ModelAndView();
		if (id > 0) {

			  usuario= usuarioDAO.findById(id);
			flash.addFlashAttribute("success", "Usuario  eliminado con éxito!");
			//mv.addObject("usuario",usuario);
			model.addAttribute("usuario", usuario);
			
		}
		return "/ModificarUsuarioDos";
	}
	
	@GetMapping("/verDatosPersonales")
	public String verDatosPersonales(Model model) {
		
		 Authentication auth = SecurityContextHolder
	             .getContext()
	             .getAuthentication();
	     UserDetails userDetail = (UserDetails) auth.getPrincipal();
		
		 Usuario user=usuarioDAO.findByUserName(userDetail.getUsername());
		 model.addAttribute("usuario",user);
		//Usuario user= 
		
		
		return"/modificarUser";
	}
	
	@PostMapping("/usuarioModificado")
	public String guardarUsuarioModificadoAdmin(Usuario usuario, Model model) {
	
		System.out.println("USUARIO ID  ES    "+usuario.getId());
	
		Usuario user=usuarioService.buscarPorId(usuario.getId());
		user.setNombre(usuario.getNombre());
		user.setApellido(usuario.getApellido());
		user.setCedula(usuario.getCedula());
		user.setMail(usuario.getMail());
		user.setHabilitado(usuario.isHabilitado());
		user.setRol(usuario.getRol());
		user.setUserName(usuario.getUserName());
		
		usuarioDAO.save(user);
		
		List<Usuario> usuarios= (List<Usuario>) usuarioDAO.findAll();
		
		model.addAttribute("usuarios", usuarios);
		Usuario usuariose= new Usuario();
		
		model.addAttribute("usuario", usuariose);
		
		
		return "/listadoUsuarios";
		
	}
	
	@PostMapping("/modificarDatosPersonales")
	public String modificarDatosPersonalesUsuario(Usuario usuario ,@RequestParam String contrasena,@RequestParam String nuevaContrasena, @RequestParam String confirmacionContrasena) {
	
		System.out.println("Pssword ID  ES    "+contrasena);
		Usuario user=usuarioService.buscarPorId(usuario.getId());
		if(seguridad.passwordEncoder().matches(contrasena, user.getPassword())){
			
			
			if(!confirmacionContrasena.equals(nuevaContrasena)) {
				
				return "modificarUser";
				
//				 ModelAndView modelAndView = new ModelAndView();
//				error(modelAndView);
			}
		
			user.setNombre(usuario.getNombre());
			user.setApellido(usuario.getApellido());
			user.setCedula(usuario.getCedula());
			user.setMail(usuario.getMail());
			user.setUserName(usuario.getUserName());
			String contra =seguridad.passwordEncoder().encode(nuevaContrasena);
			
			user.setPassword(contra);
			
			usuarioDAO.save(user);
			
		}

		return "/menu";
		
	}
	
	
	public String error(ModelAndView model) {
		
		 Authentication auth = SecurityContextHolder
	             .getContext()
	             .getAuthentication();
	     UserDetails userDetail = (UserDetails) auth.getPrincipal();
		
		 Usuario user=usuarioDAO.findByUserName(userDetail.getUsername());
		 model.addObject("usuario",user);
		 model.addObject("error", "Error No se logro guardar los cambios");
		//Usuario user= 
		
		
		return"/modificarUser";
	}
	

}
