package com.rentacar.ms_evaluaciones.controller;

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

import com.rentacar.ms_evaluaciones.dto.EvaluacionRequestDTO;
import com.rentacar.ms_evaluaciones.dto.EvaluacionResponseDTO;
import com.rentacar.ms_evaluaciones.model.Evaluacion;
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
@RequestMapping("/api/v1/evaluaciones")
public class EvaluacionController {

    @Autowired
    private EvaluacionService evaluacionService;

    @Operation(summary = "Registrar una nueva evaluacion", description = "Guarda una evaluacion en la base de datos")
    @ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Evaluacion creada exitosamente")})
    @PostMapping
    public ResponseEntity<EvaluacionResponseDTO> crearEvaluacion(@Valid @RequestBody EvaluacionRequestDTO dto) {
        log.info("Recibiendo nueva evaluacion para la reserva ID: {}", dto.getReservaId());
        
        Evaluacion e = new Evaluacion();
        e.setReservaId(dto.getReservaId());
        e.setCalificacion(dto.getCalificacion());
        e.setComentario(dto.getComentario());

        Evaluacion guardada = evaluacionService.save(e);

        EvaluacionResponseDTO resp = new EvaluacionResponseDTO();
        resp.setId(guardada.getId());
        resp.setReservaId(guardada.getReservaId());
        resp.setCalificacion(guardada.getCalificacion());
        resp.setComentario(guardada.getComentario());
        resp.setFechaEvaluacion(guardada.getFechaEvaluacion());

        log.info("Evaluacion guardada exitosamente con ID: {}", guardada.getId());
        return new ResponseEntity<>(resp, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EvaluacionResponseDTO>> listarEvaluaciones() {
        List<EvaluacionResponseDTO> responseList = evaluacionService.obtenerTodas().stream().map(e -> {
            EvaluacionResponseDTO dto = new EvaluacionResponseDTO();
            dto.setId(e.getId());
            dto.setReservaId(e.getReservaId());
            dto.setCalificacion(e.getCalificacion());
            dto.setComentario(e.getComentario());
            dto.setFechaEvaluacion(e.getFechaEvaluacion());
            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(responseList);
    }
}