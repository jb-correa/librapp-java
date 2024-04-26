package com.balanza.app.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.balanza.app.entidades.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, String>{
	
	@Query ("Select u From Usuario u Where u.email= :email")
	public Usuario buscarPorEmail(@Param ("email")String email);
	
	@Query ("Select u From Usuario u Where u.email= :id")
	public Usuario buscarPorId(@Param ("id")String email);
	
}
