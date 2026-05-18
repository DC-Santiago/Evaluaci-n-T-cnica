package com.evaluacion_back.permisos.repository;

/**
 *
 * @author SantiagoDC
 */
import com.evaluacion_back.permisos.entity.Modulo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ModuloRepository extends JpaRepository<Modulo, Integer> {

    // Si en un futuro necesitas buscar un módulo por su nombre
    Optional<Modulo> findByNombre(String nombre);

    // Si necesitas validar si un módulo ya existe por su nombre
    boolean existsByNombre(String nombre);
}