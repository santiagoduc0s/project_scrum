package com.scrum.ude.controller;


import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
import com.scrum.ude.service.MailService;
import com.scrum.ude.service.UsuarioServiceImpl;

@Controller
public class UsuarioController {

    @Autowired
    private IUsuarioDAO usuarioDAO;

    @Autowired
    private MailService mailService;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ICodigoRegistro codigoDAO;
    @Autowired
    private WebSecurityConfig ws;

    @Autowired
    private CodigoServiceImpl codigoImpl;

    @Autowired
    private UsuarioServiceImpl usuarioService;

    // retorna el usuario Logeado para todo el contexto
    public Authentication retornarUsuarioLogueado() {
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
    public String procesar(Usuario usuarioDTO, Model model, @NotNull BindingResult result, @RequestParam String repeatPassword, RedirectAttributes flash) {

        if (result.hasErrors() || !usuarioDTO.getPassword().equals(repeatPassword)) {

            flash.addFlashAttribute("danger", "Se produjo un error");
            return "/registroUsuario/registroUsuario";
        }

        Usuario usuario = (Usuario) usuarioService.findOne(usuarioDTO.getUserName());

        if (usuario == null) {

            usuarioDTO.setRol("ROLE_USER");

            CodigoRegistro codigoRegistro = codigoImpl.findCodigo(usuarioDTO.getCodigoRegistro().getCodigo());

            Date fechaHoy = new Date();

            Calendar calendar1 = Calendar.getInstance();

            calendar1.setTime(fechaHoy);

            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(codigoRegistro.getFecha());


            System.out.println("dddd");
            int dias = calendar2.get(Calendar.DAY_OF_MONTH) + 5;

            calendar2.set(Calendar.DAY_OF_MONTH, dias);

            System.out.println(calendar2.get(Calendar.DAY_OF_MONTH));

            if (calendar2.after(calendar1) || calendar2.get(Calendar.DAY_OF_MONTH) == calendar1.get(Calendar.DAY_OF_MONTH)) {

                if (codigoRegistro != null && !codigoRegistro.isUsado()) {

                    codigoRegistro.setUsado(true);
                    usuarioDTO.setCodigoRegistro(codigoRegistro);

                } else {

                    return "/registroUsuario/registroUsuario";
                }

            } else {
                System.out.println("tiempo excedido para el codigo registro");
                return "/registroUsuario/registroUsuario";
            }
            usuarioService.agregarUsuario(usuarioDTO);

            flash.addFlashAttribute("success", "Usuario Registrado con Exito");

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

        if (auth.getAuthorities().toString().equals("[ROLE_ADMIN]")) {


            List<Usuario> usuarios = (List<Usuario>) usuarioDAO.findAll();
              
            if(usuarios.removeIf(t -> t.getUserName() == auth.getName()))
            	  

            model.addAttribute("usuarios", usuarios);
            Usuario usuario = new Usuario();

            model.addAttribute("usuario", usuario);

            model.addAttribute("autoridad", auth.getAuthorities().toString());
            return "/admin/listadoUsuarios";

        }
        model.addAttribute("autoridad", auth.getAuthorities().toString());

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
    public String buscarUsuario(Model model, Usuario usuario, BindingResult result) {

        if (usuario.getCedula() != null) {

            Usuario usu = usuarioService.buscarPorCedula(usuario.getCedula());

            model.addAttribute("usuarios", usu);

        } else {

            List<Usuario> usuarios = (List<Usuario>) usuarioDAO.findAll();
            
            model.addAttribute("usuarios", usuarios);

        }

        return "/admin/listadoUsuarios";
    }

    //modifico los datos del usuario
    @GetMapping(value = "/modificar/{id}")
    public String modificarUsuario(@PathVariable(value = "id") Long id, RedirectAttributes flash, Model model) {
        Optional<Usuario> usuario = Optional.ofNullable(new Usuario());

        if (id > 0) {

            Authentication auth = retornarUsuarioLogueado();
            Usuario user = usuarioService.buscarPorId(id);
            usuario = usuarioDAO.findById(id);

            flash.addFlashAttribute("success", "Usuario  eliminado con éxito!");
            model.addAttribute("id", id);
            model.addAttribute("usuario", usuario);
            model.addAttribute("username", user.getUserName().toString());
            // model.addAttribute("autoridad",auth.getAuthorities().toString());
            model.addAttribute("autoridad", auth.getName());


        }
        return "/admin/ModificarUsuariosPerfilAdmin";
    }

    // Perfil Usuario ver datos personales
    @GetMapping("/verDatosPersonales/{id}")
    public String verDatosPersonales(@PathVariable(value = "id") Long id, Model model) {

        Authentication auth = retornarUsuarioLogueado();
        Usuario user = usuarioService.buscarPorId(id);

        //Usuario user=usuarioDAO.findByUserName(userDetail.getUsername());
        model.addAttribute("id", user.getId());
        model.addAttribute("usuario", user);
        //Usuario user=
        model.addAttribute("autoridad", auth.getAuthorities().toString());

        if (auth.getAuthorities().toString() == "[ROLE_USER]") {

            return "/user/modificarUser";
        }

        return "/admin/modificarUser";
    }
    // Perfil Usuario ver datos personales
    
    @GetMapping("/verDatosPersonalesAdministrador")
    public String verDatosPersonales( Model model) {

        Authentication auth = retornarUsuarioLogueado();
        Usuario user =usuarioService.buscarUsuarioPorUsername(auth.getName());

        //Usuario user=usuarioDAO.findByUserName(userDetail.getUsername());
        model.addAttribute("id", user.getId());
        model.addAttribute("usuario", user);
        //Usuario user=
        model.addAttribute("autoridad", auth.getAuthorities().toString());

//        if (auth.getAuthorities().toString() == "[ROLE_USER]") {
//
//            return "/user/modificarUser";
//        }

        return "/admin/modificarUsuariosNoAdministrador";
    }
    

    //Se procesan los cambios del Usuario, Perfil Administrador
    @PostMapping("/usuarioModificado")
    public String guardarUsuarioModificadoAdmin(Usuario usuario, @RequestParam(value = "id") Long id, Model model, @RequestParam(value = "contrasena") String contrasena, @RequestParam(value = "nuevaContrasena")
            String nuevaContrasena, @RequestParam(value = "confirmacionContrasena") String confirmacionContrasena) {

        System.out.println("USUARIO ID  ES    " + usuario.getId());

        Usuario user = usuarioService.buscarPorId(id);


        user.setNombre(usuario.getNombre());
        user.setApellido(usuario.getApellido());
        user.setCedula(usuario.getCedula());
        user.setMail(usuario.getMail());
        user.setRol(usuario.getRol());
        //user.setUserName(usuario.getUserName());

        if (ws.passwordEncoder().matches(contrasena, user.getPassword())) {

            if (!confirmacionContrasena.equals(nuevaContrasena)) {

                return "/admin/modificarUsuariosPerfilAdmin";

            }


            if (!nuevaContrasena.isEmpty()) {

                String contra = ws.passwordEncoder().encode(nuevaContrasena);
                user.setPassword(contra);
            }
        }


        usuarioDAO.save(user);

        List<Usuario> usuarios = (List<Usuario>) usuarioDAO.findAll();
        if(usuarios.removeIf(t -> t.getUserName() == user.getUserName()))

        model.addAttribute("usuarios", usuarios);
        Usuario usuariose = new Usuario();

        model.addAttribute("usuario", usuariose);


        return "/admin/listadoUsuarios";


    }

    // Se procesan datos de la modificacion del  propio Usuario

    @PostMapping("/modificarDatosPersonales")
    public String modificarDatosPersonalesUsuario(Model model, Usuario usuario, @RequestParam(value = "id")
            Long id, @RequestParam(value = "contrasena") String contrasena, @RequestParam(value = "nuevaContrasena")
                                                          String nuevaContrasena, @RequestParam(value = "confirmacionContrasena") String confirmacionContrasena, RedirectAttributes flash) {

        Usuario user = usuarioService.buscarPorId(id);
        if (ws.passwordEncoder().matches(contrasena, user.getPassword())) {

            if (!confirmacionContrasena.equals(nuevaContrasena)) {

                return "/user/modificarUser";
            }

        } else {
            user.setNombre(usuario.getNombre());
            user.setApellido(usuario.getApellido());
            user.setCedula(usuario.getCedula());
            user.setMail(usuario.getMail());
            //user.setUserName(usuario.getUserName());
            user.setPassword(user.getPassword());


        }


        if (!nuevaContrasena.isEmpty()) {

            String contra = ws.passwordEncoder().encode(nuevaContrasena);
            user.setPassword(contra);
        }

        usuarioDAO.save(user);

        model.addAttribute("usuario", user);


        return "redirect:/menuCambiado/" + user.getUserName() + "";

    }


    // Aun no decido errores para la pantalla
    public String error(ModelAndView model) {

        Authentication auth = retornarUsuarioLogueado();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();

        Usuario user = usuarioDAO.findByUserName(userDetail.getUsername());
        model.addObject("usuario", user);
        model.addObject("error", "Error No se logro guardar los cambios");
        //Usuario user=


        return "/user/modificarUser";
    }


    @GetMapping("/recuperar-password/{email}")
    public ResponseEntity<?> solicitarContrasena(@PathVariable(value = "email") String email) {

        Usuario user = usuarioService.buscarPorMail(email);

        String contra = "";

        if (user != null) {

            Calendar fecha = Calendar.getInstance();

            int minuto = fecha.get(Calendar.MINUTE);
            int numero = (int) (minuto * Math.random());
            contra = "" + numero;

            String password = ws.passwordEncoder().encode(contra);

            user.setPassword(password);

            usuarioDAO.save(user);

            String message = "Su nueva contraseña es: " + contra;

            mailService.sendMail("romina134262@gmail.com", email, "Recuperar contraseña PES", message);

        }

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"code\": \"1\"}");
        }
        return ResponseEntity.status(HttpStatus.OK).body("{\"code\": \"1\"}");
    }
    
    
   

}
