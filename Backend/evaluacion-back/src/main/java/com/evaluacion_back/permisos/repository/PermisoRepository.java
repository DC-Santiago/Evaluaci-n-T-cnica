package com.evaluacion_back.permisos.repository;

import com.evaluacion_back.permisos.entity.Permiso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PermisoRepository extends JpaRepository<Permiso, Integer> {

    // Obtiene todos los permisos de un usuario
    List<Permiso> findByUsuarioId(Integer usuarioId);

    // Busca permiso específico
    Optional<Permiso> findByUsuarioIdAndModuloId(Integer usuarioId, Integer moduloId);

    // Valida si ya existe permiso
    boolean existsByUsuarioIdAndModuloId(Integer usuarioId, Integer moduloId);

    // Query optimizada
    @Query("""
        SELECT p FROM Permiso p
        JOIN FETCH p.modulo m
        JOIN FETCH m.sistema
        WHERE p.usuario.id = :usuarioId
    """)
    List<Permiso> findPermisosConModuloYSistema(@Param("usuarioId") Integer usuarioId);
    
    List<Permiso> findByModuloId(Integer moduloId);
}