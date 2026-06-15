package com.rentacar.ms_vehiculos.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rentacar.ms_vehiculos.dto.VehiculoRequestDTO;
import com.rentacar.ms_vehiculos.dto.VehiculoResponseDTO;
import com.rentacar.ms_vehiculos.model.Vehiculo;
import com.rentacar.ms_vehiculos.repository.VehiculoRepository;

@Service
public class VehiculoService {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    public VehiculoResponseDTO registrarVehiculo(VehiculoRequestDTO dto) {
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setMarca(dto.getMarca());
        vehiculo.setModelo(dto.getModelo());
        vehiculo.setPatente(dto.getPatente());
        vehiculo.setAnio(dto.getAnio());
        vehiculo.setPrecioPorDia(dto.getPrecioPorDia());
        vehiculo.setDisponible(true);

        Vehiculo guardado = vehiculoRepository.save(vehiculo);
        return mapearAResponse(guardado);
    }

    public List<VehiculoResponseDTO> obtenerTodos() {
        return vehiculoRepository.findAll().stream().map(this::mapearAResponse).collect(Collectors.toList());
    }

    private VehiculoResponseDTO mapearAResponse(Vehiculo vehiculo) {
        VehiculoResponseDTO response = new VehiculoResponseDTO();
        response.setId(vehiculo.getId());
        response.setMarca(vehiculo.getMarca());
        response.setModelo(vehiculo.getModelo());
        response.setPatente(vehiculo.getPatente());
        response.setAnio(vehiculo.getAnio());
        response.setPrecioPorDia(vehiculo.getPrecioPorDia());
        response.setDisponible(vehiculo.getDisponible());
        return response;
    }
}