package com.scrum.ude.entity;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Inscripto {

@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private  Long id;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "fk_usuario")
private Usuario usuario;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "fk_curso")
private Curso curso;


public Inscripto() {
	
}


public Long getId() {
	return id;
}


public void setId(Long id) {
	this.id = id;
}


public Usuario getUsuario() {
	return usuario;
}


public void setUsuario(Usuario usuario) {
	this.usuario = usuario;
}


public Curso getCurso() {
	return curso;
}


public void setCurso(Curso curso) {
	this.curso = curso;
}

}
