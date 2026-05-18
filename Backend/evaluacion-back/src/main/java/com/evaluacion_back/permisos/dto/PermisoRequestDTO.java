package com.evaluacion_back.permisos.dto;

/**
 * @author SantiagoDC
 */
import lombok.Data;

@Data
public class PermisoRequestDTO {

    private Integer usuarioId;
    private Integer moduloId;
    private Boolean puedeLeer;
    private Boolean puedeEscribir;

    // Métodos explícitos por si el IDE se pone rejego con Lombok
    public Boolean getPuedeLeer() {
        return puedeLeer;
    }

    public void setPuedeLeer(Boolean puedeLeer) {
        this.puedeLeer = puedeLeer;
    }

    public Boolean getPuedeEscribir() {
        return puedeEscribir;
    }

    public void setPuedeEscribir(Boolean puedeEscribir) {
        this.puedeEscribir = puedeEscribir;
    }
}