package com.rentacar.ms_pagos.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rentacar.ms_pagos.dto.PagoRequestDTO;
import com.rentacar.ms_pagos.dto.PagoResponseDTO;
import com.rentacar.ms_pagos.model.Pago;
import com.rentacar.ms_pagos.repository.PagoRepository;

@Service
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    public PagoResponseDTO procesarPago(PagoRequestDTO dto) {
        Pago pago = new Pago();
        pago.setReservaId(dto.getReservaId());
        pago.setMonto(dto.getMonto());
        pago.setMetodoPago(dto.getMetodoPago());
        pago.setEstado("COMPLETADO"); // Simulamos que el pago siempre es exitoso
        pago.setFechaPago(LocalDateTime.now());

        Pago guardado = pagoRepository.save(pago);
        return mapearAResponse(guardado);
    }

    public List<PagoResponseDTO> obtenerTodos() {
        return pagoRepository.findAll().stream().map(this::mapearAResponse).collect(Collectors.toList());
    }

    private PagoResponseDTO mapearAResponse(Pago pago) {
        PagoResponseDTO response = new PagoResponseDTO();
        response.setId(pago.getId());
        response.setReservaId(pago.getReservaId());
        response.setMonto(pago.getMonto());
        response.setMetodoPago(pago.getMetodoPago());
        response.setEstado(pago.getEstado());
        response.setFechaPago(pago.getFechaPago());
        return response;
    }
}