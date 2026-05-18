package com.evaluacion_back.permisos.entity;

/**
 *
 * @author SantiagoDC
 */
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "Permiso",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"usuario_id", "modulo_id"})
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Permiso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modulo_id", nullable = false)
    private Modulo modulo;

    @Column(name = "puede_leer")
    private Boolean puedeLeer;

    @Column(name = "puede_escribir")
    private Boolean puedeEscribir;
}
