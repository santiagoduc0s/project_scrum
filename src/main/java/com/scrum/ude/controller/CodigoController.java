package com.scrum.ude.controller;

import java.util.Calendar;
import java.util.Date;

import com.scrum.ude.dao.IUsuarioDAO;
import com.scrum.ude.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.scrum.ude.dao.ICodigoRegistro;
import com.scrum.ude.entity.CodigoRegistro;

@Controller
public class CodigoController {

	@Autowired
	private UsuarioController usuarioController;

	@Autowired
	private IUsuarioDAO usuarioDAO;

	@Autowired
	private ICodigoRegistro codigoRegistro;
	//navegar Vista  Generar Codigo Registro
	@GetMapping("/vistaCodigoRegistro")
	public String verVistaCodigoRegistro(Model model) {

		Authentication auth = usuarioController.retornarUsuarioLogueado();

		UserDetails userDetail = (UserDetails) auth.getPrincipal();

		Usuario us=usuarioDAO.findByUserName(userDetail.getUsername());

		model.addAttribute("usuario",us);

		model.addAttribute("autoridad", auth.getAuthorities().toString());

		return "/generarCodigoRegistro/generarCodigo";

	}
	//genera codigo registro
	@PostMapping("/generarCodigoRegistro")
	public String generarCodigo(Model model) {

		Authentication auth = usuarioController.retornarUsuarioLogueado();

		UserDetails userDetail = (UserDetails) auth.getPrincipal();

		Usuario us=usuarioDAO.findByUserName(userDetail.getUsername());

		model.addAttribute("usuario",us);

		model.addAttribute("autoridad", auth.getAuthorities().toString());

		Calendar fecha = Calendar.getInstance();

		int hora = fecha.get(Calendar.HOUR_OF_DAY);
		int minuto = fecha.get(Calendar.MINUTE);

		int codigo = (int) (hora + minuto * Math.random());

		HashCode sha256hex = Hashing.sipHash24().hashInt(codigo);

		CodigoRegistro regis = new CodigoRegistro();

		regis.setCodigo(sha256hex.toString());
		regis.setFecha(new Date());

		codigoRegistro.save(regis);

		model.addAttribute("codigo", sha256hex);
		return "/generarCodigoRegistro/generarCodigo";
	}

}
