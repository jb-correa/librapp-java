package com.balanza.app.controladores;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.balanza.app.entidades.Ejercicio;
import com.balanza.app.entidades.SesionEjercicio;
import com.balanza.app.errores.ErrorServicio;
import com.balanza.app.service.EjercicioService;
import com.balanza.app.service.SesionEjercicioService;

@Controller
@RequestMapping("/sesion-de-ejercicio")
public class SesionEjercicioControlador {
	
	@Autowired
	private SesionEjercicioService sesionEjServ;
	
	@Autowired
	private EjercicioService ejercicioServ;
	
	
	
	@GetMapping("/crear")
	public String crear(ModelMap modelo) {
		List<Ejercicio> ejercicios=ejercicioServ.listar();
		
		List<String> tiempos= new ArrayList<String>(Arrays.asList("15 minutos", "20 minutos", "25 minutos", "30 minutos",
				"35 minutos", "40 minutos", "45 minutos", "50 minutos", "55 minutos", "1 hora", "1 hora, 5 minutos", "1 hora, 10 minutos",
				"1 hora, 15 minutos", "1 hora, 20 minutos", "1 hora, 25 minutos","1 hora, 30 minutos", "1 hora, 35 minutos", 
				"1 hora, 40 minutos", "1 hora, 45 minutos", "1 hora, 50 minutos","1 hora, 55 minutos", "2 horas", 
				"2 horas, 15 minutos", "2 horas, 30 minutos", "2 horas, 45 minutos", "3 horas"));
		
		modelo.put("tiempos", tiempos);
		
		modelo.put("ejercicios", ejercicios);
		
		return "form-sesion-ejercicio";
	}
	
	@PostMapping ("/crear")
	public String guardar(ModelMap modelo, @RequestParam String idEjercicio, 
			@RequestParam String tiempo)throws ErrorServicio{
			
		try {
			sesionEjServ.crearSesionEjercicio(idEjercicio, tiempo);
			modelo.put("exito", "Los datos se guardaron con éxito!");
			return "form-sesion-ejercicio";
		} catch (ErrorServicio e) {
			
			modelo.put("error", "No se pudieron guardar los datos!");
			return "form-sesion-ejercicio";
		}
			
		}
		
	
	
	@GetMapping("/listar")
	public String listar(ModelMap modelo) {
		
		List<SesionEjercicio> sesiones= sesionEjServ.listarSesiones();
		
		modelo.put("sesiones", sesiones);
		
		return "";
		
	}
	
	

}
