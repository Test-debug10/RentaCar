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
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.rentacar.ms_evaluaciones.client.ReservaClient;
import com.rentacar.ms_evaluaciones.model.Evaluacion;
import com.rentacar.ms_evaluaciones.repository.EvaluacionRepository;

@SpringBootTest
public class EvaluacionApplicationTests {

    @Mock
    private EvaluacionRepository evaluacionRepository;

    @Mock
    private ReservaClient reservaClient;

    @InjectMocks
    private EvaluacionService evaluacionService;

    private Evaluacion evaluacionMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        evaluacionMock = new Evaluacion();
        evaluacionMock.setId(1L);
        evaluacionMock.setReservaId(100L);
        evaluacionMock.setCalificacion(5);
        evaluacionMock.setComentario("Excelente vehículo y servicio impecable.");
        evaluacionMock.setFechaEvaluacion(LocalDate.now());
    }

    @Test
    void registrarEvaluacionExitosaTest() {
        when(reservaClient.obtenerReservaPorId(100L)).thenReturn(new Object());
        when(evaluacionRepository.save(any(Evaluacion.class))).thenReturn(evaluacionMock);

        Evaluacion response = evaluacionService.save(evaluacionMock);

        assertNotNull(response);
        assertEquals(5, response.getCalificacion());
        assertEquals(100L, response.getReservaId());
    }
}