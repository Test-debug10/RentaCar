package com.rentacar.ms_evaluaciones.service;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.springframework.boot.test.context.SpringBootTest;

import com.rentacar.ms_evaluaciones.client.ReservaClient;
import com.rentacar.ms_evaluaciones.dto.EvaluacionRequestDTO;
import com.rentacar.ms_evaluaciones.dto.EvaluacionResponseDTO;
import com.rentacar.ms_evaluaciones.model.Evaluacion;
import com.rentacar.ms_evaluaciones.repository.EvaluacionRepository;

@SpringBootTest
public class EvaluacionServiceTest {

    @Mock
    private EvaluacionRepository evaluacionRepository;

    @Mock
    private ReservaClient reservaClient;

    @InjectMocks
    private EvaluacionService evaluacionService;

    private Evaluacion evaluacionMock;
    private EvaluacionRequestDTO requestMock;

    @BeforeEach
    void setUp() {
        evaluacionMock = new Evaluacion();
        evaluacionMock.setId(1L);
        evaluacionMock.setReservaId(100L);
        evaluacionMock.setCalificacion(5);
        evaluacionMock.setComentario("Excelente vehículo y servicio impecable.");
        evaluacionMock.setFechaEvaluacion(LocalDate.now());

        requestMock = new EvaluacionRequestDTO();
        requestMock.setReservaId(100L);
        requestMock.setCalificacion(5);
        requestMock.setComentario("Excelente vehículo y servicio impecable.");
    }

    @Test
    void registrarEvaluacionExitosaTest() {
        // Given
        when(reservaClient.obtenerReservaPorId(100L)).thenReturn(new Object());
        when(evaluacionRepository.save(any(Evaluacion.class))).thenReturn(evaluacionMock);

        // When
        EvaluacionResponseDTO response = evaluacionService.registrarEvaluacion(requestMock);

        // Then
        assertNotNull(response);
        assertEquals(5, response.getCalificacion());
        assertEquals(100L, response.getReservaId());
    }
}