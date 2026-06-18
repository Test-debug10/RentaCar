package com.rentacar.ms_evaluaciones.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class EvaluacionResponseDTO {
    private Long id;
    private Long reservaId;
    private Integer calificacion;
    private String comentario;
    private LocalDate fechaEvaluacion;
}