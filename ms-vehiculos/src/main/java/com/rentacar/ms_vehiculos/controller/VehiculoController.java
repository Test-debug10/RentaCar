package com.rentacar.ms_vehiculos.controller;

import java.util.List;

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
import com.rentacar.ms_vehiculos.service.VehiculoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/version/vehiculos")
public class VehiculoController {

    @Autowired
    private VehiculoService vehiculoService;

    @PostMapping
    public ResponseEntity<VehiculoResponseDTO> crearVehiculo(@Valid @RequestBody VehiculoRequestDTO dto) {
        VehiculoResponseDTO nuevoVehiculo = vehiculoService.registrarVehiculo(dto);
        return new ResponseEntity<>(nuevoVehiculo, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<VehiculoResponseDTO>> listarVehiculos() {
        return ResponseEntity.ok(vehiculoService.obtenerTodos());
    }
}