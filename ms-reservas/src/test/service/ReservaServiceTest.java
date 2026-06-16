package com.rentacar.ms_reservas.service;

import com.rentacar.ms_reservas.client.UsuarioClient;
import com.rentacar.ms_reservas.client.VehiculoClient;
import com.rentacar.ms_reservas.client.VehiculoDTO;
import com.rentacar.ms_reservas.dto.ReservaRequestDTO;
import com.rentacar.ms_reservas.dto.ReservaResponseDTO;
import com.rentacar.ms_reservas.model.Reserva;
import com.rentacar.ms_reservas.repository.ReservaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ReservaServiceTest {

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private UsuarioClient usuarioClient;

    @Mock
    private VehiculoClient vehiculoClient;

    @InjectMocks
    private ReservaService reservaService;

    private Reserva reservaMock;
    private ReservaRequestDTO requestMock;
    private VehiculoDTO vehiculoMock;

    @BeforeEach
    void setUp() {
        reservaMock = new Reserva();
        reservaMock.setId(1L);
        reservaMock.setUsuarioId(1L);
        reservaMock.setVehiculoId(1L);
        reservaMock.setFechaInicio(LocalDate.now());
        reservaMock.setFechaFin(LocalDate.now().plusDays(3));
        reservaMock.setMontoTotal(75000.0);

        requestMock = new ReservaRequestDTO();
        requestMock.setUsuarioId(1L);
        requestMock.setVehiculoId(1L);
        requestMock.setFechaInicio(LocalDate.now());
        requestMock.setFechaFin(LocalDate.now().plusDays(3));

        vehiculoMock = new VehiculoDTO();
        vehiculoMock.setId(1L);
        vehiculoMock.setPrecioPorDia(25000.0);
    }

    @Test
    void crearReservaExitosamenteTest() {
        when(usuarioClient.obtenerUsuarioPorId(1L)).thenReturn(new Object());
        when(vehiculoClient.obtenerVehiculoPorId(1L)).thenReturn(vehiculoMock);
        when(reservaRepository.save(any(Reserva.class))).thenReturn(reservaMock);

        ReservaResponseDTO response = reservaService.crearReserva(requestMock);
        
        assertNotNull(response);
        assertEquals(75000.0, response.getMontoTotal());
    }
}