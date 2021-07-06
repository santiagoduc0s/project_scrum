package com.scrum.ude.controller;

import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.scrum.ude.dao.ICodigoRegistro;
import com.scrum.ude.entity.CodigoRegistro;

import jdk.internal.org.jline.utils.Log;

@Controller
public class codigoController {
@Autowired
	private ICodigoRegistro codigoRegistro;
 	
@GetMapping("/vistaCodigoRegistro")
public String verVistaCodigoRegistro(Model model){
	
	
	
	
	return"/generarCodigo";
	
	
	
}

@PostMapping("/generarCodigoRegistro")
public String generarCodigo(Model model) {
	
	//Date fecha= new Date();
	
	Calendar fecha = Calendar.getInstance();
	
	int hora = fecha.get(Calendar.HOUR_OF_DAY);
    int minuto = fecha.get(Calendar.MINUTE);
    
    int codigo=(int) (hora+minuto*Math.random());
    
    HashCode sha256hex = Hashing.sipHash24()
    		  .hashInt(codigo);
    
    CodigoRegistro  regis = new CodigoRegistro();
  
    
    regis.setCodigo(sha256hex.toString());
   
    codigoRegistro.save(regis);
	
	model.addAttribute("codigo",sha256hex);
	return"/generarCodigo";
}


}
