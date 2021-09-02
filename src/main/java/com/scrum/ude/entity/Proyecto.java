package com.scrum.ude.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Proyecto implements Serializable {

	private static final long serialVersionUID = 1L;

	public Proyecto() {}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private  Long id;
	
	@ManyToMany
	private List<Usuario> usuario;
	
	@OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL)
	private Set<Tarea> tarea;
	
	@Column
	private String  titulo;
	
	@Column
	private String  descripcion;
	
	@Column
	private String  codigoProyecto;
	
	@Column
	private boolean creador;
	


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Set<Tarea> getTarea() {
		return tarea;
	}

	public void setTarea(Set<Tarea> tarea) {
		this.tarea = tarea;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	

	public String getCodigoProyecto() {
		return codigoProyecto;
	}
	

	public boolean isCreador() {
		return creador;
	}

	public void setCreador(boolean creador) {
		this.creador = creador;
	}

	public List<Usuario> getUsuario() {
		return usuario;
	}

	public void setUsuario(List<Usuario> usuario) {
		this.usuario = usuario;
	}

	public void setCodigoProyecto(String codigoProyecto) {
		this.codigoProyecto = codigoProyecto;
	}

	
}
