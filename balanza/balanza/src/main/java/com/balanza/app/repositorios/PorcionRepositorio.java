package com.balanza.app.repositorios;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.balanza.app.entidades.Porcion;

@Repository
public interface PorcionRepositorio extends JpaRepository<Porcion, String>{

	@Query("Select p From Porcion i Where p.fecha= :fecha")
	public List<Porcion> buscarPorFecha(@Param("fecha") Date fecha);
	
	
}
