package com.rentacar.ms_mantenimiento.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rentacar.ms_mantenimiento.client.VehiculoClient;
import com.rentacar.ms_mantenimiento.dto.MantenimientoRequestDTO;
import com.rentacar.ms_mantenimiento.dto.MantenimientoResponseDTO;
import com.rentacar.ms_mantenimiento.model.Mantenimiento;
import com.rentacar.ms_mantenimiento.repository.MantenimientoRepository;

@Service
public class MantenimientoService {

    @Autowired
    private MantenimientoRepository mantenimientoRepository;

    @Autowired
    private VehiculoClient vehiculoClient;

    public MantenimientoResponseDTO registrarMantenimiento(MantenimientoRequestDTO dto) {
        
        // Verificamos que el vehículo exista mediante Feign
        try {
            vehiculoClient.obtenerVehiculoPorId(dto.getVehiculoId());
        } catch (Exception e) {
            throw new IllegalArgumentException("El vehículo ingresado no existe en el catálogo");
        }

        Mantenimiento mant = new Mantenimiento();
        mant.setVehiculoId(dto.getVehiculoId());
        mant.setTipo(dto.getTipo());
        mant.setDescripcion(dto.getDescripcion());
        mant.setFechaIngreso(dto.getFechaIngreso());
        mant.setCosto(dto.getCosto());

        Mantenimiento guardado = mantenimientoRepository.save(mant);
        return mapearAResponse(guardado);
    }

    public List<MantenimientoResponseDTO> obtenerTodos() {
        return mantenimientoRepository.findAll().stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
    }

    private MantenimientoResponseDTO mapearAResponse(Mantenimiento mant) {
        MantenimientoResponseDTO response = new MantenimientoResponseDTO();
        response.setId(mant.getId());
        response.setVehiculoId(mant.getVehiculoId());
        response.setTipo(mant.getTipo());
        response.setDescripcion(mant.getDescripcion());
        response.setFechaIngreso(mant.getFechaIngreso());
        response.setCosto(mant.getCosto());
        return response;
    }
}