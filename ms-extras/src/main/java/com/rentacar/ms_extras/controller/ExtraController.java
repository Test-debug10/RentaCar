package com.rentacar.ms_extras.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rentacar.ms_extras.dto.ExtraRequestDTO;
import com.rentacar.ms_extras.dto.ExtraResponseDTO;
import com.rentacar.ms_extras.service.ExtraService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "Extras", description = "API para la gestion de extras")
@RestController
@RequestMapping("/api/v1/extras")
public class ExtraController {
    
    @Autowired
    private ExtraService extraService;

    @Operation(summary = "Registrar un nuevo extra", description = "Guarda un extra en la base de datos validando sus atributos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Extra creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Error de validacion en los datos enviados"),
        @ApiResponse(responseCode = "409", description = "Conflicto: El extra ya existe")
    })
    @PostMapping
    public ResponseEntity<ExtraResponseDTO> crearExtra(@Valid @RequestBody ExtraRequestDTO dto) {
        log.info("Registrando nuevo accesorio extra en el catalogo: {}", dto.getNombre());
        ExtraResponseDTO nuevoExtra = extraService.registrarExtra(dto);
        log.info("Accesorio registrado con exito, ID: {}", nuevoExtra.getId());
        return new ResponseEntity<>(nuevoExtra, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ExtraResponseDTO>> listarExtras() {
        return ResponseEntity.ok(extraService.obtenerTodos());
    }
}