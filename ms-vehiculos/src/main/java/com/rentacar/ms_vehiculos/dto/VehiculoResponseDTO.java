package com.rentacar.ms_vehiculos.dto;

import lombok.Data;

@Data
public class VehiculoResponseDTO {
    private Long id;
    private String marca;
    private String modelo;
    private String patente;
    private Integer anio;
    private Double precioPorDia;
    private Boolean disponible;
}