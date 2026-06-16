package com.rentacar.ms_pagos.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PagoRequestDTO {

    @NotNull(message = "El ID de la reserva es obligatorio")
    private Long reservaId;

    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "1.0", message = "El monto debe ser mayor a 0")
    private Double monto;

    @NotBlank(message = "El método de pago es obligatorio")
    private String metodoPago;
}