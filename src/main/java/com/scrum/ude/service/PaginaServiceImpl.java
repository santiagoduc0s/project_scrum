package com.scrum.ude.service;

import java.util.List;

import javax.persistence.EntityManager; 
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.scrum.ude.entity.Pagina;
import com.scrum.ude.service.Interfaces.IPagina;
@Service
public class PaginaServiceImpl implements IPagina {

	private static final Logger log = LogManager.getLogger(PaginaServiceImpl.class);
	@PersistenceContext
	private EntityManager em;

	@Override
	  	public Pagina obtenerContenido(Long id) {
	  		
	  		Query query = em.createQuery("select p from Pagina p  where p.id=:id");
	  		query.setParameter("id", id);
	  		Pagina pagina = null;
	  		try {
	  			log.info("Chequear ");
	  			pagina = (Pagina) query.getSingleResult();
	  		} catch (Exception nre) {
	  			 log.info("No se ha encontrado  Paginas con dado Capitulo");

	  		}
	  		return pagina;

	  	}		
	
	
	@Override
  	public List<Pagina> obtenerPaginas() {
  		
  		Query query = em.createQuery("select p from Pagina p  ");
  		
  		List<Pagina> pagina = null;
  		try {
  			log.info("Chequear ");
  			pagina = (List<Pagina>) query.getResultList();
  		} catch (Exception nre) {
  			 log.info("No se ha encontrado  Paginas con dado Capitulo");

  		}
  		return pagina;

  	}		

}
