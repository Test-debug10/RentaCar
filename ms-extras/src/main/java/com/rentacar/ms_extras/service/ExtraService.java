package com.rentacar.ms_extras.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rentacar.ms_extras.model.Extra;
import com.rentacar.ms_extras.repository.ExtraRepository;

@Service
public class ExtraService {

    @Autowired
    private ExtraRepository extraRepository;

    public List<Extra> obtenerTodos() {
        return extraRepository.findAll();
    }

    public Extra findById(Long id) {
        return extraRepository.findById(id).orElse(null);
    }

    public Extra save(Extra extra) {
        return extraRepository.save(extra);
    }

    public Extra patchExtra(Long id, Extra extraParcial) {
        Extra existente = findById(id);
        
        if (existente == null) {
            return null;
        }

        if (extraParcial.getNombre() != null) {
            existente.setNombre(extraParcial.getNombre());
        }
        if (extraParcial.getDescripcion() != null) {
            existente.setDescripcion(extraParcial.getDescripcion());
        }
        if (extraParcial.getPrecioDiario() != null) {
            existente.setPrecioDiario(extraParcial.getPrecioDiario());
        }
        if (extraParcial.getStockTotal() != null) {
            existente.setStockTotal(extraParcial.getStockTotal());
        }

        return extraRepository.save(existente);
    }

    public void deleteById(Long id) {
        extraRepository.deleteById(id);
    }
}