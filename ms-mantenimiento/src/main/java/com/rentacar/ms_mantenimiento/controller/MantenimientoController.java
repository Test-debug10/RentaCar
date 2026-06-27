package com.rentacar.ms_mantenimiento.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rentacar.ms_mantenimiento.dto.MantenimientoRequestDTO;
import com.rentacar.ms_mantenimiento.dto.MantenimientoResponseDTO;
import com.rentacar.ms_mantenimiento.service.MantenimientoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "Mantenimientos", description = "API para la gestion de mantenimientos")
@RestController
@RequestMapping("/api/v1/mantenimientos")
public class MantenimientoController {

    @Autowired
    private MantenimientoService mantenimientoService;

    @Operation(summary = "Registrar un nuevo mantenimiento", description = "Guarda un mantenimiento")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Mantenimiento creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Error de validacion")
    })
    @PostMapping
    public ResponseEntity<MantenimientoResponseDTO> crearMantenimiento(@Valid @RequestBody MantenimientoRequestDTO dto) {
        log.info("Iniciando registro V1 para vehículo: {}", dto.getVehiculoId());
        MantenimientoResponseDTO nuevo = mantenimientoService.registrarMantenimiento(dto);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MantenimientoResponseDTO>> listarMantenimientos() {
        return ResponseEntity.ok(mantenimientoService.obtenerTodosDTO());
    }
}