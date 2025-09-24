package com.fct.clubs.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "localizacion")
public class LocalizacionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_localizacion", nullable = false)
    private Integer idLocalizacion;

    @Column(name = "provincia", nullable = false, length = 100)
    private String provincia;

    @Column(name = "municipio", nullable = false, length = 100)
    private String municipio;

    @Column(name = "codigo_postal", length = 10)
    private String codigoPostal;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "latitud", precision = 9, scale = 6)
    private BigDecimal latitud;

    @Column(name = "longitud", precision = 9, scale = 6)
    private BigDecimal longitud;
}
