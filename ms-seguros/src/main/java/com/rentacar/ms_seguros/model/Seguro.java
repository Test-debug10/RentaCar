package com.rentacar.ms_seguros.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "seguros")
@Data
public class Seguro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long reservaId;

    @Column(nullable = false, length = 100)
    private String tipoSeguro; // ej. COBERTURA_TOTAL, TERCEROS, ROBO

    @Column(nullable = false)
    private Double coberturaMaxima;

    @Column(nullable = false)
    private Double costoAdicional;
}