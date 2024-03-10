package com.example.proyecto.model;

public class Reporte {

	private String nombreGenero;
	private Long cantidadLibros;
	public String getNombreGenero() {
		return nombreGenero;
	}
	public void setNombreGenero(String nombreGenero) {
		this.nombreGenero = nombreGenero;
	}
	public Long getCantidadLibros() {
		return cantidadLibros;
	}
	public void setCantidadLibros(Long cantidadLibros) {
		this.cantidadLibros = cantidadLibros;
	}
	public Reporte(String nombreGenero, Long cantidadLibros) {
		super();
		this.nombreGenero = nombreGenero;
		this.cantidadLibros = cantidadLibros;
	}
	public Reporte() {
		super();
	}
	
	
}
