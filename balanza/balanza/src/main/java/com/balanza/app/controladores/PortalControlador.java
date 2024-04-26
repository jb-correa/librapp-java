package com.balanza.app.controladores;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.balanza.app.errores.ErrorServicio;
import com.balanza.app.service.UsuarioService;

@Controller
@RequestMapping("/")
public class PortalControlador {
	
	@Autowired
	private UsuarioService usuarioServ;
	
	@GetMapping ("/")
	public String index() {
		return "landing";
	}
	
	@PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
	@GetMapping("/inicio")
	public String inicio() {
		return "index";
	}
	
	
	@GetMapping("/login")
	public String login(HttpSession session, Authentication usuario, ModelMap modelo, @RequestParam(required = false) String error) {
		try {
			if (usuario.getName() != null) {
				return "redirect:/inicio";
			} else {
				
				if (error != null && !error.isEmpty()) {
					modelo.put("error", "La dirección de mail o la contraseña que ingresó son incorrectas.");
				}
				return "login";
			}
			
		} catch (Exception e) {
			if (error != null && !error.isEmpty()) {
				modelo.put("error", "La dirección de mail o la contraseña que ingresó son incorrectas.");
			}
			return "index";
		}
	}
	
	
	@GetMapping("/registro")
	public String registro() {
		return "form-usuario";
	}
	
	@PostMapping ("/registro")
	public String registrar(ModelMap modelo, @RequestParam String nombre, 
			@RequestParam String email, @RequestParam String password, @RequestParam
			Integer edad, @RequestParam Integer altura, @RequestParam Integer peso){
		
		try {
			usuarioServ.guardarUsuario(nombre, email, password, edad, altura, peso);
			modelo.put("exito", "El usuario se guardó correctamente");
			return "form-usuario";
		} catch (ErrorServicio e) {
			modelo.put("error", "No se pudo guardar el usuario");
			return "form-usuario";
		}
	}

	
	@GetMapping("/logout")
	public String cerrarSesion() {
		
		return "logout";
		}
	}
