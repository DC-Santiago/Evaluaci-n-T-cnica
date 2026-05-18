package com.evaluacion_back.permisos.service;

/**
 *
 * @author SantiagoDC
 */
import com.evaluacion_back.permisos.entity.Modulo;
import com.evaluacion_back.permisos.entity.Permiso;
import com.evaluacion_back.permisos.entity.Usuario;
import com.evaluacion_back.permisos.exception.BusinessException;
import com.evaluacion_back.permisos.repository.ModuloRepository;
import com.evaluacion_back.permisos.repository.PermisoRepository;
import com.evaluacion_back.permisos.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermisoService {

    private final PermisoRepository permisoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ModuloRepository moduloRepository;

    //Crea permiso
    public Permiso asignarPermiso(Integer usuarioId, Integer moduloId, Boolean leer, Boolean escribir) {

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new BusinessException("Usuario no existe"));

        Modulo modulo = moduloRepository.findById(moduloId)
                .orElseThrow(() -> new BusinessException("Módulo no existe"));


        if (permisoRepository.existsByUsuarioIdAndModuloId(usuarioId, moduloId)) {
            throw new BusinessException("El permiso ya existe para este usuario y módulo");
        }

      
        if (escribir && !leer) {
            throw new BusinessException("No se puede escribir sin permiso de lectura");
        }

        Permiso permiso = Permiso.builder()
                .usuario(usuario)
                .modulo(modulo)
                .puedeLeer(leer)
                .puedeEscribir(escribir)
                .build();

        return permisoRepository.save(permiso);
    }

    // Obtiene permisos por usuario
    public List<Permiso> obtenerPermisosUsuario(Integer usuarioId) {

        if (!usuarioRepository.existsById(usuarioId)) {
            throw new BusinessException("Usuario no existe");
        }

        return permisoRepository.findPermisosConModuloYSistema(usuarioId);
    }

    //Actualiza permiso
    public Permiso actualizarPermiso(Integer permisoId, Boolean leer, Boolean escribir) {

        Permiso permiso = permisoRepository.findById(permisoId)
                .orElseThrow(() -> new BusinessException("Permiso no encontrado"));

        if (escribir && !leer) {
            throw new BusinessException("No se puede escribir sin lectura");
        }

        permiso.setPuedeLeer(leer);
        permiso.setPuedeEscribir(escribir);

        return permisoRepository.save(permiso);
    }

    //Eliminar permiso
    public void eliminarPermiso(Integer permisoId) {

        if (!permisoRepository.existsById(permisoId)) {
            throw new BusinessException("Permiso no existe");
        }

        permisoRepository.deleteById(permisoId);
    }
}
