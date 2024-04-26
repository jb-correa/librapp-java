package com.balanza.app.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.balanza.app.entidades.*;
import com.balanza.app.errores.ErrorServicio;
import com.balanza.app.repositorios.QuemaDiariaRepositorio;
import com.balanza.app.repositorios.UsuarioRepositorio;

@Service
public class QuemaDiariaService {
	
	@Autowired
	private QuemaDiariaRepositorio quemaDiariaRepo;
	
	@Autowired
	private UsuarioRepositorio usuarioRepo;
	
	@Transactional
	public void calcular(String idUsuario)throws ErrorServicio {
		
		validar(idUsuario);
		
		QuemaDiaria QD= new QuemaDiaria();
		
		Usuario usuario=usuarioRepo.getById(idUsuario);

		Integer quemaDiaria= usuario.getPeso()*1*24; //Una caloría por hora por peso 
		
		quemaDiaria= (quemaDiaria*20/100)+quemaDiaria;   //Aproximación de actividades cotidianas
		
		QD.setUsuario(usuario);
		
		QD.setQuemaDiaria(quemaDiaria);
		
		quemaDiariaRepo.save(QD);
		
	}
	
	public void validar(String idUsuario) throws ErrorServicio{
		
		if (idUsuario.equalsIgnoreCase("") || idUsuario.isBlank()) {
			throw new ErrorServicio ("El usuario no puede estar vacío");
		}
		
	}
	
	
}
