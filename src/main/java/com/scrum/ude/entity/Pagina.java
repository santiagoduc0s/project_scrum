package com.scrum.ude.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "pagina")
public class Pagina implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "paginas")
    private List<Usuario> usuario;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "capitulo_id")
    public Capitulo capitulo;

	// preguntas
	@OneToMany(
			fetch = FetchType.LAZY,
			cascade = CascadeType.ALL,
			mappedBy = "pagina"
	)
	public List<Pregunta> preguntas = new ArrayList<>();

    private String titulo;
    private String contenido;
	public String discriminante; // TEORICA o PRACTICA

	// -------------------------

    public Pagina() {}

	// -------------------------

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Usuario> getUsuario() {
		return usuario;
	}

	public void setUsuario(List<Usuario> usuario) {
		this.usuario = usuario;
	}

	public Capitulo getCapitulo() {
		return capitulo;
	}

	public void setCapitulo(Capitulo capitulo) {
		this.capitulo = capitulo;
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

	public String getDiscriminante() {
		return discriminante;
	}

	public void setDiscriminante(String discriminante) {
		this.discriminante = discriminante;
	}
}
