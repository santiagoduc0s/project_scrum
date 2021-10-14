package com.scrum.ude.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.scrum.ude.dao.IUsuarioDAO;
import com.scrum.ude.entity.Curso;
import com.scrum.ude.entity.Usuario;
import com.scrum.ude.service.CursoServiceImpl;
import com.scrum.ude.service.UsuarioServiceImpl;

@Controller
public class CursoController {
    //
    @Autowired
    private IUsuarioDAO usuarioDAO;

    @Autowired
    private UsuarioServiceImpl usuarioImpl;

    @Autowired
    private UsuarioController usuarioController;

    @Autowired
    private CursoServiceImpl cursoImpl;


    // navegar a vista Curso y busco por el usuario si se ha inscripto al curso
    @GetMapping("/verCurso")
    public String cursoScrum(Model model) {
//		
        Authentication auth = usuarioController.retornarUsuarioLogueado();

        UserDetails userDetail = (UserDetails) auth.getPrincipal();

        model.addAttribute("autoridad", auth.getAuthorities().toString());

        Usuario us = usuarioDAO.findByUserName(userDetail.getUsername());
        Long id = (long) 1;
        Curso curso = cursoImpl.codigoCurso(id);

        model.addAttribute("curso", curso);

        model.addAttribute("usuario", us);

        return "/curso/scrum";
    }
}
