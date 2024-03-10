package com.example.proyecto.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.ui.Model;

import com.example.proyecto.service.LibroService;

import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import com.example.proyecto.service.GeneroService;
import com.example.proyecto.model.Libro;
import com.example.proyecto.model.Reporte;
import com.example.proyecto.model.Genero;

@Controller
@RequestMapping("/biblioteca")
public class LibrosController {

	@Autowired
	LibroService libroService;

	@Autowired
	GeneroService generoService;

	@GetMapping("/libros")
	public String showListaLibros(Model model) {

		List<Libro> listLibros = libroService.getAllLibros();

		model.addAttribute("libros", listLibros);

		return "listLibros";
	}

	@GetMapping("/crear")
	public String formularioLibro(Model model) {
		List<Genero> listGeneros = generoService.getAllGeneros();

		model.addAttribute("generos", listGeneros);
		return "crear";
	}

	@PostMapping("/crear")
	public String crearLibro(@RequestParam("nombre") String nombre, @RequestParam("autor") String autor,
			@RequestParam("fechaPublicacion") LocalDate fechaPublicacion, @RequestParam("idGenero") Long idGnero,
			Model model) {

		Libro libro = new Libro();
		libro.nombre = nombre;
		libro.autor = autor;
		libro.fechaPublicacion = fechaPublicacion;

		Genero genero = generoService.getGeneroById(idGnero);
		libro.genero = genero;

		libroService.createLibro(libro);

		model.addAttribute("libros", libroService.getAllLibros());
		model.addAttribute("generos", generoService.getAllGeneros());

		return "listLibros";
	}

	@GetMapping("/edit/{id}")
	public String editorLibro(@PathVariable("id") Long id, Model model) {

		Libro libro = libroService.getLibroById(id);

		model.addAttribute("libro", libro);
		model.addAttribute("generos", generoService.getAllGeneros());

		return "editar";
	}

	@PostMapping("/edit")
	public String editarLibro(@RequestParam("id") Long id, @RequestParam("nombre") String nombre,
			@RequestParam("autor") String autor, @RequestParam("fechaPublicacion") LocalDate fechaPublicacion,
			@RequestParam("idGenero") Long idGenero, Model model) {

		Libro libro = libroService.getLibroById(id);

		libro.nombre = nombre;
		libro.autor = autor;
		libro.fechaPublicacion = fechaPublicacion;

		Genero genero = generoService.getGeneroById(idGenero);
		libro.genero = genero;

		libroService.createLibro(libro);

		model.addAttribute("libros", libroService.getAllLibros());

		return "listLibros";
	}

	@GetMapping("/delete/{id}")
	public String eliminarLibro(@PathVariable Long id, Model model) {

		libroService.deleteLibro(id);

		model.addAttribute("libros", libroService.getAllLibros());
		model.addAttribute("generos", generoService.getAllGeneros());
		return "listLibros";
	}

	@GetMapping("/reporte/libros-ultimos-seis-meses-por-genero")
	public String mostrarReportePorGenero(Model model) {

		List<Object[]> resultados = libroService.contarLibrosUltimosSeisMesesPorGenero();
		model.addAttribute("resultados", resultados);

		return "reporte";
	}

	@GetMapping("/reporte")
	public void report(HttpServletResponse response, Model model) throws JRException, IOException {

		// 1. Acceder al reporte
		InputStream jasperStream = this.getClass().getResourceAsStream("/reportes/reporte.jasper");

		// 2. Preparadar los datos
		Map<String, Object> params = new HashMap<>();
		params.put("usuario", "Jorge Ventura");

		List<Object[]> resultados = libroService.contarLibrosUltimosSeisMesesPorGenero();
		List<Reporte> listaReporte = new ArrayList<>();
		
		for (Object[] resultado : resultados) {
		    Reporte reporte = new Reporte();
		    reporte.setNombreGenero((String) resultado[0]);
		    reporte.setCantidadLibros((Long) resultado[1]);
		    listaReporte.add(reporte);
		}
		model.addAttribute("reportes", listaReporte);
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listaReporte);

		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);

		// 3. Configuracion

		response.setContentType("aplication/x-pdf");
		response.setHeader("Content-disposition", "filename=reporte.pdf");

		// 4. Exportar reporte
		final OutputStream outputStream = response.getOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
	}

}
