package com.balanza.app.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import com.balanza.app.entidades.*;

@Repository
public interface BalanzaRepositorio  extends JpaRepository<Balanza, String>{

}
