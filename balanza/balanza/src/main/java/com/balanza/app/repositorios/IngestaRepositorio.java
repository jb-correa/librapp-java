package com.balanza.app.repositorios;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.balanza.app.entidades.Ingesta;

@Repository
public interface IngestaRepositorio extends JpaRepository<Ingesta, String>{

	@Query("Select i From Ingesta i Where i.fecha.month= :fecha.month AND i.fecha.day= :fecha.day")
	public Ingesta buscarPorFecha(@Param("fecha") Date fecha);
	
	
}
