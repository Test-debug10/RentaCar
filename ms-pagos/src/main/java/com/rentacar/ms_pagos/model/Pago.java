package com.rentacar.ms_pagos.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "pagos")
@Data
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long reservaId;

    @Column(nullable = false)
    private Double monto;

    @Column(nullable = false, length = 50)
    private String metodoPago; // ej. TARJETA_CREDITO, DEBITO, TRANSFERENCIA

    @Column(nullable = false, length = 20)
    private String estado; // ej. PENDIENTE, COMPLETADO, RECHAZADO

    @Column(nullable = false)
    private LocalDateTime fechaPago;
}