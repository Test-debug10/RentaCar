package com.rentacar.ms_evaluaciones.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rentacar.ms_evaluaciones.dto.EvaluacionRequestDTO;
import com.rentacar.ms_evaluaciones.dto.EvaluacionResponseDTO;
import com.rentacar.ms_evaluaciones.service.EvaluacionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "Evaluaciones", description = "API para la gestion de evaluaciones")
@RestController
@RequestMapping("/api/v2/evaluaciones")
public class EvaluacionController {

    @Autowired
    private EvaluacionService evaluacionService;

    @Operation(summary = "Registrar una nueva evaluacion", description = "Guarda una evaluacion en la base de datos validando sus atributos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Evaluacion creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Error de validacion en los datos enviados"),
        @ApiResponse(responseCode = "409", description = "Conflicto: La evaluacion ya existe")
    })
    
    @PostMapping
    public ResponseEntity<EvaluacionResponseDTO> crearEvaluacion(@Valid @RequestBody EvaluacionRequestDTO dto) {
        log.info("Recibiendo nueva evaluacion para la reserva ID: {}", dto.getReservaId());
        EvaluacionResponseDTO nuevaEvaluacion = evaluacionService.registrarEvaluacion(dto);
        log.info("Evaluacion guardada exitosamente con ID: {}", nuevaEvaluacion.getId());
        return new ResponseEntity<>(nuevaEvaluacion, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EvaluacionResponseDTO>> listarEvaluaciones() {
        return ResponseEntity.ok(evaluacionService.obtenerTodas());
    }
    
}