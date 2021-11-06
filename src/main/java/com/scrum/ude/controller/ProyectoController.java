package com.scrum.ude.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.scrum.ude.entity.Tarea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
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

    @Autowired
    private UsuarioServiceImpl usuarioService;


    @GetMapping("/vistaProyecto")
    public String vistaProyecto(Model model) {
        Proyecto proyecto = new Proyecto();

        Authentication auth = usuarioController.retornarUsuarioLogueado();

        model.addAttribute("proyecto", proyecto);

        Usuario user = usuarioImpl.findOne(auth.getName());

        List<Proyecto> proyectos = (List<Proyecto>) proyectoImpl.buscarProyectoPorUsuario(user.getId());

        model.addAttribute("proyectos", proyectos);
        model.addAttribute("usuario", user.getUserName());
        model.addAttribute("autoridad", auth.getAuthorities().toString());

        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        Usuario us = usuarioDAO.findByUserName(userDetail.getUsername());
        model.addAttribute("usuario", us);

        return "/proyecto/crearProyecto";
    }


    //METODO DE TODOS LOS PROYECTOS CON SUS PARTICIPANTES 

    //Listado de Proyectos con participantes
    @GetMapping("/vistaProyectosWithParticipantes")
    public String verProyectosWithParticipantes(Model model) {
        Proyecto proyecto = new Proyecto();

        Authentication auth = usuarioController.retornarUsuarioLogueado();

        model.addAttribute("proyecto", proyecto);

        Usuario user = usuarioImpl.findOne(auth.getName());

        List<Proyecto> proyectos = (List<Proyecto>) proyectoImpl.buscarTodosLosProyectos();

        model.addAttribute("proyectos", proyectos);
        model.addAttribute("usuario", user.getUserName());
        model.addAttribute("autoridad", auth.getAuthorities().toString());

        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        Usuario us = usuarioDAO.findByUserName(userDetail.getUsername());
        model.addAttribute("usuario", us);

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

        List<Proyecto> proyectosos = new ArrayList();


        usuarios.add(user);
        proyecto.setCreador(user.getUserName());
        proyecto.setUsuario(usuarios);
        int numero = (int) (Math.random() * 100 + 1);
        HashCode sha256hex = Hashing.sipHash24().hashInt(numero);

        proyecto.setCodigoProyecto("" + sha256hex);

        Proyecto proyect = (Proyecto) proyectoImpl.buscarProyectoPorUsuarioWithTitulo(user.getId(), proyecto.getTitulo());

        List<Proyecto> ver = (List<Proyecto>) proyectoImpl.buscarProyectoPorUsuario(user.getId());
//			
        if (proyect == null) {
//				
            String mensajeFlash = "Proyecto creado con éxito!";

            if (ver != null) {

                proyectosos.addAll(ver);
            }
            proyectosos.add(proyecto);
            user.setProyecto(proyectosos);
            usuarioDAO.save(user);
//				 
            //proyectoDAO.save(proyecto);
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


    @PostMapping("/unirseProyecto")
    public String unirseProyecto(Model model, Proyecto proyecto, RedirectAttributes flash) {

        Authentication auth = usuarioController.retornarUsuarioLogueado();
//		
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
//
        Usuario user = usuarioDAO.findByUserName(userDetail.getUsername());
        List<Usuario> usuarios = usuarioImpl.buscarProyectosoVinculadosPorUsuario(user.getId());


        //proyecto.setCreador(true);
        //proyecto.setUsuario(usuarios);

        String t = proyecto.getCodigoProyecto();

        Proyecto proyect = (Proyecto) proyectoImpl.buscarPorCodigoProyecto(proyecto.getCodigoProyecto());


//			
        if (proyect == null) {
//				
            String mensajeFlash = "No existe proyecto";
            flash.addFlashAttribute("warning", mensajeFlash);
        } else {

            if (proyect.getUsuario().contains(user)) {

                String mensajeFlash = "Usted ya se encuentra inscripto en este proyecto";
                flash.addFlashAttribute("warning", mensajeFlash);
            } else {

                usuarios.addAll(proyect.getUsuario());
                usuarios.add(user);

                proyect.setUsuario(usuarios);

                List<Proyecto> proyectos = proyectoImpl.buscarProyectoPorUsuario(user.getId());
                proyectos.add(proyect);
                user.setProyecto(proyectos);
                usuarioDAO.save(user);

                proyectoDAO.save(proyect);
            }
        }
        List<Proyecto> proyectos = (List<Proyecto>) proyectoImpl.buscarProyectoPorUsuario(user.getId());
        model.addAttribute("proyectos", proyectos);
        return "redirect:/vistaProyecto";
    }

    @GetMapping("/salirProyecto/{id}")
    public String salirProyecto(Model model, @PathVariable(value = "id") Long id, RedirectAttributes flash) {

        Authentication auth = usuarioController.retornarUsuarioLogueado();
//
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
//
        Usuario user = usuarioDAO.findByUserName(userDetail.getUsername());
        List<Usuario> usuarios = usuarioImpl.buscarProyectosoVinculadosPorUsuario(user.getId());

        List<Proyecto> proyectos = proyectoImpl.buscarProyectoPorUsuario(user.getId());

        Proyecto proyect = (Proyecto) proyectoImpl.buscarPorIdProyecto(id);
        String mensajeFlash = "No existe proyecto";
//
        flash.addFlashAttribute("warning", mensajeFlash);

        if (proyectos.contains(proyect)) {

            // usuarios.remove(user);

            // proyect.setUsuario(usuarios);


            //proyectos.add(proyect);
            proyectos.remove(proyect);
            user.setProyecto(proyectos);
            usuarioDAO.save(user);

            System.out.println("Entro en el metodo");
        }

        List<Proyecto> proyectoso = (List<Proyecto>) proyectoImpl.buscarProyectoPorUsuario(user.getId());
        model.addAttribute("proyectos", proyectoso);
        return "redirect:/vistaProyecto";
    }


    // vista de cada Proyecto son sus respectivas tareas
    @GetMapping("/verProyectoTarea/{id}")
    public String verProyectoWithTarea(@PathVariable(value = "id") Long id, Authentication auth, Model model) {

        Usuario user = usuarioService.findOne(((UserDetails) auth.getPrincipal()).getUsername());

        boolean participaDelProyecto = false;
        if (Objects.equals(user.getRol(), "ROLE_USER")) {
            for (Proyecto proyecto : user.getProyecto()) {
                if (Objects.equals(proyecto.getId(), id)) {
                    participaDelProyecto = true;
                }
            }

            if (!participaDelProyecto) {
                return "redirect:/";
            }
        }

        Proyecto proyecto = proyectoImpl.buscarPorIdProyecto(id);

        model.addAttribute("proyecto", proyecto);
        model.addAttribute("tareas", proyecto.getTarea());
        model.addAttribute("autoridad", auth.getAuthorities().toString());

        model.addAttribute("usuario", user);

        Tarea tarea = new Tarea();
        model.addAttribute("ide", id);
        model.addAttribute("tarea", tarea);

        return "/proyecto/proyectoConTarea";
    }


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

        return "/proyecto/modificarProyecto";

    }

    @PostMapping("/guardarModificacionProyecto")
   public String verProyectoParaModificar(Proyecto proyecto, @RequestParam(value = "id") Long id, Model model) {
////					
        Authentication auth = usuarioController.retornarUsuarioLogueado();
//
        auth.getName();
        Usuario user = usuarioImpl.findOne(auth.getName());
        Proyecto proyectoso = proyectoImpl.buscarPorIdProyecto(id);
//
        //model.addAttribute("proyecto", proyecto);

        List<Usuario> usuarios= usuarioImpl.buscarProyectosoVinculadosPorUsuario(proyecto.getId());

        List<Proyecto> proyectos;

        //if (!proyectoso.getCreador().equals(user.getUserName())) {

          //  return "redirect:/vistaProyecto";
       //}
        if((proyecto.getDescripcion()!=proyectoso.getDescripcion()) && proyecto.getTitulo()!=proyectoso.getTitulo()) {

        //usuarios.add(user);

        proyectoso.setTitulo(proyecto.getTitulo());
        proyectoso.setDescripcion(proyecto.getDescripcion());
        proyectoso.setUsuario(usuarios);
        proyectoso.setUsuario(usuarios);

        proyectoDAO.save(proyectoso);

        }

        model.addAttribute("autoridad", auth.getAuthorities().toString());

        List<Proyecto> proyectosos = (List<Proyecto>) proyectoImpl.buscarProyectoPorUsuario(user.getId());

        model.addAttribute("proyectos", proyectosos);


        return "redirect:/vistaProyecto";
////
   }
//    


    //ACA DUCOS PARA QUITAR PARTICIPANTES METODO QUE PROCESA
//    @PostMapping("/guardarModificacionProyecto")
//    public String verProyectoParaModificar(Proyecto proyecto, @RequestParam(value = "id") Long id, @RequestParam(value = "idParticipante") Long idParticipante, Model model) {
////
//        Authentication auth = usuarioController.retornarUsuarioLogueado();
//
//        auth.getName();
//        Usuario user = usuarioImpl.findOne(auth.getName());
//        Proyecto proyectoso = proyectoImpl.buscarPorIdProyecto(id);
//
//        //model.addAttribute("proyecto", proyecto);
//
//        List<Usuario> usuarios = usuarioImpl.buscarProyectosoVinculadosPorUsuario(proyecto.getId());
//
//        //List<Proyecto> proyectos;
//
//        //   if (!proyectoso.getCreador().equals(user.getUserName())) {
//
//        //     return "redirect:/vistaProyecto";
//        //}
//        if ((proyecto.getDescripcion() != proyectoso.getDescripcion()) && proyecto.getTitulo() != proyectoso.getTitulo()) {
//
//            Usuario usuarioParticipante = usuarioImpl.buscarPorId(idParticipante);
//
//            usuarios.add(user);
//
//            usuarios.remove(usuarioParticipante);
//
//            proyectoso.setTitulo(proyecto.getTitulo());
//            proyectoso.setDescripcion(proyecto.getDescripcion());
//            proyectoso.setUsuario(usuarios);
//            proyectoso.setUsuario(usuarios);
//
//            proyectoDAO.save(proyectoso);
//
//        }
//
//        model.addAttribute("autoridad", auth.getAuthorities().toString());
//
//        List<Proyecto> proyectosos = (List<Proyecto>) proyectoImpl.buscarProyectoPorUsuario(user.getId());
//
//        model.addAttribute("proyectos", proyectosos);
//
//
//        return "redirect:/vistaProyecto";
////
//    }







    // aca elimino un  proyecto
    @GetMapping(value = "/eliminarProyecto/{id}")
    public String eliminarProyecto(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

        if (id > 0) {

            Authentication auth = usuarioController.retornarUsuarioLogueado();

            auth.getName();
            Usuario user = usuarioImpl.findOne(auth.getName());
            Proyecto proyecto = proyectoImpl.buscarPorIdProyecto(id);

            if (user.getProyecto().contains(proyecto)) {

                List<Proyecto> proyectos = user.getProyecto();


                proyectos.remove(proyecto);

                user.setProyecto(proyectos);



                usuarioDAO.save(user);

                proyectoDAO.delete(proyecto);


            }




            flash.addFlashAttribute("success", "Proyecto  eliminado con éxito!");
        }
        return "redirect:/vistaProyecto";
    }

    // aca elimino al participante de la lista  del   proyecto
    @GetMapping(value = "/eliminarPartipante")
    public String eliminarPartipante(@ModelAttribute Long idUsuario, @ModelAttribute Long idProyecto, RedirectAttributes flash) {

        Proyecto proyecto = proyectoImpl.buscarPorIdProyecto(idProyecto);
        List<Usuario> usuarios = new ArrayList<>();

        Usuario user = usuarioImpl.buscarPorId(idUsuario);
        usuarios.remove(user);
        proyecto.setUsuario(usuarios);

        if (!proyecto.getCreador().equals(user.getUserName())) {

            proyectoDAO.save(proyecto);
        } else {
            flash.addFlashAttribute("warning", "No se puede eliminar a si mismo!");

        }

        List<Proyecto> proyectos = new ArrayList<>();

        proyectos.remove(proyecto);
        user.setProyecto(proyectos);
        usuarioDAO.save(user);

        flash.addFlashAttribute("success", "Participante Eliminado!");

        return "";
    }

    @GetMapping("/proyectos")
    public String showAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "") String text,
            Authentication auth,
            Model model
    ) {

        Usuario user = usuarioService.findOne(((UserDetails) auth.getPrincipal()).getUsername());

        if (Objects.equals(user.getRol(), "ROLE_ADMIN")) {
            Pageable pageable = PageRequest.of(page, 10);
            Page<Proyecto> proyectos;
            if (Objects.equals(text, "")) {
                proyectos = proyectoImpl.getAll(pageable);
            } else {
                proyectos = proyectoImpl.getProyectosLikeTitulo("%" + text + "%", pageable);
            }

            int totalPage = proyectos.getTotalPages();
            if (totalPage > 0) {
                List<Integer> pages = IntStream.rangeClosed(1, totalPage).boxed().collect(Collectors.toList());
                model.addAttribute("pages", pages);
            }

            model.addAttribute("text", text);
            model.addAttribute("proyectos", proyectos.getContent());
            model.addAttribute("current", page + 1);
            model.addAttribute("usuario", user);
            model.addAttribute("autoridad", auth.getAuthorities().toString());

            return "proyecto/proyectos";
        }
        return "redirect:/menu";
    }

    @GetMapping("/api/proyectos/participantes/{id}")
    public ResponseEntity<?> getParticipantes(@PathVariable Long id) {
        Proyecto proyecto = proyectoImpl.buscarPorIdProyecto(id);
        return ResponseEntity.status(HttpStatus.OK).body(usuarioImpl.transformUsuarioDao(proyecto.getUsuario()));
    }

}


