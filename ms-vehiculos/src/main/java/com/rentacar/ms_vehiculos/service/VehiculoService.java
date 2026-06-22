package com.rentacar.ms_vehiculos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rentacar.ms_vehiculos.model.Vehiculo;
import com.rentacar.ms_vehiculos.repository.VehiculoRepository;

@Service
public class VehiculoService {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    public List<Vehiculo> obtenerTodos() {
        return vehiculoRepository.findAll();
    }

    public Vehiculo findById(Long id) {
        return vehiculoRepository.findById(id).orElse(null);
    }

    public Vehiculo save(Vehiculo vehiculo) {
        if (vehiculo.getId() == null && vehiculo.getDisponible() == null) {
            vehiculo.setDisponible(true);
        }
        return vehiculoRepository.save(vehiculo);
    }

    public Vehiculo patchVehiculo(Long id, Vehiculo parcial) {
        Vehiculo existente = findById(id);
        
        if (existente == null) {
            return null;
        }

        if (parcial.getMarca() != null) {
            existente.setMarca(parcial.getMarca());
        }
        if (parcial.getModelo() != null) {
            existente.setModelo(parcial.getModelo());
        }
        if (parcial.getPatente() != null) {
            existente.setPatente(parcial.getPatente());
        }
        if (parcial.getAnio() != null) {
            existente.setAnio(parcial.getAnio());
        }
        if (parcial.getPrecioPorDia() != null) {
            existente.setPrecioPorDia(parcial.getPrecioPorDia());
        }
        if (parcial.getDisponible() != null) {
            existente.setDisponible(parcial.getDisponible());
        }

        return vehiculoRepository.save(existente);
    }

    public void deleteById(Long id) {
        vehiculoRepository.deleteById(id);
    }
}