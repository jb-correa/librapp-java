package com.balanza.app.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.balanza.app.entidades.Balanza;
import com.balanza.app.errores.ErrorServicio;
import com.balanza.app.service.*;

@Controller
@RequestMapping("/balanza")
public class BalanzaControlador {
		
	@Autowired
	private BalanzaService balanzaServ;
	
	
	@GetMapping("/crear")
	public String crearBalanza(){
		return "";
	}
	
	@PostMapping("/crear")
	public String guardar (ModelMap modelo, @RequestParam String idUsuario, String idEjercicio,
			@RequestParam String  idQuemaDiaria, @RequestParam  String idIngesta) throws ErrorServicio{
		
		try {
			balanzaServ.calcularBalanza(idUsuario, idEjercicio, idQuemaDiaria, idIngesta);
			modelo.put("exito", "Los datos se guardaron con éxito!");
			return "";
		} catch (ErrorServicio e) {
			
			modelo.put("error", "No se pudieron guardar los datos!");
			return "";
			
		}
	
	}
	
	@GetMapping("/listar")
	public String listar(ModelMap modelo) {
		
		List<Balanza> historial= balanzaServ.mostrarHistorial();
		
		modelo.put("historial", historial);
		
		return "";
	}
	
	
}
