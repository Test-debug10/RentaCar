package com.rentacar.ms_evaluaciones.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "evaluaciones")
@Data
public class Evaluacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long reservaId;

    @Column(nullable = false)
    private Integer calificacion; // Escala del 1 al 5

    @Column(nullable = false, length = 500)
    private String comentario;

    @Column(nullable = false)
    private LocalDate fechaEvaluacion;
}