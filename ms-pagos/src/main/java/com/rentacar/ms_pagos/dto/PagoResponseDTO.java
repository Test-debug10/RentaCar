package com.rentacar.ms_pagos.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PagoResponseDTO {
    private Long id;
    private Long reservaId;
    private Double monto;
    private String metodoPago;
    private String estado;
    private LocalDateTime fechaPago;
}