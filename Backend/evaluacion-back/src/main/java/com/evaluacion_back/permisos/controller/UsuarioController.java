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
// 🔹 Habilitamos CORS de forma explícita para el listado y guardado desde Angular
@CrossOrigin(origins = "http://localhost:4200")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository; 

    // Listar usuarios
    @GetMapping
    public List<Usuario> listar() {
        return usuarioRepository.findAll(); 
    }

    // Crear usuario
    @PostMapping
    public Usuario crear(@RequestBody Usuario usuario) {
        return usuarioRepository.save(usuario); 
    }

    // Actualizar estado de usuario o datos base
    @PutMapping("/{id}")
    public Usuario actualizar(@PathVariable Integer id, @RequestBody Usuario usuarioDetalles) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
        
        usuario.setNombre(usuarioDetalles.getNombre());
        usuario.setCorreo(usuarioDetalles.getCorreo());
        
        usuario.setActivo(usuarioDetalles.getActivo()); 
        
        return usuarioRepository.save(usuario);
    }
}