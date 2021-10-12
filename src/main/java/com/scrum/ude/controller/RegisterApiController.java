package com.scrum.ude.controller;

import com.scrum.ude.entity.CodigoRegistro;
import com.scrum.ude.entity.Usuario;
import com.scrum.ude.service.CodigoServiceImpl;
import com.scrum.ude.service.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;

@RestController
@RequestMapping("/api/register")
public class RegisterApiController {

    @Autowired
    private CodigoServiceImpl codigoImpl;

    @Autowired
    private UsuarioServiceImpl usuarioService;

    @GetMapping("/codigo-registro-disponible/{codigo}")
    public ResponseEntity<?> existeCodigoRegistroDisponible(@PathVariable String codigo) {

        boolean existe = false;

        CodigoRegistro codigoRegistro = codigoImpl.findCodigo(codigo);

        if (codigoRegistro != null) {
            Date fechaHoy = new Date();

            Calendar calendar1 = Calendar.getInstance();

            calendar1.setTime(fechaHoy);

            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(codigoRegistro.getFecha());

            int dias = calendar2.get(Calendar.DAY_OF_MONTH) + 5;

            calendar2.set(Calendar.DAY_OF_MONTH, dias);

            if (calendar2.after(calendar1) || calendar2.get(Calendar.DAY_OF_MONTH) == calendar1.get(Calendar.DAY_OF_MONTH)) {
                if (codigoRegistro != null && !codigoRegistro.isUsado()) {
                    existe = true;
                }
            }
        }

        if (existe) {
            return ResponseEntity.status(HttpStatus.OK).body("{\"code\": \"1\"}");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"code\": \"0\"}");
    }

    @GetMapping("/username-disponible/{user}")
    public ResponseEntity<?> existeUsernameDisponible(@PathVariable String user) {

        boolean existe = false;
        existe = usuarioService.ExisteUsuario(user);

        if (!existe) {
            return ResponseEntity.status(HttpStatus.OK).body("{\"code\": \"1\"}");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"code\": \"0\"}");
    }

    @GetMapping("/email-disponible/{email}")
    public ResponseEntity<?> existeEmailDisponible(@PathVariable String email) {

        boolean existe = false;
        existe = usuarioService.ExisteMail(email);

        if (!existe) {
            return ResponseEntity.status(HttpStatus.OK).body("{\"code\": \"1\"}");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"code\": \"0\"}");
    }

    @GetMapping("/cedula-disponible/{cedula}")
    public ResponseEntity<?> existeCedulaDisponible(@PathVariable Long cedula) {

        boolean existe = false;
        Usuario usuario = usuarioService.buscarPorCedula(cedula);

        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.OK).body("{\"code\": \"1\"}");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"code\": \"0\"}");
    }

}
