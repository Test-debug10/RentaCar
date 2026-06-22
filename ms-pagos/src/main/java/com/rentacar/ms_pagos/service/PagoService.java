package com.rentacar.ms_pagos.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rentacar.ms_pagos.model.Pago;
import com.rentacar.ms_pagos.repository.PagoRepository;

@Service
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    public List<Pago> obtenerTodos() {
        return pagoRepository.findAll();
    }

    public Pago findById(Long id) {
        return pagoRepository.findById(id).orElse(null);
    }

    public Pago save(Pago pago) {
        if (pago.getEstado() == null) {
            pago.setEstado("COMPLETADO");
        }
        if (pago.getFechaPago() == null) {
            pago.setFechaPago(LocalDateTime.now());
        }
        return pagoRepository.save(pago);
    }

    public Pago patchPago(Long id, Pago parcial) {
        Pago existente = findById(id);
        
        if (existente == null) {
            return null;
        }

        if (parcial.getReservaId() != null) {
            existente.setReservaId(parcial.getReservaId());
        }
        if (parcial.getMonto() != null) {
            existente.setMonto(parcial.getMonto());
        }
        if (parcial.getMetodoPago() != null) {
            existente.setMetodoPago(parcial.getMetodoPago());
        }
        if (parcial.getEstado() != null) {
            existente.setEstado(parcial.getEstado());
        }

        return pagoRepository.save(existente);
    }

    public void deleteById(Long id) {
        pagoRepository.deleteById(id);
    }
}