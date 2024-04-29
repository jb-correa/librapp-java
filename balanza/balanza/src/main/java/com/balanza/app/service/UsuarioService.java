package com.balanza.app.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.balanza.app.entidades.Usuario;
import com.balanza.app.errores.ErrorServicio;
import com.balanza.app.repositorios.UsuarioRepositorio;


@Service
public class UsuarioService implements UserDetailsService{

	@Autowired
	private UsuarioRepositorio usuarioRep;
	
	@Autowired
	private EmailNotifications emailNotification;

	
	@Transactional
	public void guardarUsuario(String nombre, String email, String password, Integer edad,
			Integer altura, Integer peso) throws ErrorServicio{
			validar(nombre, email, password, edad, altura, peso);
			
			Usuario usuario= new Usuario();
			
			usuario.setNombre(nombre);
			usuario.setEmail(email);
			usuario.setPassword(new BCryptPasswordEncoder().encode(password));
			usuario.setEdad(edad);
			usuario.setAltura(altura);
			usuario.setPeso(peso);
			usuario.setAlta(new Date());
			

			//EmailNotifications emailNotification= new EmailNotifications();
			emailNotification.sendEmail("Gracias por registrarte", "Bienvenido", email);

			usuarioRep.save(usuario);
		
	}
	
	public void validar(String nombre, String email, String password, Integer edad,
			Integer altura, Integer peso) throws ErrorServicio{
		
		if (nombre==null || nombre.equals("")|| nombre.isEmpty()) {
			throw new ErrorServicio("El nombre no puede estar vacío");
		}
		if (email==null || email.equals("")|| email.isEmpty()) {
			throw new ErrorServicio("El email no puede estar vacío");
		}
		if (password==null || password.equals("")|| password.isEmpty()) {
			throw new ErrorServicio("La contraseña no puede estar vacía");
		}
		if (edad==null || edad==0) {
			throw new ErrorServicio("La edad no puede estar vacía");
		}
		if (edad<18) {
			throw new ErrorServicio("Lo sentimos! Debes ser mayor de 18 para usar esta aplicación");
		}
		if (altura==null || altura==0) {
			throw new ErrorServicio("La altura no puede estar vacía");
		}
		if (peso==null || peso==0) {
			throw new ErrorServicio("El peso no puede estar vacío");
		}
	}
	
	public List<Usuario> listarUsuarios(){
		
		List<Usuario> usuarios=usuarioRep.findAll();
		
		return usuarios;
		
	}
	
	public Usuario buscarUsuario(String id) {
		
		Usuario usuario= usuarioRep.getById(id);
		
		return usuario;
		
	}
	
	@Transactional
	public void eliminarUsuario(String id) {
		
		Usuario usuario= usuarioRep.getById(id);
		
		usuarioRep.delete(usuario);
		
	}
	
	public void modificarPeso(String id, Integer peso) {
		
		Usuario usuario=usuarioRep.getById(id);
		
		usuario.setPeso(peso);
		
		usuarioRep.save(usuario);
		
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Usuario usuario = usuarioRep.buscarPorEmail(email);
		
		if (usuario != null) {
			List<GrantedAuthority> permisos = new ArrayList<>();
			GrantedAuthority p = new SimpleGrantedAuthority("ROLE_USUARIO_REGISTRADO");
			permisos.add(p);
			
			User user= new User(usuario.getEmail(), usuario.getPassword(), permisos);
			
			return user;
		}
		return null;

	}

}
	
	

