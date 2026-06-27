package com.rentacar.ms_notificaciones.service;

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
    private NotificacionRepository repo;
    
    @InjectMocks
    private NotificacionService service;

    private Notificacion notificacionMock;
    private NotificacionRequestDTO requestMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        notificacionMock = new Notificacion();
        notificacionMock.setId(1L);
        notificacionMock.setDestinatarioEmail("cliente@rentacar.cl");
        notificacionMock.setCanal("EMAIL");
        
        requestMock = new NotificacionRequestDTO();
        requestMock.setDestinatarioEmail("cliente@rentacar.cl");
        requestMock.setCanal("EMAIL");
    }

    @Test
    void registrarNotificacionExitosaTest() {
        when(repo.save(any(Notificacion.class))).thenReturn(notificacionMock);

        NotificacionResponseDTO response = service.registrarNotificacion(requestMock);

        assertNotNull(response);
        assertEquals("EMAIL", response.getCanal());
        assertEquals("cliente@rentacar.cl", response.getDestinatarioEmail());
    }
}