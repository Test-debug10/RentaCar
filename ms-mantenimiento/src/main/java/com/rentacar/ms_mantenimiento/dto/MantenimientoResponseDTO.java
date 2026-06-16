package com.rentacar.ms_mantenimiento.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class MantenimientoResponseDTO {
    private Long id;
    private Long vehiculoId;
    private String tipo;
    private String descripcion;
    private LocalDate fechaIngreso;
    private Double costo;
}