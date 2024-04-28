package com.balanza.app.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.balanza.app.entidades.*;
import com.balanza.app.errores.ErrorServicio;
import com.balanza.app.repositorios.EjercicioRepositorio;

@Service
public class EjercicioService {
	
		@Autowired
		private EjercicioRepositorio ejercicioRepo;
		
		@Transactional
		public void crear(String nombreEjercicio, Integer caloriasQuemadasPorHora) throws ErrorServicio{
			
			Ejercicio ejercicio= new Ejercicio();
			
			validar(nombreEjercicio, caloriasQuemadasPorHora);
			
			ejercicio.setNombreEjercicio(nombreEjercicio);
			ejercicio.setCaloriasQuemadasPorMinuto(caloriasQuemadasPorHora/60);
			
			ejercicioRepo.save(ejercicio);
			
		}
	
		public void validar(String nombreEjercicio, Integer caloriasQuemadasPorHora) throws ErrorServicio{
			
			if(nombreEjercicio==null|| nombreEjercicio.equalsIgnoreCase(""))
				throw new ErrorServicio ("El nombre no puede estar vacío");
			
			
			List<Ejercicio> ejercicios=ejercicioRepo.findAll();
			
			for (Ejercicio ejercicio: ejercicios) {
				if (ejercicio.getNombreEjercicio().equalsIgnoreCase(nombreEjercicio)) {
					throw new ErrorServicio("El tipo de ejercicio que estás intentando ingresar ya existe");
				}
			}
			
			if(caloriasQuemadasPorHora==0 || caloriasQuemadasPorHora==null) {
				throw new ErrorServicio ("La cantidad de calorías quemadas "
						+ "no puede ser cero");
			}
		}
			
			
		
		public List<Ejercicio> listar(){
			
			List<Ejercicio> ejercicios= ejercicioRepo.findAll();
			
			return ejercicios;
			
		}
		
		public Ejercicio buscarEjercicio(String id) {
			
			Ejercicio ej= ejercicioRepo.getById(id);
			
			return ej;
			
		}
		
		@Transactional
		public void eliminarEjercicio(String id) {
			
			Ejercicio ej= ejercicioRepo.getById(id);
			
			ejercicioRepo.delete(ej);
			
		}
		
		
		
}
