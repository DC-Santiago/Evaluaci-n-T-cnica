package com.evaluacion_back.permisos.service;

/**
 *
 * @author SantiagoDC
 */

import com.evaluacion_back.permisos.entity.Usuario;
import com.evaluacion_back.permisos.exception.BusinessException;
import com.evaluacion_back.permisos.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public Usuario crearUsuario(Usuario usuario) {

        // 🔥 Validación clave
        if (usuarioRepository.existsByCorreo(usuario.getCorreo())) {
            throw new BusinessException("El correo ya está registrado");
        }

        usuario.setActivo(true);

        return usuarioRepository.save(usuario);
    }

    public Usuario obtenerPorId(Integer id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Usuario no encontrado"));
    }
}
