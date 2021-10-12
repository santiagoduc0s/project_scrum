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

import com.scrum.ude.dao.IUsuarioDAO;
import com.scrum.ude.entity.Pagina;
import com.scrum.ude.entity.Usuario;
import com.scrum.ude.service.PaginaServiceImpl;
import com.scrum.ude.service.UsuarioServiceImpl;

@Controller
public class PaginaController {
	@Autowired
	private IUsuarioDAO usuarioDAO;

    @Autowired
    private UsuarioController usuarioController;

    @Autowired
    public PaginaServiceImpl paginaImpl;
    
    @Autowired
    private UsuarioServiceImpl usuarioService;


    @GetMapping("/verPag/{id}")

    public String verPagina(Model model, @PathVariable(value = "id") Long id) {

        Authentication auth = usuarioController.retornarUsuarioLogueado();

        UserDetails userDetail = (UserDetails) auth.getPrincipal();

        model.addAttribute("autoridad", auth.getAuthorities().toString());

        Pagina pagina = null;
        pagina = paginaImpl.obtenerContenido(id);
        if(pagina!=null) {
        	
        	 //usuarioDAO.sa
        Usuario user=usuarioService.findOne(auth.getName());
        
        List<Pagina> paginas= new ArrayList<>();
        paginas.addAll(user.getPaginas());
        if(!paginas.contains(pagina)) {
        	
            paginas.add(pagina);

        }
        	
        System.out.println(user);
        	
        user.setPaginas(paginas);

        usuarioDAO.save(user);
        	
        }

        model.addAttribute("pagina", pagina);


        return "/curso/pagina";

    }


}

