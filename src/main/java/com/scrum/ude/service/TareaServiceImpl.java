package com.scrum.ude.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.scrum.ude.entity.Tarea;
import com.scrum.ude.service.Interfaces.ITarea;

@Service
public class TareaServiceImpl implements ITarea {

	
	private static final Logger log = LogManager.getLogger(TareaServiceImpl.class);
	
	@PersistenceContext
	private EntityManager em;


	@Override
   	public Tarea buscarPorNombreTarea(String nombre) {

   		
   		Query query = em.createQuery("select t from Tarea t where t.nombre=:nombre");
   		query.setParameter("nombre", nombre);

   		Tarea tarea = null;
   		try {
   			log.info("Chequear ");
   			tarea = (Tarea) query.getSingleResult();
   		} catch (Exception nre) {
   			 log.info("No se ha encontrado  nombre con dicha tarea");

   		}
   		return tarea;

   	}

	@Override
	public Tarea buscarPorIdTarea(Long id) {
		Query query = em.createQuery("select t from  Tarea t JOIN FETCH t.proyecto p where t.id=:id");
		query.setParameter("id", id);

		Tarea tarea = null;
		try {
			log.info("Chequear ");
			tarea = (Tarea) query.getSingleResult();
		} catch (Exception nre) {
			log.info("No se ha encontrado  nombre con dicha tarea");

		}
		return tarea;
	}
}
