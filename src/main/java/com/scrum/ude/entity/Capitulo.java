package com.scrum.ude.entity;
import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Capitulo implements Serializable {

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private Long id;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "Curso")
private Curso curso;


@OneToMany
private List<Pagina> pagina;

@Column
private String titulo;

@Column
private String descripcion;

public Capitulo() {
	
}

public Long getId() {
	return id;
}


public void setId(Long id) {
	this.id = id;
}

public Curso getCurso() {
	return curso;
}

public void setCurso(Curso curso) {
	this.curso = curso;
}

public List<Pagina> getPagina() {
	return pagina;
}

public void setPagina(List<Pagina> pagina) {
	this.pagina = pagina;
}

public String getTitulo() {
	return titulo;
}

public void setTitulo(String titulo) {
	this.titulo = titulo;
}

public String getDescripcion() {
	return descripcion;
}

public void setDescripcion(String descripcion) {
	this.descripcion = descripcion;
}



}

