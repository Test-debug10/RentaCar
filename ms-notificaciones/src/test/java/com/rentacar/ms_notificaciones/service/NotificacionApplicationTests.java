package com.rentacar.ms_notificaciones.service;

import java.time.LocalDateTime;

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

import com.rentacar.ms_notificaciones.dto.NotificacionRequestDTO;
import com.rentacar.ms_notificaciones.dto.NotificacionResponseDTO;
import com.rentacar.ms_notificaciones.model.Notificacion;
import com.rentacar.ms_notificaciones.repository.NotificacionRepository;

@SpringBootTest
public class NotificacionApplicationTests {

    @Mock
    private NotificacionRepository notificacionRepository;

    @InjectMocks
    private NotificacionService notificacionService;

    private Notificacion notificacionMock;
    private NotificacionRequestDTO requestMock;

    @BeforeEach
    void setUp() {
        // Inicializa los componentes de simulación antes de ejecutar cada prueba
		MockitoAnnotations.openMocks(this);

        notificacionMock = new Notificacion();
        notificacionMock.setId(1L);
        notificacionMock.setReservaId(150L);
        notificacionMock.setDestinatarioEmail("cliente@rentacar.cl");
        notificacionMock.setMensaje("Tu reserva ha sido confirmada.");
        notificacionMock.setCanal("EMAIL");
        notificacionMock.setFechaEnvio(LocalDateTime.now());

        requestMock = new NotificacionRequestDTO();
        requestMock.setReservaId(150L);
        requestMock.setDestinatarioEmail("cliente@rentacar.cl");
        requestMock.setMensaje("Tu reserva ha sido confirmada.");
        requestMock.setCanal("EMAIL");
    }

    @Test
    void registrarNotificacionExitosaTest() {
        // Given
        when(notificacionRepository.save(any(Notificacion.class))).thenReturn(notificacionMock);

        // When
        NotificacionResponseDTO response = notificacionService.registrarNotificacion(requestMock);

        // Then
        assertNotNull(response);
        assertEquals("EMAIL", response.getCanal());
        assertEquals("cliente@rentacar.cl", response.getDestinatarioEmail());
    }
}