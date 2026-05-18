package com.evaluacion_back.permisos.controller;

/**
 *
 * @author SantiagoDC
 */

import com.evaluacion_back.permisos.entity.Usuario;
import com.evaluacion_back.permisos.repository.UsuarioRepository; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository; 

    //listar usuarios
    @GetMapping
    public List<Usuario> listar() {
        return usuarioRepository.findAll(); 
    }

    //crear usuario
    @PostMapping
    public Usuario crear(@RequestBody Usuario usuario) {
        return usuarioRepository.save(usuario); 
    }
}