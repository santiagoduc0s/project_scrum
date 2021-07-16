package com.scrum.ude.controller;

import java.util.List; 
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.scrum.ude.dao.ICodigoRegistro;
import com.scrum.ude.dao.IUsuarioDAO;
import com.scrum.ude.entity.CodigoRegistro;
import com.scrum.ude.entity.Usuario;
import com.scrum.ude.service.CodigoServiceImpl;
import com.scrum.ude.service.UsuarioServiceImpl;

@Controller
public class UsuarioController {
	
	@Autowired
	private IUsuarioDAO usuarioDAO;
	@Autowired
	private WebSecurityConfig seguridad;
	
	@Autowired
	private CodigoServiceImpl codigoImpl;
	
	@Autowired
	 private UsuarioServiceImpl usuarioService;
	
	// retorna el usuario Logeado para todo el contexto
	
	public  Authentication retornarUsuarioLogueado(){
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth;
	}
	
	//navego a la vista de registro usuario 
	
	@GetMapping("/registroUsuario")
	public String redirigir(Model model) {
		Usuario usuario = new Usuario();

		model.addAttribute("usuario", usuario);

		return "/registroUsuario/registroUsuario";
	}
	
	//aca proceso los datos  del registro usuario
	@PostMapping("/registroUsuario")
	public String procesar(Usuario usuarioDTO, Model model, BindingResult result,@RequestParam String repeatPassword, RedirectAttributes flash) {

		if (result.hasErrors()||!usuarioDTO.getPassword().equals(repeatPassword)) {
			
			flash.addFlashAttribute("danger","Se encontrado un error Choto");
			return "/registroUsuario/registroUsuario";
		}

		Usuario usuario = (Usuario) usuarioService.findOne(usuarioDTO.getUserName());

		if (usuario == null) {
			
			usuarioDTO.setRol("ROLE_USER");

			CodigoRegistro  codigoRegistro = codigoImpl.findCodigo(usuarioDTO.getCodigoRegistro().getCodigo());
			
			if(codigoRegistro!=null && !codigoRegistro.isUsado()) {
			
				codigoRegistro.setUsado(true);
				usuarioDTO.setCodigoRegistro(codigoRegistro);
					
			}
			else {
				
				return "/registroUsuario/registroUsuario";	
			}
			usuarioService.agregarUsuario(usuarioDTO);
			 
			flash.addFlashAttribute("success","Usuario Registrado con Exito");

			return "/login/index";
		} else {
			model.addAttribute("respuesta", "Este Usuario ya esta Registrado" + usuario.getUserName());

		}

		return "/registroUsuario/registroUsuario";	
		}
	
	//navego a la vista de ver usuarios que estan registrados
	
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/listadoUsuarios")
	public String verUsuarios(Model model) {
		
		Authentication auth = retornarUsuarioLogueado();
		if(auth.getAuthorities().toString().equals("[ROLE_ADMIN]")) {
			
		
			List<Usuario> usuarios= (List<Usuario>) usuarioDAO.findAll();
			
			model.addAttribute("usuarios", usuarios);
			Usuario usuario= new Usuario();
			
			model.addAttribute("usuario", usuario);
			return "/admin/listadoUsuarios";
			
		}
		model.addAttribute("autoridad",auth.getAuthorities().toString());
		
		return "/menu/menu";
	}
	
	// aca elimino un usuario
	
	@GetMapping(value = "/eliminar/{id}")
	public String eliminarUsuario(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

		if (id > 0) {

			usuarioDAO.deleteById(id);
			flash.addFlashAttribute("success", "Usuario  eliminado con éxito!");
		}
		return "redirect:/listadoUsuarios";
	}
	
	//busco por cedula de usuario 
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/listadoUsuarios")
	public String buscarUsuario(Model model,Usuario usuario,BindingResult result) {
		
		if(usuario.getCedula()!=null) {

		Usuario usu= usuarioService.buscarPorCedula(usuario.getCedula());
		
		model.addAttribute("usuarios", usu);
		
		} else {
		
		List<Usuario> usuarios= (List<Usuario>) usuarioDAO.findAll();
		
		model.addAttribute("usuarios", usuarios);
		
		 }
		
		return "/admin/listadoUsuarios";
	}
	//modifico los datos del usuario
	@GetMapping(value = "/modificar/{id}")
	public String modificarUsuario(@PathVariable(value = "id") Long id, RedirectAttributes flash, Model model) {
		Optional<Usuario> usuario = Optional.ofNullable(new Usuario());
		//ModelAndView mv= new ModelAndView();
		if (id > 0) {

			  usuario= usuarioDAO.findById(id);
			
			  flash.addFlashAttribute("success", "Usuario  eliminado con éxito!");
			  model.addAttribute("id", id);
			  model.addAttribute("usuario", usuario);
			
		}
		return "/admin/ModificarUsuariosPerfilAdmin";
	}
	// Perfil Usuario ver datos personales
	@GetMapping("/verDatosPersonales")
	public String verDatosPersonales(Model model) {
		
		 Authentication auth = retornarUsuarioLogueado();
	     
		 UserDetails userDetail = (UserDetails) auth.getPrincipal();
		
		 Usuario user=usuarioDAO.findByUserName(userDetail.getUsername());
		 model.addAttribute("id",user.getId());
		 model.addAttribute("usuario",user);
		//Usuario user= 
		 model.addAttribute("autoridad",auth.getAuthorities().toString());
		
		return"/user/modificarUser";
	}
	
	//Se procesan los cambios del Usuario, Perfil Administrador  
	@PostMapping("/usuarioModificado")
	public String guardarUsuarioModificadoAdmin(Usuario usuario,@RequestParam(value = "id") Long id, Model model) {
	
		System.out.println("USUARIO ID  ES    "+usuario.getId());
	
		Usuario user=usuarioService.buscarPorId(id);
		user.setNombre(usuario.getNombre());
		user.setApellido(usuario.getApellido());
		user.setCedula(usuario.getCedula());
		user.setMail(usuario.getMail());
		user.setRol(usuario.getRol());
		user.setUserName(usuario.getUserName());
		
		usuarioDAO.save(user);
		
		List<Usuario> usuarios= (List<Usuario>) usuarioDAO.findAll();
		
		model.addAttribute("usuarios", usuarios);
		Usuario usuariose= new Usuario();
		
		model.addAttribute("usuario", usuariose);
		
		
		return "/admin/listadoUsuarios";
		
	}
	// Se procesan datos de la modificacion del  propio Usuario
	
	@PostMapping("/modificarDatosPersonales")
	public String modificarDatosPersonalesUsuario(Usuario usuario ,@RequestParam(value = "id") Long id,@RequestParam(value="contrasena") String contrasena,@RequestParam(value="nuevaContrasena") String nuevaContrasena, @RequestParam (value="confirmacionContrasena")String confirmacionContrasena) {
	
		Usuario user=usuarioService.buscarPorId(id);
		if(seguridad.passwordEncoder().matches(contrasena, user.getPassword())){
			
				if(!confirmacionContrasena.equals(nuevaContrasena)) {
				
					return "/user/modificarUser";
				
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

		return "redirect:/menu";
		
	}
	
	// Aun no decido errores para la pantalla
	public String error(ModelAndView model) {
		
		Authentication auth = retornarUsuarioLogueado();
	     UserDetails userDetail = (UserDetails) auth.getPrincipal();
		
		 Usuario user=usuarioDAO.findByUserName(userDetail.getUsername());
		 model.addObject("usuario",user);
		 model.addObject("error", "Error No se logro guardar los cambios");
		//Usuario user= 
		
		
		return"/user/modificarUser";
	}
	
}
