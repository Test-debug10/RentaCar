package com.rentacar.ms_seguros.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SeguroRequestDTO {

    @NotNull(message = "El ID de la reserva es obligatorio")
    private Long reservaId;

    @NotBlank(message = "El tipo de seguro es obligatorio")
    private String tipoSeguro;

    @NotNull(message = "La cobertura máxima es obligatoria")
    @Min(value = 0, message = "La cobertura no puede ser negativa")
    private Double coberturaMaxima;

    @NotNull(message = "El costo adicional es obligatorio")
    @Min(value = 0, message = "El costo no puede ser negativo")
    private Double costoAdicional;
}