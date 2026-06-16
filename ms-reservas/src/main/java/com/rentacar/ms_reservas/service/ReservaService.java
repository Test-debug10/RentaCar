package com.rentacar.ms_reservas.service;

import com.rentacar.ms_reservas.client.UsuarioClient;
import com.rentacar.ms_reservas.client.VehiculoClient;
import com.rentacar.ms_reservas.client.VehiculoDTO;
import com.rentacar.ms_reservas.dto.ReservaRequestDTO;
import com.rentacar.ms_reservas.dto.ReservaResponseDTO;
import com.rentacar.ms_reservas.model.Reserva;
import com.rentacar.ms_reservas.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private UsuarioClient usuarioClient;

    @Autowired
    private VehiculoClient vehiculoClient;

    // utilizamos el feig
    public ReservaResponseDTO crearReserva(ReservaRequestDTO dto) {
        if (dto.getFechaFin().isBefore(dto.getFechaInicio())) {
            throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la fecha de inicio");
        }

        // Validamos que el usuario exista
        try {
            usuarioClient.obtenerUsuarioPorId(dto.getUsuarioId());
        } catch (Exception e) {
            throw new IllegalArgumentException("El usuario ingresado no existe");
        }

        // Validamos que el auto existe
        VehiculoDTO vehiculo;
        try {
            vehiculo = vehiculoClient.obtenerVehiculoPorId(dto.getVehiculoId());
        } catch (Exception e) {
            throw new IllegalArgumentException("El vehículo ingresado no existe o no está disponible");
        }

        // calculamos el monto
        long dias = ChronoUnit.DAYS.between(dto.getFechaInicio(), dto.getFechaFin());
        if (dias == 0) 
            dias = 1; // nota:1 día de cobro
        Double montoTotal = dias * vehiculo.getPrecioPorDia();

        // guardamos
        Reserva reserva = new Reserva();
        reserva.setUsuarioId(dto.getUsuarioId());
        reserva.setVehiculoId(dto.getVehiculoId());
        reserva.setFechaInicio(dto.getFechaInicio());
        reserva.setFechaFin(dto.getFechaFin());
        reserva.setMontoTotal(montoTotal);

        Reserva guardada = reservaRepository.save(reserva);
        return mapearAResponse(guardada);
    }

    public List<ReservaResponseDTO> obtenerTodas() {
        return reservaRepository.findAll().stream().map(this::mapearAResponse).collect(Collectors.toList());
    }

    private ReservaResponseDTO mapearAResponse(Reserva reserva) {
        ReservaResponseDTO response = new ReservaResponseDTO();
        response.setId(reserva.getId());
        response.setUsuarioId(reserva.getUsuarioId());
        response.setVehiculoId(reserva.getVehiculoId());
        response.setFechaInicio(reserva.getFechaInicio());
        response.setFechaFin(reserva.getFechaFin());
        response.setMontoTotal(reserva.getMontoTotal());
        return response;
    }
}