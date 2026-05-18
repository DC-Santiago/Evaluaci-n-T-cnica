package com.evaluacion_back.permisos.controller;

/**
 *
 * @author SantiagoDC
 */
import com.evaluacion_back.permisos.dto.PermisoRequestDTO;
import com.evaluacion_back.permisos.dto.PermisoResponseDTO;
import com.evaluacion_back.permisos.entity.Permiso;
import com.evaluacion_back.permisos.service.PermisoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permisos")
@RequiredArgsConstructor
public class PermisoController {

    private final PermisoService permisoService;

    // ✅ Crear permiso
    @PostMapping
    public PermisoResponseDTO crear(@RequestBody PermisoRequestDTO request) {

        Permiso permiso = permisoService.asignarPermiso(
                request.getUsuarioId(),
                request.getModuloId(),
                request.getPuedeLeer(),
                request.getPuedeEscribir()
        );

        return mapToDTO(permiso);
    }

    // ✅ Obtener permisos por usuario
    @GetMapping("/usuario/{usuarioId}")
    public List<PermisoResponseDTO> obtenerPorUsuario(@PathVariable Integer usuarioId) {

        return permisoService.obtenerPermisosUsuario(usuarioId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ✅ Actualizar permiso
    @PutMapping("/{id}")
    public PermisoResponseDTO actualizar(
            @PathVariable Integer id,
            @RequestBody PermisoRequestDTO request) {

        Permiso permiso = permisoService.actualizarPermiso(
                id,
                request.getPuedeLeer(),
                request.getPuedeEscribir()
        );

        return mapToDTO(permiso);
    }

    // ❌ Eliminar permiso
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        permisoService.eliminarPermiso(id);
    }

    // 🔄 Mapper interno (simple y suficiente para prueba)
    private PermisoResponseDTO mapToDTO(Permiso p) {
        return PermisoResponseDTO.builder()
                .id(p.getId())
                .usuario(p.getUsuario().getNombre())
                .modulo(p.getModulo().getNombre())
                .sistema(p.getModulo().getSistema().getNombre())
                .puedeLeer(p.getPuedeLeer())
                .puedeEscribir(p.getPuedeEscribir())
                .build();
    }
}
