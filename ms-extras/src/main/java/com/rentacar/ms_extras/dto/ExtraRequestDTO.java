package com.rentacar.ms_extras.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ExtraRequestDTO {

    @NotBlank(message = "El nombre del accesorio es obligatorio")
    private String nombre;

    @NotBlank(message = "La descripción no puede estar vacía")
    private String descripcion;

    @NotNull(message = "El precio diario es obligatorio")
    @Min(value = 0, message = "El precio no puede ser negativo")
    private Double precioDiario;

    @NotNull(message = "El stock total es obligatorio")
    @Min(value = 1, message = "Debe registrar al menos 1 unidad en stock")
    private Integer stockTotal;
}