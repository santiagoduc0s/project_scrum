package com.scrum.ude.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.scrum.ude.repository.ProyectoRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.scrum.ude.entity.Proyecto;
import com.scrum.ude.service.Interfaces.IProyecto;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProyectoServiceImpl implements IProyecto  {

	private static final Logger log = LogManager.getLogger(ProyectoServiceImpl.class);
	
	@PersistenceContext
	private EntityManager em;

	@Autowired
	ProyectoRepository proyectoRepository;
	

	@Override
   	public List<Proyecto> buscarProyectoPorUsuario(Long id) {

		Query query = em.createQuery("select p from  Proyecto p JOIN FETCH p.usuario u where  u.id=:id");
   		query.setParameter("id", id);

   		List<Proyecto> proyectos = null;
   		try {
   			log.info("Inicio de busqueda de Proyectos por Usuario ");
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
   			log.info("Inicio de busqueda de Usuarios del proyecto ");
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
   			log.info("Inicio de busqueda de Proyecto  por Nombre de TItulo ");
   			proyecto = (Proyecto) query.getSingleResult();
   		} catch (Exception nre) {
   			 log.info("No se encuentro Proyecto");

   		}
   		return proyecto;

   	}
	
	@Override
   	public Proyecto buscarPorCodigoProyecto( String codigo) {

   		
   		Query query = em.createQuery("select p from Proyecto p  where p.codigoProyecto=:codigo ");
   		query.setParameter("codigo",codigo);

   		Proyecto proyecto = null;
   		try {
   			log.info("Inicio de busqueda de Codigo de Proyecto ");
   			proyecto = (Proyecto) query.getSingleResult();
   		} catch (Exception nre) {
   			 log.info("No se ha encontrado Codigo Proyecto");

   		}
   		return proyecto;

   	}
	
	
	
	@Override
   	public List<Proyecto> buscarTodosLosProyectos() {

   		
   		Query query = em.createQuery("select p from Proyecto p  JOIN FETCH p.usuario ",Proyecto.class);

   		List<Proyecto> proyecto = null;
   		try {
   			log.info("Inicio de busqueda de Proyectos ");
   			proyecto = (List<Proyecto>) query.getResultList();
   		} catch (Exception nre) {
   			 log.info("No se ha encontrado  Proyectos");

   		}
   		return proyecto;

   	}

	@Override
	@Transactional(readOnly = true)
	public Page<Proyecto> getAll(Pageable pageable) {
		return proyectoRepository.findAll(pageable);
	}


	@Override
   	public Proyecto buscarPorIdProyecto(Long id) {

   		
   		Query query = em.createQuery("select p from Proyecto p where p.id=:id");
   		query.setParameter("id", id);

   		Proyecto proyecto = null;
   		try {
   			log.info("Se inicia Busqueda de Proyecto por ID ");
   			proyecto = (Proyecto) query.getSingleResult();
   		} catch (Exception nre) {
   			 log.info("No se ha Encontrado dicho ID de Proyecto");

   		}
   		return proyecto;

   	}

   public Page<Proyecto> getProyectosLikeTitulo(String titulo, Pageable pageable) {
		return proyectoRepository.findAllByTituloLike(titulo, pageable);
   }

//	@Override
//	public List<Proyecto> buscarProyectosPorUsuario(Long id) {
//
//		Query query = em.createQuery("select p from  Proyecto p JOIN FETCH p.usuario u where  p.id=:id");
//		query.setParameter("id", id);
//
//		List<Proyecto> proyectos = null;
//		try {
//			log.info("Chequear ");
//			proyectos = (List<Proyecto>) query.getResultList();
//		} catch (Exception nre) {
//			log.info("No se ha encontrado  usuarios");
//
//		}
//		return proyectos;
//
//	}

    
}
