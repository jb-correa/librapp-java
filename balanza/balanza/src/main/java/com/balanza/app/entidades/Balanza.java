package com.balanza.app.entidades;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor 
	public class Balanza {
	
	@Id
	@GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	
	@OneToOne
	private Usuario usuario;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;
	
	@OneToOne
	private SesionEjercicio sesionEjercicio;
	
	@OneToOne
	private QuemaDiaria quemaDiaria;
	
	@OneToOne
	private Ingesta ingesta;
	
	private Integer resultadoBalanza;
	
}
