package com.scrum.ude.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

        model.addAttribute("autoridad", auth.getAuthorities().toString());

        Pagina pagina = null;
        pagina = paginaImpl.obtenerContenido(id);
        Usuario user = null;
        if (pagina != null) {

            user = usuarioService.findOne(auth.getName());

            String dis = pagina.getDiscriminante();
            
            if (Objects.equals(dis, "PRACTICA")) {
                return "cuestionarios/index";
            }
            
            List<Pagina> paginas = new ArrayList<>();
            paginas.addAll(user.getPaginas());

            
            if (!paginas.contains(pagina)) {

                paginas.add(pagina);

            }

            System.out.println(user);

            user.setPaginas(paginas);

            usuarioDAO.save(user);

        }

        model.addAttribute("pagina", pagina);

        model.addAttribute("usuario", user);

       

        return "/curso/pagina";
    }

    @GetMapping("/PaginasByUser")
    public ResponseEntity<?> cantidadPorUsuarioPaginas() {
        Authentication auth = usuarioController.retornarUsuarioLogueado();

        Usuario user = usuarioService.findOne(auth.getName());

        List<Pagina> paginasVistas = new ArrayList<>();

        paginasVistas.addAll(user.getPaginas());

        // --------------

        List<Pagina> paginasTotales = paginaImpl.obtenerPaginas();

        if(paginasTotales.contains("PRACTICA")){
        	
        	paginasTotales.removeIf(obj -> obj.discriminante.equals("PRACTICA"));
        	
        }

        // ----------------

        String json = "{\"pagesViewed\": " + paginasVistas.size() + ", \"totalPages\": " + paginasTotales.size() + "}";

        return ResponseEntity.status(HttpStatus.OK).body(json);
    }
}

