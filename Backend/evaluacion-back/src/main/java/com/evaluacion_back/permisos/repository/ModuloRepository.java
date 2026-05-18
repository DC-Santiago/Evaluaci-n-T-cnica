package com.evaluacion_back.permisos.repository;

/**
 *
 * @author SantiagoDC
 */
import com.evaluacion_back.permisos.entity.Modulo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ModuloRepository extends JpaRepository<Modulo, Integer> {

   
    Optional<Modulo> findByNombre(String nombre);

   
    boolean existsByNombre(String nombre);
}