package com.rentacar.ms_reservas.service;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.rentacar.ms_reservas.client.VehiculoClient;
import com.rentacar.ms_reservas.client.VehiculoDTO;
import com.rentacar.ms_reservas.dto.ReservaRequestDTO;
import com.rentacar.ms_reservas.dto.ReservaResponseDTO;
import com.rentacar.ms_reservas.model.Reserva;
import com.rentacar.ms_reservas.repository.ReservaRepository;

@SpringBootTest
public class ReservaApplicationTests {

    @Mock 
    private ReservaRepository reservaRepository;
    
    @Mock
    private VehiculoClient vehiculoClient;
    
    @InjectMocks
    private ReservaService reservaService;

    private Reserva reservaMock;
    private ReservaRequestDTO requestMock;
    private VehiculoDTO vehiculoMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
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
        when(vehiculoClient.obtenerVehiculoPorId(1L)).thenReturn(vehiculoMock);
        when(reservaRepository.save(any(Reserva.class))).thenReturn(reservaMock);

        ReservaResponseDTO response = reservaService.crearReserva(requestMock);
        
        assertNotNull(response);
        assertEquals(75000.0, response.getMontoTotal());
    }
}