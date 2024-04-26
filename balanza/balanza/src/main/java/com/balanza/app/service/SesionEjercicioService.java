package com.balanza.app.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.balanza.app.entidades.*;
import com.balanza.app.errores.ErrorServicio;
import com.balanza.app.repositorios.*;

@Service
public class SesionEjercicioService {

		@Autowired
		private SesionEjercicioRepositorio sesionEjercicioRepo;
		
		@Autowired
		private EjercicioRepositorio ejercicioRepo;
		
		@Transactional
		public void crearSesionEjercicio(String idEjercicio, String tiempo) 
		throws ErrorServicio{
			
			validar (idEjercicio, tiempo);
			
			Integer numero=convertirCantidad(tiempo);
			
			SesionEjercicio sesion= new SesionEjercicio();
			
			Ejercicio ej= ejercicioRepo.getById(idEjercicio);
			
			sesion.setEjercicio(ej);
			sesion.setTiempoDeEjercicio(numero);
			sesion.setCaloriasQuemadas(ej.getCaloriasQuemadasPorMinuto()*numero);
			
			sesionEjercicioRepo.save(sesion);
			
		}
		
		public void validar(String idEjercicio, String tiempo) throws ErrorServicio {
			
			if (idEjercicio.equalsIgnoreCase("") || idEjercicio.isEmpty()) {
				throw new ErrorServicio ("El tipo de ejercicio no puede estar vacío");
			}
			if(tiempo==null) {
				throw new ErrorServicio("La cantidad de tiempo del ejercicio no puede ser cero");
			}
			
		}
		
		public SesionEjercicio buscarSesion(String id) {
			
			SesionEjercicio sesion= sesionEjercicioRepo.getById(id);
			
			return sesion;
			
		}
		
		public List<SesionEjercicio> listarSesiones(){
			
			List<SesionEjercicio> sesiones=sesionEjercicioRepo.findAll();
			
			return sesiones;
			
		}
		
		@Transactional
		public void eliminarSesion(String id) {
			
			SesionEjercicio sesion= sesionEjercicioRepo.getById(id);
			
			sesionEjercicioRepo.delete(sesion);			
			
		}
		
		public Integer convertirCantidad(String cantidad) {
			
			
			String [] divididos=cantidad.split(" ");
			
			Integer minutos=0;
			Integer horas=0;
			Integer numero=0;
			
			for (String num: divididos) {
				//Chequeo que sean minutos
				if (num.contains("0")|| num.contains("5")) {
					minutos= Integer.parseInt("num");				
				}else {
					horas=Integer.parseInt("num");
					//Conversion de horas a minutos
					horas*=60;
				}
					
			}
			
			numero=horas+minutos;
			
			return numero;
			
		}
		
}
