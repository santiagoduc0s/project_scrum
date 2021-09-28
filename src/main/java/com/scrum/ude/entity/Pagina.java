package com.scrum.ude.entity;
import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="pagina")
public class Pagina implements Serializable {
	
private static final long serialVersionUID = 1L;

@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private Long id;



@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "paginas")
private List<Usuario> usuario;
//@JsonIgnore


@Column
private String titulo;

@Column
private String contenido;

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


public List<Usuario> getUsuario() {
	return usuario;
}

public void setUsuario(List<Usuario> usuario) {
	this.usuario = usuario;
}

public void setContenido(String contenido) {
	this.contenido = contenido;
}

}
