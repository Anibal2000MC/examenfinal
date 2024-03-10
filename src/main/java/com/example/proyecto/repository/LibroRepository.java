package com.example.proyecto.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.proyecto.model.Libro;

public interface LibroRepository extends JpaRepository<Libro, Long> {

	@Query("SELECT l.genero.nombre, COUNT(l) FROM Libro l WHERE l.fechaPublicacion >= :fechaInicio GROUP BY l.genero.nombre")
	List<Object[]> contarLibrosPorGeneroUltimosSeisMeses(@Param("fechaInicio") LocalDate fechaInicio);

}
