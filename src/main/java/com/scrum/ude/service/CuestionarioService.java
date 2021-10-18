package com.scrum.ude.service;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

import com.scrum.ude.dao.IOpcionDAO;
import com.scrum.ude.dao.IPreguntaDAO;
import com.scrum.ude.entity.Capitulo;
import com.scrum.ude.entity.CodigoRegistro;
import com.scrum.ude.entity.Opcion;
import com.scrum.ude.entity.Pregunta;
import com.scrum.ude.service.Interfaces.ICuestionario;

public class CuestionarioService  implements ICuestionario{
	@Autowired
	private IOpcionDAO opcionDAO;
	
	@Autowired
	private IPreguntaDAO  preguntaDAO;
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Opcion respuestaCorrecta(Long idPregunta,Long idOpcion) {
		
		Pregunta pre =buscarPregunta(idPregunta);
		
		Opcion opcion=buscarOpcion(idOpcion);
		
		if(pre.getOpciones().contains(opcion)) {
		 
		return opcion;
	}
		
		return null;
	}
	
	@Override
  	public Pregunta buscarPregunta(Long id) {
  		
  		Query query = em.createQuery("select p from Pregunta p  where c.id=:id");
  		query.setParameter("id", id);

  		Pregunta pregunta = null;
  		try {
  			
  			pregunta = (Pregunta) query.getSingleResult();
  		} catch (Exception nre) {
  			

  		}
  		return pregunta;

  	}		
	
	
	@Override
  	public 	Opcion buscarOpcion(Long id) {
  		
  		Query query = em.createQuery("select o from Opcion o  where c.id=:id");
  		query.setParameter("id", id);

  		Opcion opcion = null;
  		try {
  			
  			opcion = (Opcion) query.getSingleResult();
  		} catch (Exception nre) {
  			

  		}
  		return opcion;

  	}		


	
	

}
