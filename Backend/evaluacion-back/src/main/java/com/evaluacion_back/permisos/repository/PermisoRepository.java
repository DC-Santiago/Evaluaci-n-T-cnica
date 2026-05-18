package com.evaluacion_back.permisos.repository;

/**
 *
 * @author Sadec
 */
import com.evaluacion_back.permisos.entity.Permiso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PermisoRepository extends JpaRepository<Permiso, Integer> {

    // Obtiene todos los permisos de un usuario
    List<Permiso> findByUsuarioId(Integer usuarioId);

    //  Busca permiso específico
    Optional<Permiso> findByUsuarioIdAndModuloId(Integer usuarioId, Integer moduloId);

    // Valida si ya existe permiso
    boolean existsByUsuarioIdAndModuloId(Integer usuarioId, Integer moduloId);
}