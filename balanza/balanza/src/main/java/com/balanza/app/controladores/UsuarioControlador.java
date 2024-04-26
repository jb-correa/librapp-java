package com.balanza.app.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.balanza.app.entidades.Usuario;
import com.balanza.app.service.UsuarioService;

import java.util.List;

@Controller
@RequestMapping ("/usuario")
public class UsuarioControlador {
	
	@Autowired
	private UsuarioService usuarioServ;
	
		
	@GetMapping("/listar")
	public String listar(ModelMap modelo) {
		
		List<Usuario> usuarios= usuarioServ.listarUsuarios();
		
		modelo.put("usuarios", usuarios);
		
		return "list-usuario";
	}
	
		
	}
	
	

