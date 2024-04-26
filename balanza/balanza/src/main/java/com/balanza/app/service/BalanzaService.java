package com.balanza.app.service;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.balanza.app.entidades.*;
import com.balanza.app.errores.ErrorServicio;
import com.balanza.app.repositorios.*;


@Service
public class BalanzaService {
		
	@Autowired
	private BalanzaRepositorio balanzaRepo;
	
	@Autowired
	private UsuarioRepositorio usuarioRepo;
	
	@Autowired
	private SesionEjercicioRepositorio sesionEjercicioRepo;
	
	@Autowired
	private QuemaDiariaRepositorio quemaDiariaRepo;
	
	private IngestaRepositorio ingestaRepo;
	
	@Transactional
	public void calcularBalanza(String idUsuario, String idSesionEjercicio, String idQuemaDiaria,
			String idIngesta) throws ErrorServicio{
		
		Balanza balanza= new Balanza();
		Usuario usuario = usuarioRepo.getById(idIngesta);
		SesionEjercicio sesionEjercicio=  sesionEjercicioRepo.getById(idSesionEjercicio);
		QuemaDiaria quemaDiaria= quemaDiariaRepo.getById(idIngesta);
		Ingesta ingesta= ingestaRepo.getById(idIngesta);
		
		validar(usuario, ingesta, quemaDiaria);
		
		balanza.setUsuario(usuario);
		balanza.setSesionEjercicio(sesionEjercicio);
		balanza.setQuemaDiaria(quemaDiaria);
		balanza.setIngesta(ingesta);
		
		Integer calorias= ingesta.getTotalCaloriasDia()-(quemaDiaria.getQuemaDiaria()+
				sesionEjercicio.getCaloriasQuemadas());
		
		balanza.setResultadoBalanza(calorias);
		
		balanzaRepo.save(balanza);
		
	}
	
	public void validar(Usuario usuario, Ingesta ingesta, QuemaDiaria quemaDiaria) 
			throws ErrorServicio{
			
		if (usuario==null) {
			throw new ErrorServicio ("El usuario no puede estar vacío");
		}
		if (ingesta==null) {
			throw new ErrorServicio ("Se deben ingresar los alimentos del día");
		}
		if (quemaDiaria==null) {
			throw new ErrorServicio("La quema diaria no puede estar vacía");
		}
		
	}
	public Balanza buscarBalanza(String idBalanza) {
		
		Balanza balanza = balanzaRepo.getById(idBalanza);
		
		return balanza;
		
	}
	
	public List<Balanza> mostrarHistorial(){
		
		List<Balanza> historial=balanzaRepo.findAll();
		
		return historial;
		
	}
	
}
