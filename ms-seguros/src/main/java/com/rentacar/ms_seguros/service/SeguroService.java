package com.rentacar.ms_seguros.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rentacar.ms_seguros.client.ReservaClient;
import com.rentacar.ms_seguros.model.Seguro;
import com.rentacar.ms_seguros.repository.SeguroRepository;

@Service
public class SeguroService {

    @Autowired
    private SeguroRepository seguroRepository;

    @Autowired
    private ReservaClient reservaClient;

    public List<Seguro> obtenerTodos() {
        return seguroRepository.findAll();
    }

    public Seguro findById(Long id) {
        return seguroRepository.findById(id).orElse(null);
    }

    public Seguro save(Seguro seguro) {
        try {
            reservaClient.obtenerReservaPorId(seguro.getReservaId());
        } catch (Exception e) {
            throw new IllegalArgumentException("La reserva indicada no existe en el sistema.");
        }

        return seguroRepository.save(seguro);
    }

    public Seguro patchSeguro(Long id, Seguro parcial) {
        Seguro existente = findById(id);
        
        if (existente == null) {
            return null;
        }

        if (parcial.getReservaId() != null) {
            try {
                reservaClient.obtenerReservaPorId(parcial.getReservaId());
                existente.setReservaId(parcial.getReservaId());
            } catch (Exception e) {
                throw new IllegalArgumentException("La nueva reserva indicada no existe en el sistema.");
            }
        }

        if (parcial.getTipoSeguro() != null) {
            existente.setTipoSeguro(parcial.getTipoSeguro());
        }
        if (parcial.getCoberturaMaxima() != null) {
            existente.setCoberturaMaxima(parcial.getCoberturaMaxima());
        }
        if (parcial.getCostoAdicional() != null) {
            existente.setCostoAdicional(parcial.getCostoAdicional());
        }

        return seguroRepository.save(existente);
    }

    public void deleteById(Long id) {
        seguroRepository.deleteById(id);
    }
}