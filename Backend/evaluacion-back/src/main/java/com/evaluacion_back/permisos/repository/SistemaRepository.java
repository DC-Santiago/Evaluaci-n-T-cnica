package com.evaluacion_back.permisos.repository;

/**
 *
 * @author SantiagoDC
 */
import com.evaluacion_back.permisos.entity.Sistema;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SistemaRepository extends JpaRepository<Sistema, Integer> {
}
