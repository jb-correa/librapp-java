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

import com.balanza.app.entidades.Comida;
import com.balanza.app.errores.ErrorServicio;
import com.balanza.app.service.ComidaService;
import com.balanza.app.service.PorcionService;

@Controller
@RequestMapping("/porcion")
public class PorcionControlador {
	
	@Autowired
	private PorcionService porcionServ;
	
	@Autowired
	private ComidaService comidaServ;	
	
	@GetMapping("/comidas-del-dia")
	public String crear(ModelMap modelo){
		
		List<String> cantidades =new ArrayList<String>(Arrays.asList("50 gramos", "100 gramos", "150 gramos", "200 gramos",
				"250 gramos", "300 gramos", "350 gramos", "400 gramos", "450 gramos", "500 gramos", "750 gramos", "1000 gramos",
				"1250 gramos", "1500 gramos", "100 cc", "150 cc", "200 cc", "250 cc", "300 cc", "350 cc", "400 cc", "450 cc", 
				"500 cc", "750 cc", "1000 cc", "1 unidad", "2 unidades", "3 unidades", "4 unidades", "5 unidades", "6 unidades",
				"7 unidades", "8 unidades", "9 unidades", "10 unidades"));
		List<Comida> comidas= comidaServ.listar();
		modelo.put("cantidades", cantidades);
		modelo.put("comidas", comidas);
		
		return "form-porcion";
	}
	
	@PostMapping("/comidas-del-dia")
	public String guardar(ModelMap modelo, @RequestParam String idComida, 
			@RequestParam String cantidad) throws ErrorServicio {
		
		try {
			porcionServ.crearPorcion(idComida, cantidad);
			modelo.put("exito", "Los datos se guardaron con éxito!");
			return "redirect: /porcion/comidas-del-dia";
		} catch (ErrorServicio e) {
			
			modelo.put("error", "No se pudieron guardar los datos!");
			return "form-porcion";
			
		}
	}
	
		
}
