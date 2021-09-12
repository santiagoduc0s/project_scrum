package com.scrum.ude.controller;

import com.scrum.ude.dao.IUsuarioDAO;
import com.scrum.ude.entity.Proyecto;
import com.scrum.ude.entity.Usuario;
import com.scrum.ude.helpers.Auth;
import com.scrum.ude.service.ProyectoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value = "/api/proyectos")
public class ApiProyectoController {

    @Autowired
    private ProyectoServiceImpl proyectoService;

    @Autowired
    private UsuarioController usuarioController;

    @Autowired
    private IUsuarioDAO usuarioDAO;

    @GetMapping(value = "/quick-view/{id}")
    public ModelAndView quickView(@PathVariable(value = "id") Long id) {
        ModelAndView mav = new ModelAndView("proyecto/quick-view");
        Proyecto proyecto = proyectoService.buscarPorIdProyecto(id);

        Authentication auth = usuarioController.retornarUsuarioLogueado();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        Usuario usuario = usuarioDAO.findByUserName(userDetail.getUsername());

        mav.addObject("proyecto", proyecto);
        mav.addObject("usuario", usuario);
        mav.addObject("id", id);

        return mav;
    }
}
