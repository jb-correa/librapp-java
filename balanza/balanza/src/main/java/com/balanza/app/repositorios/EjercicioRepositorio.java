package com.balanza.app.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.balanza.app.entidades.Ejercicio;


@Repository
public interface EjercicioRepositorio extends JpaRepository<Ejercicio, String>{

}
