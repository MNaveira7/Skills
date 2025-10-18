package com.fct.clubs.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "rol", schema = "dbo")
public class RolEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol", nullable = false, unique = true)
    private Integer rolId;

    @Column(name = "nombre_rol", nullable = false, length = 50, unique = true)
    private String nombreRol;
}
