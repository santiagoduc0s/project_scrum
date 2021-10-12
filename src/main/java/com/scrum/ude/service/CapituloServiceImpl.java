package com.scrum.ude.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.scrum.ude.entity.Capitulo;
import com.scrum.ude.entity.CodigoRegistro;
import com.scrum.ude.entity.Pagina;
import com.scrum.ude.service.Interfaces.ICapitulo;

@Service
public class CapituloServiceImpl implements ICapitulo {

	
	private static final Logger log = LogManager.getLogger(CapituloServiceImpl.class);
	@PersistenceContext
	private EntityManager em;

	@Override
	  	public Capitulo buscarPaginas(Long id) {
	  		
	  		Query query = em.createQuery("select c from Capitulo c  where c.id=:id");
	  		query.setParameter("id", id);

	  		Capitulo capitulo = null;
	  		try {
	  			log.info("Chequear ");
	  			capitulo = (Capitulo) query.getSingleResult();
	  		} catch (Exception nre) {
	  			 log.info("No se ha encontrado  Paginas con dado Capitulo");

	  		}
	  		return capitulo;

	  	}		
	
	@Override
  	public List<Capitulo> buscarPaginas() {
  		
  		Query query = em.createQuery("select c from Capitulo c ");
  		

  		List<Capitulo> capitulo = null;
  		try {
  			log.info("Chequear ");
  			capitulo = (List<Capitulo>) query.getResultList();
  		} catch (Exception nre) {
  			 log.info("No se ha encontrado  Paginas con dado Capitulo");

  		}
  		return capitulo;

  	}		
	
}
