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
@RequestMapping("/api/v2/mantenimientos")
public class MantenimientoController {

    @Autowired
    private MantenimientoService mantenimientoService;

    @Operation(summary = "Registrar un nuevo mantenimiento", description = "Guarda un mantenimiento en la base de datos validando sus atributos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Mantenimiento creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Error de validacion en los datos enviados"),
        @ApiResponse(responseCode = "409", description = "Conflicto: El mantenimiento ya existe")
    })
    
    @PostMapping
    public ResponseEntity<MantenimientoResponseDTO> crearMantenimiento(@Valid @RequestBody MantenimientoRequestDTO dto) {
        log.info("Iniciando registro de mantenimiento {} para el vehiculo ID: {}", dto.getTipo(), dto.getVehiculoId());
        MantenimientoResponseDTO nuevoMantenimiento = mantenimientoService.registrarMantenimiento(dto);
        log.info("Mantenimiento registrado con ID: {}", nuevoMantenimiento.getId());
        return new ResponseEntity<>(nuevoMantenimiento, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MantenimientoResponseDTO>> listarMantenimientos() {
        return ResponseEntity.ok(mantenimientoService.obtenerTodos());
    }
}