package com.evaluacion_back.permisos.dto;

/**
 *
 * @author SantiagoDC
 */
import lombok.Data;

@Data
public class PermisoRequestDTO {

    private Integer usuarioId;
    private Integer moduloId;
    private Boolean puedeLeer;
    private Boolean puedeEscribir;
}