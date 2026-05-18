package com.evaluacion_back.permisos.entity;

/**
 *
 * @author SantiagoDC
 */
import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Modulo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Modulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;

    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sistema_id", nullable = false)
    private Sistema sistema;


    @JsonIgnore
    @OneToMany(mappedBy = "modulo", fetch = FetchType.LAZY)
    private List<Permiso> permisos;
}
