package com.balanza.app.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.balanza.app.entidades.Comida;
import com.balanza.app.errores.ErrorServicio;
import com.balanza.app.repositorios.ComidaRepositorio;

@Service
public class ComidaService {
	
	@Autowired
	private ComidaRepositorio comidaRep;
	
	@Transactional
	public void crearComida(String nombre, Integer calorias) throws ErrorServicio{
		
		validar(nombre, calorias);
		
		Comida comida= new Comida();
		
		comida.setNombre(nombre);
		comida.setCalorías(calorias);
		
		comidaRep.save(comida);
		
	}
	
	
	public void validar(String nombre, Integer calorias) throws ErrorServicio{
		
		if (nombre.isEmpty()|| nombre.equals("") || nombre==null) {
			throw new ErrorServicio("El nombre no puede estar vacío");
		}
		
		List<Comida> comidasIngresadas=comidaRep.findAll();
		
		for (Comida comida: comidasIngresadas) {
			
			if(comida.getNombre().equalsIgnoreCase(nombre)) {
				throw new ErrorServicio("La comida que estás intentando ingresar y existe");
			}
			
		}
		
		if (calorias==null || calorias==0) {
			throw new ErrorServicio("La cantidad de calorías no puede estar vacía");
		}
		
	}
	
	public List<Comida> listar(){
		
		List<Comida> comidas=comidaRep.findAll();
		
		return comidas;
		
	}
	
	public Comida buscarComida(String id) {
		
		Comida comida =comidaRep.getById(id);
		
		return comida;
	}
	
	@Transactional
	public void eliminar(String id) {
		
		Comida comida= comidaRep.getById(id);
		
		comidaRep.delete(comida);
		
	}
	
	
	
		
}
