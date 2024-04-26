package com.balanza.app.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.balanza.app.entidades.*;
import com.balanza.app.errores.ErrorServicio;
import com.balanza.app.repositorios.IngestaRepositorio;

@Service
public class IngestaService {
		
	@Autowired
	private IngestaRepositorio ingestaRepo;
		
	@Transactional
	public void guardaIngestaDiaria(List<Porcion> ingestaDiaria) throws ErrorServicio{
		
		Ingesta ingesta= new Ingesta();
		

		int calorias=0;
		
		
		for (Porcion porcion: ingestaDiaria) {
			
			calorias+=porcion.getTotalCaloriasPorcion();
			
		}
		double totalCaloriasDia=calorias;
		
		ingesta.setIngestaDiaria(ingestaDiaria);
		ingesta.setTotalCaloriasDia((double) calorias);

		ingesta.setIngestaDiaria(ingestaDiaria);
		ingesta.setTotalCaloriasDia(totalCaloriasDia);
		ingesta.setFecha((java.sql.Date) new Date());

		
		ingestaRepo.save(ingesta);
		
	}
	
	
	public List<Ingesta> listarIngestasDiarias(){
		
		List<Ingesta> ingestasDiarias= ingestaRepo.findAll();
		
		return ingestasDiarias;
	}
	
	public Ingesta buscarIngesta(String id) {
		
		Ingesta ingesta=ingestaRepo.getById(id);
		
		return ingesta;
		
	}
	
	@Transactional
	public void eliminarIngesta(String id) {
		
		Ingesta ingesta= ingestaRepo.getById(id);
		
		ingestaRepo.delete(ingesta);
		
	}
	
	
}
