package com.scrum.ude.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.scrum.ude.entity.Curso;
import com.scrum.ude.service.Interfaces.ICurso;

@Service
public class CursoServiceImpl implements ICurso {
	
	private static final Logger log = LogManager.getLogger(UsuarioServiceImpl.class);
    
	@PersistenceContext
	private EntityManager em;
	
	@Override
   	public Curso codigoCurso(int codigo) {

   		
   		Query query = em.createQuery("select c from Curso c where c.codigoCurso=:codigo");
   		query.setParameter("codigo",codigo);

   		Curso curso = null;
   		try {
   			log.info("Chequear ");
   			curso = (Curso) query.getSingleResult();
   			
   			
   			
   		} catch (Exception nre) {
   			 log.info("No se ha encontrado  nombre con dicho Curso ");

   		}
   		return curso;

   	}
}
