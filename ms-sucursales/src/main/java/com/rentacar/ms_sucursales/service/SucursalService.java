package com.rentacar.ms_sucursales.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rentacar.ms_sucursales.model.Sucursal;
import com.rentacar.ms_sucursales.repository.SucursalRepository;

@Service
public class SucursalService {

    @Autowired
    private SucursalRepository sucursalRepository;

    public List<Sucursal> obtenerTodas() {
        return sucursalRepository.findAll();
    }

    public Sucursal findById(Long id) {
        return sucursalRepository.findById(id).orElse(null);
    }

    public Sucursal save(Sucursal sucursal) {
        return sucursalRepository.save(sucursal);
    }

    public Sucursal patchSucursal(Long id, Sucursal parcial) {
        Sucursal existente = findById(id);
        
        if (existente == null) {
            return null;
        }

        if (parcial.getNombre() != null) {
            existente.setNombre(parcial.getNombre());
        }
        if (parcial.getDireccion() != null) {
            existente.setDireccion(parcial.getDireccion());
        }
        if (parcial.getCiudad() != null) {
            existente.setCiudad(parcial.getCiudad());
        }
        if (parcial.getTelefono() != null) {
            existente.setTelefono(parcial.getTelefono());
        }

        return sucursalRepository.save(existente);
    }

    public void deleteById(Long id) {
        sucursalRepository.deleteById(id);
    }
}