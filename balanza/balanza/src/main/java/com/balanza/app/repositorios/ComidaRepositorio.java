package com.balanza.app.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.balanza.app.entidades.Comida;

@Repository
public interface ComidaRepositorio extends JpaRepository<Comida, String> {

}
