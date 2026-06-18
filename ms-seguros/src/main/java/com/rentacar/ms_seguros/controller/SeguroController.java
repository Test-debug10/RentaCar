package com.rentacar.ms_seguros.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rentacar.ms_seguros.dto.SeguroRequestDTO;
import com.rentacar.ms_seguros.dto.SeguroResponseDTO;
import com.rentacar.ms_seguros.service.SeguroService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "Seguros", description = "API para la gestion de seguros")
@RestController
@RequestMapping("/api/v1/seguros")
public class SeguroController {

    @Autowired
    private SeguroService seguroService;

    @Operation(summary = "Registrar un nuevo seguro", description = "Guarda un seguro en la base de datos validando sus atributos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Seguro creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Error de validacion en los datos enviados"),
        @ApiResponse(responseCode = "409", description = "Conflicto: El seguro ya existe")
    })
    @PostMapping
    public ResponseEntity<SeguroResponseDTO> crearSeguro(@Valid @RequestBody SeguroRequestDTO dto) {
        log.info("Procesando emision de seguro {} para la reserva ID: {}", dto.getTipoSeguro(), dto.getReservaId());
        SeguroResponseDTO nuevoSeguro = seguroService.registrarSeguro(dto);
        log.info("Seguro emitido exitosamente con ID interno: {}", nuevoSeguro.getId());
        return new ResponseEntity<>(nuevoSeguro, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SeguroResponseDTO>> listarSeguros() {
        return ResponseEntity.ok(seguroService.obtenerTodos());
    }
}