package com.rentacar.ms_evaluaciones.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rentacar.ms_evaluaciones.client.ReservaClient;
import com.rentacar.ms_evaluaciones.dto.EvaluacionRequestDTO;
import com.rentacar.ms_evaluaciones.dto.EvaluacionResponseDTO;
import com.rentacar.ms_evaluaciones.model.Evaluacion;
import com.rentacar.ms_evaluaciones.repository.EvaluacionRepository;

@Service
public class EvaluacionService {

    @Autowired
    private EvaluacionRepository evaluacionRepository;

    @Autowired
    private ReservaClient reservaClient;

    public EvaluacionResponseDTO registrarEvaluacion(EvaluacionRequestDTO dto) {
        try {
            reservaClient.obtenerReservaPorId(dto.getReservaId());
        } catch (Exception e) {
            throw new IllegalArgumentException("No se encontró la reserva asociada para evaluar.");
        }

        Evaluacion evaluacion = new Evaluacion();
        evaluacion.setReservaId(dto.getReservaId());
        evaluacion.setCalificacion(dto.getCalificacion());
        evaluacion.setComentario(dto.getComentario());
        evaluacion.setFechaEvaluacion(LocalDate.now());

        Evaluacion guardada = evaluacionRepository.save(evaluacion);
        return mapearAResponse(guardada);
    }

    public List<EvaluacionResponseDTO> obtenerTodas() {
        return evaluacionRepository.findAll().stream()
                .map(this::mapearAResponse)
                .collect(Collectors.toList());
    }

    private EvaluacionResponseDTO mapearAResponse(Evaluacion evaluacion) {
        EvaluacionResponseDTO response = new EvaluacionResponseDTO();
        response.setId(evaluacion.getId());
        response.setReservaId(evaluacion.getReservaId());
        response.setCalificacion(evaluacion.getCalificacion());
        response.setComentario(evaluacion.getComentario());
        response.setFechaEvaluacion(evaluacion.getFechaEvaluacion());
        return response;
    }
}