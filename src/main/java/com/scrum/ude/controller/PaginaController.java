package com.scrum.ude.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

import com.scrum.ude.dao.IPaginaDAO;
import com.scrum.ude.entity.Opcion;
import com.scrum.ude.entity.Pregunta;
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

    @Autowired
    private IPaginaDAO iPaginaDAO;


    @GetMapping("/verPag/{id}")
    public String verPagina(Model model, @PathVariable Long id) {

        Authentication auth = usuarioController.retornarUsuarioLogueado();

        model.addAttribute("autoridad", auth.getAuthorities().toString());

        Pagina pagina = null;
        pagina = paginaImpl.obtenerContenido(id);
        Usuario user = null;
        user = usuarioService.findOne(auth.getName());

        model.addAttribute("pagina", pagina);
        model.addAttribute("usuario", user);

        String dis = pagina.getDiscriminante();

        if (Objects.equals(dis, "PRACTICA")) {

            Optional<Pagina> paginaOptional = iPaginaDAO.findById(id);
            Pagina paginaPractica = paginaOptional.get();

            model.addAttribute("preguntas", paginaPractica.getPreguntas());

            return "cuestionarios/index";
        }

        if (pagina != null) {
            List<Pagina> paginas = new ArrayList<>();
            paginas.addAll(user.getPaginas());
            
            if (!paginas.contains(pagina)) {
                paginas.add(pagina);
            }

            user.setPaginas(paginas);
            usuarioDAO.save(user);
        }
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
        	paginasTotales.removeIf(obj -> obj.getDiscriminante().equals("PRACTICA"));
        }

        // ----------------

        String json = "{\"pagesViewed\": " + paginasVistas.size() + ", \"totalPages\": " + paginasTotales.size() + "}";

        return ResponseEntity.status(HttpStatus.OK).body(json);
    }
}

