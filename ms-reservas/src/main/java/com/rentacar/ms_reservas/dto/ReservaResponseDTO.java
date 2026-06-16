package com.rentacar.ms_reservas.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ReservaResponseDTO {
    private Long id;
    private Long usuarioId;
    private Long vehiculoId;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Double montoTotal;
}