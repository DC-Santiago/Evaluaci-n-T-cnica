package com.evaluacion_back.permisos.repository;

/**
 *
 * @author SantiagoDC
 */
import com.evaluacion_back.permisos.entity.Modulo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModuloRepository extends JpaRepository<Modulo, Integer> {

    List<Modulo> findByCorreo(String correo);

    boolean existsByCorreo(String correo);
}