package com.evaluacion_back.permisos.dto;

/**
 *
 * @author SantiagoDC
 */
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PermisoResponseDTO {

    private Integer id;
    private String usuario;
    private String modulo;
    private String sistema;
    private Boolean puedeLeer;
    private Boolean puedeEscribir;
}
