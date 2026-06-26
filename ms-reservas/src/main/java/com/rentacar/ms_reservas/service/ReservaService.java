package com.rentacar.ms_reservas.service;

import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rentacar.ms_reservas.client.UsuarioClient;
import com.rentacar.ms_reservas.client.VehiculoClient;
import com.rentacar.ms_reservas.client.VehiculoDTO;
import com.rentacar.ms_reservas.dto.ReservaResponseDTO;
import com.rentacar.ms_reservas.model.Reserva;
import com.rentacar.ms_reservas.repository.ReservaRepository;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private UsuarioClient usuarioClient;

    @Autowired
    private VehiculoClient vehiculoClient;

    public List<ReservaResponseDTO> obtenerTodas() {
        return reservaRepository.findAll().stream().map(e -> {
            ReservaResponseDTO dto = new ReservaResponseDTO();

            dto.setId(e.getId());
            dto.setUsuarioId(e.getUsuarioId());
            dto.setVehiculoId(e.getVehiculoId());
            dto.setFechaFin(e.getFechaInicio());
            dto.setFechaFin(e.getFechaFin());
            dto.setMontoTotal(e.getMontoTotal());

            return dto;
        }).toList();
    }

    public Reserva findById(Long id) {
        return reservaRepository.findById(id).orElse(null);
    }

    public Reserva save(Reserva reserva) {
        if (reserva.getFechaFin().isBefore(reserva.getFechaInicio())) {
            throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la fecha de inicio");
        }

        try {
            usuarioClient.obtenerUsuarioPorId(reserva.getUsuarioId());
        } catch (Exception e) {
            throw new IllegalArgumentException("El usuario ingresado no existe");
        }

        VehiculoDTO vehiculo;
        try {
            vehiculo = vehiculoClient.obtenerVehiculoPorId(reserva.getVehiculoId());
        } catch (Exception e) {
            throw new IllegalArgumentException("El vehículo ingresado no existe o no está disponible");
        }

        long dias = ChronoUnit.DAYS.between(reserva.getFechaInicio(), reserva.getFechaFin());
        if (dias == 0) {
            dias = 1; // minimo 1 dia de cobro
        }
        reserva.setMontoTotal(dias * vehiculo.getPrecioPorDia());

        return reservaRepository.save(reserva);
    }

    public Reserva patchReserva(Long id, Reserva parcial) {
        Reserva existente = findById(id);
        if (existente == null) {
            return null;
        }

        if (parcial.getUsuarioId() != null) {
            try {
                usuarioClient.obtenerUsuarioPorId(parcial.getUsuarioId());
                existente.setUsuarioId(parcial.getUsuarioId());
            } catch (Exception e) {
                throw new IllegalArgumentException("El nuevo usuario ingresado no existe");
            }
        }

        boolean requiereRecalculo = false;

        if (parcial.getVehiculoId() != null) {
            existente.setVehiculoId(parcial.getVehiculoId());
            requiereRecalculo = true;
        }
        if (parcial.getFechaInicio() != null) {
            existente.setFechaInicio(parcial.getFechaInicio());
            requiereRecalculo = true;
        }
        if (parcial.getFechaFin() != null) {
            existente.setFechaFin(parcial.getFechaFin());
            requiereRecalculo = true;
        }

        if (existente.getFechaFin().isBefore(existente.getFechaInicio())) {
            throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la fecha de inicio");
        }

        if (requiereRecalculo) {
            VehiculoDTO vehiculo;
            try {
                vehiculo = vehiculoClient.obtenerVehiculoPorId(existente.getVehiculoId());
            } catch (Exception e) {
                throw new IllegalArgumentException("El vehículo de la actualización no existe o no está disponible");
            }
            long dias = ChronoUnit.DAYS.between(existente.getFechaInicio(), existente.getFechaFin());
            if (dias == 0) {
                dias = 1;
            }
            existente.setMontoTotal(dias * vehiculo.getPrecioPorDia());
        }

        return reservaRepository.save(existente);
    }

    public void deleteById(Long id) {
        reservaRepository.deleteById(id);
    }
}