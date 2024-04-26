package com.balanza.app.entidades;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Usuario {
	
	@Id
	@GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	
	private String nombre;
	
	private String email;
	
	private String password;
	

	private Integer edad;
	
	private Integer altura;
	
	private Integer peso;
	
	private boolean activo;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date alta;
	
	@OneToOne
	private Balanza balanza;
	
	@ManyToMany
	private List<Balanza> historial;
	
	
}
