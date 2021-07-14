package com.scrum.ude.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Curso {
	
@Id
@GeneratedValue(strategy =GenerationType.AUTO)
private Long id;

@Column
private int codigoCurso;

@Column
private String nombre;



public Curso() {
	
}

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public int getCodigoCurso() {
	return codigoCurso;
}

public void setCodigoCurso(int codigoCurso) {
	this.codigoCurso = codigoCurso;
}

public String getNombre() {
	return nombre;
}

public void setNombre(String nombre) {
	this.nombre = nombre;
}





}
