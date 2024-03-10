package com.example.proyecto.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name="libro")
public class Libro {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	public Long id;
	
	@Column(name="nombre", nullable = false, length = 60)
	public String nombre;
	
	@Column(name="autor", nullable = false, length = 60)
	public String autor;
	
	@Column(name="fechaPublicacion", nullable = false)
	public LocalDate fechaPublicacion;
	
	@ManyToOne
	@JoinColumn(name="genero", nullable=false)
	public Genero genero;
	
	@Transient
	public String nombreGenero ;

	public String getNombreGenero() {
		nombreGenero = genero.nombre;
		return nombreGenero;
	}

	public void setNombreGenero(String nombreGenero) {
		this.nombreGenero = nombreGenero;
	}

	public Libro(Long id, String nombre, String autor, LocalDate fechaPublicacion, Genero genero) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.autor = autor;
		this.fechaPublicacion = fechaPublicacion;
		this.genero = genero;
	}

	public Libro() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public LocalDate getFechaPublicacion() {
		return fechaPublicacion;
	}

	public void setFechaPublicacion(LocalDate fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}

	public Genero getGenero() {
		return genero;
	}

	public void setGenero(Genero genero) {
		this.genero = genero;
	}
	
	
	
}
