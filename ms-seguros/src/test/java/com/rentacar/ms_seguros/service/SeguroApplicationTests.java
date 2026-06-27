package com.rentacar.ms_seguros.service;

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

import com.rentacar.ms_seguros.client.ReservaClient;
import com.rentacar.ms_seguros.dto.SeguroRequestDTO;
import com.rentacar.ms_seguros.dto.SeguroResponseDTO;
import com.rentacar.ms_seguros.model.Seguro;
import com.rentacar.ms_seguros.repository.SeguroRepository;

@SpringBootTest
public class SeguroApplicationTests {

    @Mock 
    private SeguroRepository seguroRepository;
    
    @Mock 
    private ReservaClient reservaClient;
    
    @InjectMocks 
    private SeguroService seguroService;

    private Seguro seguroMock;
    private SeguroRequestDTO requestMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        seguroMock = new Seguro();
        seguroMock.setId(1L);
        seguroMock.setReservaId(10L);
        seguroMock.setTipoSeguro("COBERTURA_TOTAL");
        seguroMock.setCoberturaMaxima(5000000.0);
        seguroMock.setCostoAdicional(25000.0);

        requestMock = new SeguroRequestDTO();
        requestMock.setReservaId(10L);
        requestMock.setTipoSeguro("COBERTURA_TOTAL");
        requestMock.setCoberturaMaxima(5000000.0);
        requestMock.setCostoAdicional(25000.0);
    }

    @Test
    void registrarSeguroExitosoTest() {
        when(reservaClient.obtenerReservaPorId(10L)).thenReturn(new Object());
        when(seguroRepository.save(any(Seguro.class))).thenReturn(seguroMock);

        SeguroResponseDTO response = seguroService.registrarSeguro(requestMock);

        assertNotNull(response);
        assertEquals("COBERTURA_TOTAL", response.getTipoSeguro());
        assertEquals(25000.0, response.getCostoAdicional());
    }
}