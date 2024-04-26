package com.balanza.app.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.balanza.app.entidades.Comida;
import com.balanza.app.errores.ErrorServicio;
import com.balanza.app.service.ComidaService;

@Controller
@RequestMapping("/comida")
public class ComidaControlador {

	@Autowired
	private ComidaService comidaServ;
	
	@GetMapping("/crear")
	public String crearComida() {
		
		return "";
	}
	
	@PostMapping("/crear")
	public String guardar (ModelMap modelo, @RequestParam String nombre, 
			@RequestParam Integer calorias) throws ErrorServicio{
		
		try {
			
			comidaServ.crearComida(nombre, calorias);
			
			modelo.put("exito", "Los datos se guardaron con éxito!");
			
			return "";
			
		}catch (ErrorServicio e) {
			
			modelo.put("error", "No pudieron guardarse los datos");
			
			return "";
		}
		
		
	}
	
	@GetMapping("/listar")
	public String listar(ModelMap modelo) {
		
		List<Comida> comidas= comidaServ.listar();
		
		modelo.put("comidas", comidas);
		
		return "";
	}
	
	@GetMapping("/listar/eliminar")
	public String eliminar(String id) {
		
		comidaServ.eliminar(id);
		
		return"";
	}
	
}
