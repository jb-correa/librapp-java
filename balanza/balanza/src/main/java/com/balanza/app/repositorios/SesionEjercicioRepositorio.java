package com.balanza.app.repositorios;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.balanza.app.entidades.*;

@Repository
public interface SesionEjercicioRepositorio extends JpaRepository<SesionEjercicio, String> {

	@Query("Select s From SesionEjercicio s Where s.fecha= :fecha")
	public SesionEjercicio buscarPorFecha(@Param("fecha") Date fecha);
	
}
