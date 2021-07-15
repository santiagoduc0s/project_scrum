package com.scrum.ude.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.scrum.ude.entity.CodigoRegistro;
import com.scrum.ude.service.Interfaces.ICodigo;

@Service
public class CodigoServiceImpl implements ICodigo {
	
	private static final Logger log = LogManager.getLogger(CodigoServiceImpl.class);
	@PersistenceContext
	private EntityManager em;

	 @Override
	  	public CodigoRegistro findCodigo(String codigo) {
	  		
	  		Query query = em.createQuery("select c from CodigoRegistro c where c.codigo=:codigo");
	  		query.setParameter("codigo", codigo);

	  		CodigoRegistro codigoRegistro = null;
	  		try {
	  			log.info("Chequear ");
	  			codigoRegistro = (CodigoRegistro) query.getSingleResult();
	  		} catch (Exception nre) {
	  			 log.info("No se ha encontrado  codigoRegistro");

	  		}
	  		return codigoRegistro;

	  	}	
	
}
