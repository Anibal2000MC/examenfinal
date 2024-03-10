package com.example.proyecto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.proyecto.model.Genero;
import com.example.proyecto.repository.GeneroRepository;

@Service
public class GeneroService {

	@Autowired
	GeneroRepository generoRepository;
	
	public List<Genero> getAllGeneros() {
		return generoRepository.findAll();
	}

	public Genero getGeneroById(Long id) {
		return generoRepository.findById(id).orElse(null);
	}
}
