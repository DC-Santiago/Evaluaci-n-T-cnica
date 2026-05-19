package com.evaluacion_back.permisos.entity;

/**
 *
 * @author SantiagoDC
 */
import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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

    // permitiendo que Angular siga viendo los datos básicos del Sistema.
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sistema_id", nullable = false)
    @JsonIgnoreProperties("modulos") 
    private Sistema sistema;

    @JsonIgnore
    @OneToMany(mappedBy = "modulo", fetch = FetchType.LAZY)
    private List<Permiso> permisos;
}