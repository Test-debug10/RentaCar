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

    @Autowired
    private SucursalRepository sucursalRepository;

    public SucursalResponseDTO registrarSucursal(SucursalRequestDTO dto) {
        Sucursal sucursal = new Sucursal();
        sucursal.setNombre(dto.getNombre());
        sucursal.setDireccion(dto.getDireccion());
        sucursal.setCiudad(dto.getCity());
        sucursal.setTelefono(dto.getTelefono());

        Sucursal guardada = sucursalRepository.save(sucursal);
        return mapearAResponse(guardada);
    }

    public List<SucursalResponseDTO> obtenerTodas() {
        return sucursalRepository.findAll().stream().map(this::mapearAResponse).collect(Collectors.toList());
    }

    private SucursalResponseDTO mapearAResponse(Sucursal sucursal) {
        SucursalResponseDTO response = new SucursalResponseDTO();
        response.setId(sucursal.getId());
        response.setNombre(sucursal.getNombre());
        response.setDireccion(sucursal.getDireccion());
        response.setCiudad(sucursal.getCiudad());
        response.setTelefono(sucursal.getTelefono());
        return response;
    }
}