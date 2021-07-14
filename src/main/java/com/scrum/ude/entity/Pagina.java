package com.scrum.ude.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Pagina {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private Long id;

@Column
private String titulo;

@Column
private String contenido;

@OneToOne
private Capitulo capitulo;
public Pagina() {
	
	
}

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

public String getContenido() {
	return contenido;
}

public void setContenido(String contenido) {
	this.contenido = contenido;
}

}
