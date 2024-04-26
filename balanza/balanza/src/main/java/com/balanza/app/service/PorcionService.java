package com.balanza.app.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.balanza.app.entidades.Comida;
import com.balanza.app.entidades.Porcion;
import com.balanza.app.errores.ErrorServicio;
import com.balanza.app.repositorios.PorcionRepositorio;

@Service
public class PorcionService {
	
	@Autowired
	private PorcionRepositorio porcionRepo;
	
	@Autowired
	private ComidaService comidaServ;
	
	//El método persiste y devuelve objeto para el front
	@Transactional
	public void crearPorcion(String idComida, String cantidad) throws ErrorServicio {

		Date fecha = new Date();

		Comida comida = comidaServ.buscarComida(idComida);

		validar(fecha, comida, cantidad);

		Integer numero = convertirCantidad(cantidad);

		Porcion porcion = new Porcion();

		porcion.setComida(comida);
		porcion.setCantidad(numero);
		porcion.setTotalCaloriasPorcion(comida.getCalorías() * numero);
	}

	public Porcion crearPorcion(Comida comida, Integer cantidad) throws ErrorServicio{
				
		Porcion porcion= new Porcion();
		
		porcion.setComida(comida);
		porcion.setCantidad(cantidad);
		porcion.setTotalCaloriasPorcion(comida.getCaloriasPorUnidad()*cantidad);
>
		
		porcionRepo.save(porcion);
		
		return porcion;
		
	}
	
	public void validar(Date fecha, Comida comida, String cantidad) throws ErrorServicio{
		
		if (comida== null) {
			throw new ErrorServicio ("La comida no puede estar vacía");
		}
		if (cantidad==null ) {
			throw new ErrorServicio ("La cantidad no puede ser cero");
		}
		if (fecha==null) {
			throw new ErrorServicio ("La fecha no puede estar vacía");
		}
		
	}
	
	public List<Porcion> buscarPorFecha(Date fecha){
		
		List<Porcion> ingestaDiaria=porcionRepo.buscarPorFecha(fecha);
		
		return ingestaDiaria;
		
	}
	
	public Integer convertirCantidad(String cantidad) {
	
		
		String [] divididos=cantidad.split(" ");
		
		Integer numero=0;
		
		for (String num: divididos) {
			if (num.contains("0") || num.contains("1") || num.contains("2")|| num.contains("3")|| num.contains("4")|| num.contains("5")
					|| num.contains("6")|| num.contains("7")|| num.contains("8")|| num.contains("9")) {
				numero=Integer.parseInt(cantidad);
			}
		}
		
		return numero;
		
	}
	
	public void eliminarPorcion(String idPorcion) {
		
		Porcion porcion= porcionRepo.getById(idPorcion);
		
		porcionRepo.delete(porcion);
		
	}
	
	
	
}
