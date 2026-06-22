package com.rentacar.ms_mantenimiento.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rentacar.ms_mantenimiento.client.VehiculoClient;
import com.rentacar.ms_mantenimiento.model.Mantenimiento;
import com.rentacar.ms_mantenimiento.repository.MantenimientoRepository;

@Service
public class MantenimientoService {

    @Autowired
    private MantenimientoRepository mantenimientoRepository;

    @Autowired
    private VehiculoClient vehiculoClient;

    public List<Mantenimiento> obtenerTodos() {
        return mantenimientoRepository.findAll();
    }

    public Mantenimiento findById(Long id) {
        return mantenimientoRepository.findById(id).orElse(null);
    }

    public Mantenimiento save(Mantenimiento mantenimiento) {
        try {
            vehiculoClient.obtenerVehiculoPorId(mantenimiento.getVehiculoId());
        } catch (Exception e) {
            throw new IllegalArgumentException("El vehículo ingresado no existe en el catálogo");
        }

        return mantenimientoRepository.save(mantenimiento);
    }

    public Mantenimiento patchMantenimiento(Long id, Mantenimiento parcial) {
        Mantenimiento existente = findById(id);
        
        if (existente == null) {
            return null;
        }

        if (parcial.getVehiculoId() != null) {
            try {
                vehiculoClient.obtenerVehiculoPorId(parcial.getVehiculoId());
                existente.setVehiculoId(parcial.getVehiculoId());
            } catch (Exception e) {
                throw new IllegalArgumentException("El nuevo vehículo ingresado no existe en el catálogo");
            }
        }

        if (parcial.getTipo() != null) {
            existente.setTipo(parcial.getTipo());
        }
        if (parcial.getDescripcion() != null) {
            existente.setDescripcion(parcial.getDescripcion());
        }
        if (parcial.getFechaIngreso() != null) {
            existente.setFechaIngreso(parcial.getFechaIngreso());
        }
        if (parcial.getCosto() != null) {
            existente.setCosto(parcial.getCosto());
        }

        return mantenimientoRepository.save(existente);
    }

    public void deleteById(Long id) {
        mantenimientoRepository.deleteById(id);
    }
}