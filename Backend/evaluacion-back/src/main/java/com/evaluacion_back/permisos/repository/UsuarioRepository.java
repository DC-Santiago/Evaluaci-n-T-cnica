package com.evaluacion_back.permisos.repository;

/**
 *
 * @author SantiagoDC
 */
import com.evaluacion_back.permisos.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByCorreo(String correo);

    boolean existsByCorreo(String correo);
}
