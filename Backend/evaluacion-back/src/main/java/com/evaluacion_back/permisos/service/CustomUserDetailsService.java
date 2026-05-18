package com.evaluacion_back.permisos.service;

/**
 *
 * @author SantiagoDC
 */

import com.evaluacion_back.permisos.entity.Usuario;
import com.evaluacion_back.permisos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
     
        Usuario usuario = usuarioRepository.findByCorreo(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el correo: " + username));

        
        return new User(
                usuario.getCorreo(),      
                usuario.getContrasena(),  
                Collections.emptyList()   
        );
    }
}