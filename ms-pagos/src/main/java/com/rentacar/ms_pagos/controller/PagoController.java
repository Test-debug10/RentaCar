package com.rentacar.ms_pagos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rentacar.ms_pagos.dto.PagoRequestDTO;
import com.rentacar.ms_pagos.dto.PagoResponseDTO;
import com.rentacar.ms_pagos.service.PagoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "Pagos V1", description = "API para la gestión de pagos")
@RestController
@RequestMapping("/api/v1/pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @Operation(summary = "Registrar un nuevo pago")
    @PostMapping
    public ResponseEntity<PagoResponseDTO> registrarPago(@Valid @RequestBody PagoRequestDTO dto) {
        log.info("Iniciando procesamiento de pago para la reserva ID: {}", dto.getReservaId());
        PagoResponseDTO nuevoPago = pagoService.procesarPago(dto);
        return new ResponseEntity<>(nuevoPago, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PagoResponseDTO>> listarPagos() {
        return ResponseEntity.ok(pagoService.obtenerTodosDTO());
    }
}