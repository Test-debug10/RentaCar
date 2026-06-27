package com.rentacar.ms_vehiculos.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rentacar.ms_vehiculos.dto.VehiculoRequestDTO;
import com.rentacar.ms_vehiculos.dto.VehiculoResponseDTO;
import com.rentacar.ms_vehiculos.model.Vehiculo;
import com.rentacar.ms_vehiculos.service.VehiculoService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Vehiculos", description = "API para la gestion de vehiculos")
@RestController
@RequestMapping("/api/v1/vehiculos")
public class VehiculoController {

    @Autowired
    private VehiculoService vehiculoService;

    @PostMapping
    public ResponseEntity<VehiculoResponseDTO> crearVehiculo(@Valid @RequestBody VehiculoRequestDTO dto) {
        Vehiculo v = new Vehiculo();
        v.setMarca(dto.getMarca());
        v.setModelo(dto.getModelo());
        v.setPatente(dto.getPatente());
        v.setAnio(dto.getAnio());
        v.setPrecioPorDia(dto.getPrecioPorDia());

        Vehiculo guardado = vehiculoService.save(v);

        VehiculoResponseDTO resp = new VehiculoResponseDTO();
        resp.setId(guardado.getId());
        resp.setMarca(guardado.getMarca());
        resp.setPatente(guardado.getPatente());
        resp.setPrecioPorDia(guardado.getPrecioPorDia());
        
        return new ResponseEntity<>(resp, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<VehiculoResponseDTO>> listarVehiculos() {
        return ResponseEntity.ok(vehiculoService.obtenerTodos().stream().map(v -> {
            VehiculoResponseDTO dto = new VehiculoResponseDTO();
            dto.setId(v.getId());
            dto.setMarca(v.getMarca());
            dto.setPatente(v.getPatente());
            dto.setPrecioPorDia(v.getPrecioPorDia());
            return dto;
        }).collect(Collectors.toList()));
    }
}