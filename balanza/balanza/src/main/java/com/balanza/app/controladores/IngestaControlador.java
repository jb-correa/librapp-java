package com.balanza.app.controladores;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.balanza.app.entidades.Comida;
import com.balanza.app.entidades.Porcion;
import com.balanza.app.errores.ErrorServicio;
import com.balanza.app.service.ComidaService;
import com.balanza.app.service.IngestaService;
import com.balanza.app.service.PorcionService;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/comidas-del-dia")
public class IngestaControlador {
	
	@Autowired
	private IngestaService ingestaServ;
	
	@Autowired
	private ComidaService comidaServ;
	
	@Autowired
	private PorcionService porcionServ;
	
	

	@GetMapping("/guardar")
	public String guardar(ModelMap modelo) {
		List<Porcion> ingestaDiaria=porcionServ.buscarPorFecha(new Date());	
		modelo.put("ingestaDiaria", ingestaDiaria);
		
		return "";
	}
	
	@PostMapping("/guardar")
	public String guardado() throws ErrorServicio {
		List<Porcion> ingestaDiaria=porcionServ.buscarPorFecha(new Date());	
		try {
			
			ingestaServ.guardaIngestaDiaria(ingestaDiaria);
		} catch (ErrorServicio e) {
			throw new ErrorServicio ("No se pudieron guardar las comidas del hoy");
		}
		
		return "";
	}
	
	
	/**
	 * @param request
	 * @param modelo
	 * @param idComida
	 * @param cantidad
	 * @return
	 * @throws ErrorServicio
	 */
	@PostMapping("/guardar-porcion")
	public String guardar(HttpSession request, ModelMap modelo, @RequestParam String idComida,
						  @RequestParam String cantidad) throws ErrorServicio {

		try {

			Comida comida = comidaServ.buscarComida(idComida);

			//porcionServ.validar(comida, cantidad);

			Integer numero = porcionServ.convertirCantidad(cantidad.toUpperCase());

			Porcion porcion = porcionServ.crearPorcion(comida, numero);

			if (request.getAttribute("ingestaDiaria") != null) {
				List<Porcion> ingestaDiaria = new ArrayList<>((List) request.getAttribute("ingestaDiaria"));
				ingestaDiaria.add(porcion);
				request.setAttribute("ingestDiaria", ingestaDiaria);
			} else {
				List<Porcion> ingestaDiaria = new ArrayList<>();
				ingestaDiaria.add(porcion);
				request.setAttribute("ingestaDiaria", ingestaDiaria);

			}

			modelo.put("exito", "Los datos se guardaron con éxito!");
			return "/form-porcion";
		} catch (ErrorServicio e) {

			modelo.put("error", "No se pudieron guardar los datos!");
			return "form-porcion";

		}}


		@GetMapping("/guardar")
		public String guardar (HttpSession request, ModelMap model){

			List<Porcion> ingestaDiaria = new ArrayList<>((List) request.getAttribute("ingestaDiaria"));

			Double totalCaloriasDia = 0.0;

			for (Porcion porcio : ingestaDiaria) {

				totalCaloriasDia += porcio.getTotalCaloriasPorcion();

			}

			model.addAttribute("ingestaDiaria", ingestaDiaria);
			model.addAttribute("totalCaloriasDia", totalCaloriasDia);


			return "form-ingesta";
		}
		;

		@PostMapping ("/guardar")
		public  String guardado (HttpSession request, ModelMap model) throws ErrorServicio {

			try {
				
				@SuppressWarnings("unchecked")
				List<Porcion> ingestaDiaria = new ArrayList<Porcion>((List<Porcion>) request.getAttribute("ingestaDiaria"));

				ingestaServ.guardaIngestaDiaria(ingestaDiaria);
				model.put("exito", "Se cargaron con éxito las comidas del día");
				return "form-sesion-ejercicio";
			} catch (ErrorServicio e) {
				model.put("error", "No se pudieron guardar las comidas del día");
				return "form-ingesta";
			}


		}

		@GetMapping("/guardar/eliminar")
		public String eliminar (String id){

			porcionServ.eliminarPorcion(id);

			return "form-ingesta";
		}

		//Recuperar historial para este método
		@GetMapping("/historial")
		public String listar (ModelMap modelo){

			//List<Ingesta> historial = ingestaServ.historial();

			//modelo.put("historial", historial);

			return "";

		}


	

	@GetMapping("/guardar-porcion")
	public String crear(ModelMap modelo) {

		List<String> cantidades = new ArrayList<String>(Arrays.asList("50 gramos", "100 gramos", "150 gramos", "200 gramos",
				"250 gramos", "300 gramos", "350 gramos", "400 gramos", "450 gramos", "500 gramos", "750 gramos", "1000 gramos",
				"1250 gramos", "1500 gramos", "50 cc", "100 cc", "150 cc", "200 cc", "250 cc", "300 cc", "350 cc", "400 cc", "450 cc",
				"500 cc", "750 cc", "1000 cc", "1 unidad", "2 unidades", "3 unidades", "4 unidades", "5 unidades", "6 unidades",
				"7 unidades", "8 unidades", "9 unidades", "Media", "Medio", "Una y media", "Dos y media", "Tres y media",
				"Cuatro y media", "Cinco y media", "Seis y media", "Siete y media", "Medio", "Uno y medio", "Dos y medio",
				"Tres y medio", "Cuatro y medio", "Cinco y medio", "Seis y medio", "Siete y medio"));
		List<Comida> comidas = comidaServ.listar();
		modelo.put("cantidades", cantidades);
		modelo.put("comidas", comidas);

		return "form-porcion";
	}
}


	

	
	
	

