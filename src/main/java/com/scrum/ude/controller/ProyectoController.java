package com.scrum.ude.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.scrum.ude.dao.IProyectoDAO;
import com.scrum.ude.dao.ITareaDAO;
import com.scrum.ude.dao.IUsuarioDAO;
import com.scrum.ude.entity.Proyecto;
import com.scrum.ude.entity.Usuario;
import com.scrum.ude.service.ProyectoServiceImpl;
import com.scrum.ude.service.UsuarioServiceImpl;

@Controller
public class ProyectoController {

    @Autowired
    private IUsuarioDAO usuarioDAO;

    @Autowired
    private ProyectoServiceImpl proyectoImpl;
    @Autowired
    private UsuarioServiceImpl usuarioImpl;

    @Autowired
    private UsuarioController usuarioController;

    @Autowired
    private ITareaDAO tareaDAO;

    @Autowired
    private IProyectoDAO proyectoDAO;

    // navegar a vista de Proyectos y busco los proyectos para mostrar del usuario
    @GetMapping("/vistaProyecto")
    public String vistaProyecto(Model model) {
        Proyecto proyecto = new Proyecto();
//		
        Authentication auth = usuarioController.retornarUsuarioLogueado();
//		
        model.addAttribute("proyecto", proyecto);
//
        auth.getName();
        Usuario user = usuarioImpl.findOne(auth.getName());
//
        List<Proyecto> proyectos = (List<Proyecto>) proyectoImpl.buscarProyectoPorUsuario(user.getId());
//
        model.addAttribute("proyectos", proyectos);
        model.addAttribute("autoridad", auth.getAuthorities().toString());
//		for(Proyecto pro:proyectos) {
//          if(pro.isCreador()) {
//        	  
//        	  model.addAttribute("creador", pro.isCreador());	  
//          }
//		}


        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        Usuario us = usuarioDAO.findByUserName(userDetail.getUsername());
        model.addAttribute("usuario", us);
//
        return "/proyecto/crearProyecto";
    }

    // proceso la creacion del proyecto
    @PostMapping("/crearProyecto")
    public String crearProyecto(Model model, Proyecto proyecto, RedirectAttributes flash) {

        Authentication auth = usuarioController.retornarUsuarioLogueado();
//		
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
//
        Usuario user = usuarioDAO.findByUserName(userDetail.getUsername());
        List<Usuario> usuarios = new ArrayList();

        usuarios.add(user);
        proyecto.setCreador(true);
        proyecto.setUsuario(usuarios);

        Proyecto proyect = (Proyecto) proyectoImpl.buscarProyectoPorUsuarioWithTitulo(user.getId(), proyecto.getTitulo());

//			
        if (proyect == null) {
//				
            String mensajeFlash = "Proyecto creado correctamente";
//				 
            proyectoDAO.save(proyecto);
//				 
            flash.addFlashAttribute("success", mensajeFlash);
        } else {
            return "redirect:/vistaProyecto";
//			    	
        }
        List<Proyecto> proyectos = (List<Proyecto>) proyectoImpl.buscarProyectoPorUsuario(user.getId());
        model.addAttribute("proyectos", proyectos);

        return "redirect:/vistaProyecto";
    }

    // vista de cada Proyecto son sus respectivas tareas
    @GetMapping("/verProyectoTarea/{id}")
    public String verProyectoWithTarea(@PathVariable(value = "id") Long id, Model model) {

        Authentication auth = usuarioController.retornarUsuarioLogueado();

        Proyecto proyecto = proyectoImpl.buscarPorIdProyecto(id);

        model.addAttribute("proyecto", proyecto);

        model.addAttribute("autoridad", auth.getAuthorities().toString());

        //buscar tareas con el proyecto asociado
        //Tarea tarea=tareaDA
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        Usuario user = usuarioDAO.findByUserName(userDetail.getUsername());
        model.addAttribute("usuario", user);


        return "/proyecto/proyectoConTarea";

    }

    // ver el proyecto para ser modificado
    @GetMapping("/verProyecto/{id}")
    public String verProyectoParaModificar(@PathVariable(value = "id") Long id, Model model) {

        Authentication auth = usuarioController.retornarUsuarioLogueado();


        UserDetails userDetail = (UserDetails) auth.getPrincipal();

        Usuario user = usuarioDAO.findByUserName(userDetail.getUsername());

        Proyecto proyecto = proyectoImpl.buscarPorIdProyecto(id);

        model.addAttribute("proyecto", proyecto);
        model.addAttribute("id", id);
        model.addAttribute("usuario", user);

        model.addAttribute("autoridad", auth.getAuthorities().toString());

        //buscar tareas con el proyecto asociado
        //Tarea tarea=tareaDAO.

        return "/proyecto/modificarProyecto";

    }

    //		// ver el proyecto para ser modificado //Fijate
    @PostMapping("/guardarModificacionProyecto")
    public String verProyectoParaModificar(Proyecto proyecto, @RequestParam(value = "id") Long id, Model model) {
//					
        Authentication auth = usuarioController.retornarUsuarioLogueado();

        auth.getName();
        Usuario user = usuarioImpl.findOne(auth.getName());
        //Proyecto proyecto = proyectoImpl.buscarPorIdProyecto(id);

        //model.addAttribute("proyecto", proyecto);

        List<Usuario> usuarios = new ArrayList();
        usuarios.add(user);

        if (!proyecto.isCreador()) {

            proyecto.setCreador(true);
        }
        proyecto.setUsuario(usuarios);
        proyecto.setId(id);
        proyectoDAO.save(proyecto);

        model.addAttribute("autoridad", auth.getAuthorities().toString());


        List<Proyecto> proyectos = (List<Proyecto>) proyectoImpl.buscarProyectoPorUsuario(user.getId());

        model.addAttribute("proyectos", proyectos);
        //buscar tareas con el proyecto asociado
        //Tarea tarea=tareaDAO.

        return "redirect:/vistaProyecto";
//
    }

    //
//	
    // aca elimino un  proyecto
    @GetMapping(value = "/eliminarProyecto/{id}")
    public String eliminarProyecto(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

        if (id > 0) {
            proyectoDAO.deleteById(id);
            flash.addFlashAttribute("success", "Proyecto eliminado correctamente");
        }
        return "redirect:/vistaProyecto";
    }

}
