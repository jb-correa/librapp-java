package com.balanza.app.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.balanza.app.entidades.*;
import com.balanza.app.errores.ErrorServicio;
import com.balanza.app.service.EjercicioService;

@Controller
@RequestMapping("/ejercicio")
public class EjercicioControlador {
	
	@Autowired
	private EjercicioService ejercicioServ;
	
	@GetMapping("/crear")
	public String crear() {
		return "form-ejercicio";
	}
	
	@PostMapping("/crear")
	public String guardar(ModelMap modelo, @RequestParam String nombreEjercicio, @RequestParam Integer
			caloriasQuemadasPorHora) throws ErrorServicio{
		
		try {
			ejercicioServ.crear(nombreEjercicio, caloriasQuemadasPorHora);
			modelo.put("exito", "Los datos se guardaron con éxito!");
			return "form-ejercicio";
		} catch (ErrorServicio e) {
			
			modelo.put("error", "No se pudieron guardar los datos!");
			return "form-ejercicio";
			
		}
		
	}
	
	@GetMapping ("/listar")
	public String listar(ModelMap modelo) {
		
		List<Ejercicio> ejercicios= ejercicioServ.listar();
		
		modelo.put("ejercicios", ejercicios);
		
		return "";
	}
	
	@GetMapping ("/listar/eliminar")
	public String eliminar(String id) {
		
		ejercicioServ.eliminarEjercicio(id);
		
		return "";
	}
	
	
	
}
