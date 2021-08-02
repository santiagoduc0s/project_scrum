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

@Entity
public class Pagina implements Serializable {
	
private static final long serialVersionUID = 1L;

@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private Long id;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name="capitulo")
private Capitulo capitulo;

@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
@JoinTable(name = "usuario_Paginas")
@JoinColumn(name="Pagina_id")
private List<Usuario> usuario;
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


public Capitulo getCapitulo() {
	return capitulo;
}

public void setCapitulo(Capitulo capitulo) {
	this.capitulo = capitulo;
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
