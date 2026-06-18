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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Vehiculos", description = "API para la gestion de vehiculos")
@RestController
@RequestMapping("/api/v2/vehiculos")
public class VehiculoController {

    @Autowired
    private VehiculoService vehiculoService;

    @Operation(summary = "Registrar un nuevo vehiculo", description = "Guarda un vehiculo en la base de datos validando sus atributos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Vehiculo creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Error de validacion en los datos enviados"),
        @ApiResponse(responseCode = "409", description = "Conflicto: La patente ya existe")
    })
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