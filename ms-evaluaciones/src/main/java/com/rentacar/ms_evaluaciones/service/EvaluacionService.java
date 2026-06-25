package com.rentacar.ms_evaluaciones.service;

import java.time.LocalDate;
import java.util.List;

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

    public Evaluacion findById(Long id) {
        return evaluacionRepository.findById(id).orElse(null);
    }

    public Evaluacion save(Evaluacion evaluacion) {
        try {
            reservaClient.obtenerReservaPorId(evaluacion.getReservaId());
        } catch (Exception e) {
            throw new IllegalArgumentException("No se encontró la reserva asociada para evaluar.");
        }

        if (evaluacion.getFechaEvaluacion() == null) {
            evaluacion.setFechaEvaluacion(LocalDate.now());
        }

        return evaluacionRepository.save(evaluacion);
    }

    public Evaluacion patchEvaluacion(Long id, Evaluacion evaluacionParcial) {
        Evaluacion existente = findById(id);
        
        if (existente == null) {
            return null;
        }

        if (evaluacionParcial.getCalificacion() != null) {
            existente.setCalificacion(evaluacionParcial.getCalificacion());
        }
        if (evaluacionParcial.getComentario() != null) {
            existente.setComentario(evaluacionParcial.getComentario());
        }
        
        return evaluacionRepository.save(existente);
    }

    public void deleteById(Long id) {
        evaluacionRepository.deleteById(id);
    }
    

    public EvaluacionResponseDTO registrarEvaluacion(EvaluacionRequestDTO dto) {
        Evaluacion evaluacion = new Evaluacion();
        evaluacion.setReservaId(dto.getReservaId());
        evaluacion.setCalificacion(dto.getCalificacion());
        evaluacion.setComentario(dto.getComentario());

        Evaluacion guardada = save(evaluacion);

        EvaluacionResponseDTO response = new EvaluacionResponseDTO();
        response.setId(guardada.getId());
        response.setReservaId(guardada.getReservaId());
        response.setCalificacion(guardada.getCalificacion());
        response.setComentario(guardada.getComentario());
        response.setFechaEvaluacion(guardada.getFechaEvaluacion());

        return response;
    }

    public List<EvaluacionResponseDTO> obtenerTodas() {
        return evaluacionRepository.findAll().stream().map(e -> {
            EvaluacionResponseDTO dto = new EvaluacionResponseDTO();

            dto.setId(e.getId());
            dto.setReservaId(e.getReservaId());
            dto.setCalificacion(e.getCalificacion());
            dto.setComentario(e.getComentario());
            dto.setFechaEvaluacion(e.getFechaEvaluacion());

            return dto;
        }).toList();
    }
}