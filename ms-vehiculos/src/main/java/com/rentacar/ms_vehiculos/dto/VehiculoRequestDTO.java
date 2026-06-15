package com.rentacar.ms_vehiculos.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VehiculoRequestDTO {

    @NotBlank(message = "La marca no puede estar vacía")
    private String marca;

    @NotBlank(message = "El modelo no puede estar vacío")
    private String modelo;

    @NotBlank(message = "La patente es obligatoria")
    private String patente;

    @NotNull(message = "El año es obligatorio")
    @Min(value = 2010, message = "El vehículo debe ser del año 2010 en adelante")
    private Integer anio;

    @NotNull(message = "El precio por día es obligatorio")
    @Min(value = 1, message = "El precio debe ser mayor a 0")
    private Double precioPorDia;
}