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
   	public Curso codigoCurso(Long id) {

   		
   		Query query = em.createQuery("select c from Curso c JOIN FETCH c.capitulos  where c.id=:id");
   		query.setParameter("id",id);

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
