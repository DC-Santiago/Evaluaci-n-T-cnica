package com.evaluacion_back.permisos.entity;

/**
 *
 * @author SantiagoDC
 */
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "Sistema")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sistema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String nombre;

    private String descripcion;

    private Boolean activo;

    @OneToMany(mappedBy = "sistema", fetch = FetchType.LAZY)
    private List<Modulo> modulos;
}
