package com.rentacar.ms_reservas.service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rentacar.ms_reservas.client.VehiculoClient;
import com.rentacar.ms_reservas.client.VehiculoDTO;
import com.rentacar.ms_reservas.dto.ReservaRequestDTO;
import com.rentacar.ms_reservas.dto.ReservaResponseDTO;
import com.rentacar.ms_reservas.model.Reserva;
import com.rentacar.ms_reservas.repository.ReservaRepository;

@Service
public class ReservaService {

    @Autowired private ReservaRepository reservaRepository;
    @Autowired private VehiculoClient vehiculoClient;

    public Reserva findById(Long id) {
        return reservaRepository.findById(id).orElse(null);
    }

    public List<Reserva> obtenerTodas() {
        return reservaRepository.findAll();
    }

    public Reserva save(Reserva reserva) {
        if (reserva.getFechaFin().isBefore(reserva.getFechaInicio())) {
            throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la de inicio");
        }
        
        VehiculoDTO vehiculo = vehiculoClient.obtenerVehiculoPorId(reserva.getVehiculoId());
        long dias = ChronoUnit.DAYS.between(reserva.getFechaInicio(), reserva.getFechaFin());
        reserva.setMontoTotal((dias <= 0 ? 1 : dias) * vehiculo.getPrecioPorDia());
        
        return reservaRepository.save(reserva);
    }

    public Reserva patchReserva(Long id, Reserva parcial) {
        Reserva existente = findById(id);
        if (existente == null) {
            return null;
        }

        if (parcial.getUsuarioId() != null) {
            existente.setUsuarioId(parcial.getUsuarioId());
        }
        if (parcial.getVehiculoId() != null) {
            existente.setVehiculoId(parcial.getVehiculoId());
        }
        if (parcial.getFechaInicio() != null) {
            existente.setFechaInicio(parcial.getFechaInicio());
        }
        if (parcial.getFechaFin() != null) {
            existente.setFechaFin(parcial.getFechaFin());
        }
        
        return save(existente);
    }

    public void deleteById(Long id) {
        reservaRepository.deleteById(id);
    }

    public ReservaResponseDTO crearReserva(ReservaRequestDTO dto) {
        Reserva r = new Reserva();
        r.setUsuarioId(dto.getUsuarioId());
        r.setVehiculoId(dto.getVehiculoId());
        r.setFechaInicio(dto.getFechaInicio());
        r.setFechaFin(dto.getFechaFin());
        
        Reserva guardada = save(r);
        return mapToDTO(guardada);
    }

    public List<ReservaResponseDTO> obtenerTodasDTO() {
        return obtenerTodas().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private ReservaResponseDTO mapToDTO(Reserva r) {
        ReservaResponseDTO dto = new ReservaResponseDTO();
        dto.setId(r.getId());
        dto.setUsuarioId(r.getUsuarioId());
        dto.setVehiculoId(r.getVehiculoId());
        dto.setFechaInicio(r.getFechaInicio());
        dto.setFechaFin(r.getFechaFin());
        dto.setMontoTotal(r.getMontoTotal());
        return dto;
    }
}