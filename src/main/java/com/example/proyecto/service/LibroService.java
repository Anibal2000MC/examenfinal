package com.example.proyecto.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.proyecto.model.Libro;
import com.example.proyecto.repository.LibroRepository;

@Service
public class LibroService {

	@Autowired
	LibroRepository libroRepository;

	public List<Libro> getAllLibros() {
		return libroRepository.findAll();
	}

	public Libro getLibroById(Long id) {
		return libroRepository.findById(id).orElse(null);
	}

	public Libro createLibro(Libro libro) {
		return libroRepository.save(libro);
	}

	public void deleteLibro(Long id) {
		libroRepository.deleteById(id);
	}

	public List<Object[]> contarLibrosUltimosSeisMesesPorGenero() {
	    // Calcula la fecha de inicio para los últimos seis meses
	    LocalDate fechaInicio = LocalDate.now().minusMonths(6);

	    // Llama al método del repositorio para contar los libros por género en el período especificado
	    return libroRepository.contarLibrosPorGeneroUltimosSeisMeses(fechaInicio);
	}


}
