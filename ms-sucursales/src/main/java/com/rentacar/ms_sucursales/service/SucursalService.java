package com.rentacar.ms_sucursales.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rentacar.ms_sucursales.dto.SucursalRequestDTO;
import com.rentacar.ms_sucursales.dto.SucursalResponseDTO;
import com.rentacar.ms_sucursales.model.Sucursal;
import com.rentacar.ms_sucursales.repository.SucursalRepository;

@Service
public class SucursalService {

    @Autowired private SucursalRepository sucursalRepository;

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

    public SucursalResponseDTO registrarSucursal(SucursalRequestDTO dto) {
        Sucursal s = new Sucursal();
        s.setNombre(dto.getNombre());
        s.setDireccion(dto.getDireccion());
        s.setCiudad(dto.getCity());
        s.setTelefono(dto.getTelefono());
        
        Sucursal guardada = save(s);
        return mapToDTO(guardada);
    }

    public List<SucursalResponseDTO> obtenerTodasDTO() {
        return obtenerTodas().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private SucursalResponseDTO mapToDTO(Sucursal s) {
        SucursalResponseDTO dto = new SucursalResponseDTO();
        dto.setId(s.getId());
        dto.setNombre(s.getNombre());
        dto.setDireccion(s.getDireccion());
        dto.setCiudad(s.getCiudad());
        dto.setTelefono(s.getTelefono());
        return dto;
    }
}