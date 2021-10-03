package com.scrum.ude.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.scrum.ude.entity.Proyecto;
import com.scrum.ude.service.Interfaces.IProyecto;

@Service
public class ProyectoServiceImpl implements IProyecto  {

	private static final Logger log = LogManager.getLogger(ProyectoServiceImpl.class);
	
	@PersistenceContext
	private EntityManager em;
	

	@Override
   	public List<Proyecto> buscarProyectoPorUsuario(Long id) {

		//String sql="SELECT p.* FROM proyecto p, usuario u WHERE u.id=p.id AND  u.id=1";
   		//Query query =em.createNativeQuery(sql);
		Query query = em.createQuery("select p from  Proyecto p JOIN FETCH p.usuario u where  u.id=:id");
   		query.setParameter("id", id);

   		List<Proyecto> proyectos = null;
   		try {
   			log.info("Chequear ");
   			proyectos = (List<Proyecto>) query.getResultList();
   		} catch (Exception nre) {
   			 log.info("No se ha encontrado  usuarios");

   		}
   		return proyectos;

   	}
	
	@Override
   	public List<Proyecto> buscarPorusuarioProyectos(Long id) {

		//String sql="SELECT p.* FROM proyecto p, usuario u WHERE u.id=p.id AND  u.id=1";
   		//Query query =em.createNativeQuery(sql);
		Query query = em.createQuery("select u from  Usuario u JOIN FETCH u.proyecto p where  u.id=:id");
   		query.setParameter("id", id);

   		List<Proyecto> proyectos = null;
   		try {
   			log.info("Chequear ");
   			proyectos = (List<Proyecto>) query.getResultList();
   		} catch (Exception nre) {
   			 log.info("No se ha encontrado  usuarios");

   		}
   		return proyectos;

   	}
	
	
	
	
	
	@Override
   	public Proyecto buscarProyectoPorUsuarioWithTitulo(Long id,String titulo) {

   		
   		Query query = em.createQuery("select p from Proyecto p JOIN FETCH p.usuario u where u.id=:id and p.titulo=:titulo");
   		query.setParameter("id", id);
   		query.setParameter("titulo", titulo);

   		Proyecto proyecto = null;
   		try {
   			log.info("Chequear ");
   			proyecto = (Proyecto) query.getSingleResult();
   		} catch (Exception nre) {
   			 log.info("No se ha encontrado  usuarios");

   		}
   		return proyecto;

   	}
	
	@Override
   	public Proyecto buscarPorCodigoProyecto( String codigo) {

   		
   		Query query = em.createQuery("select p from Proyecto p  where p.codigoProyecto=:codigo ");
   		query.setParameter("codigo",codigo);

   		Proyecto proyecto = null;
   		try {
   			log.info("Chequear ");
   			proyecto = (Proyecto) query.getSingleResult();
   		} catch (Exception nre) {
   			 log.info("No se ha encontrado  Proyecto");

   		}
   		return proyecto;

   	}
	
    
    @Override
   	public Proyecto buscarPorIdProyecto(Long id) {

   		
   		Query query = em.createQuery("select p from Proyecto p where p.id=:id");
   		query.setParameter("id", id);

   		Proyecto proyecto = null;
   		try {
   			log.info("Chequear ");
   			proyecto = (Proyecto) query.getSingleResult();
   		} catch (Exception nre) {
   			 log.info("No se ha encontrado  proyecto");

   		}
   		return proyecto;

   	}
    
//    @Override
//   	public void elimnarProyectobyUsuario(Long id) {
//
//   		
//   		em.createNativeQuery("select p from Proyecto p where p.id=:id");
//   		//query.setParameter("id", id);
//
//   		Proyecto proyecto = null;
//   		try {
//   			log.info("Chequear ");
//   			proyecto = (Proyecto) query.getSingleResult();
//   		} catch (Exception nre) {
//   			 log.info("No se ha encontrado  proyecto");
//
//   		}
//   
//
//   	}
//    
    
}
