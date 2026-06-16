package com.rentacar.ms_pagos.service;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.springframework.boot.test.context.SpringBootTest;

import com.rentacar.ms_pagos.dto.PagoRequestDTO;
import com.rentacar.ms_pagos.dto.PagoResponseDTO;
import com.rentacar.ms_pagos.model.Pago;
import com.rentacar.ms_pagos.repository.PagoRepository;

@SpringBootTest
public class PagoServiceTest {

    @Mock
    private PagoRepository pagoRepository;

    @InjectMocks
    private PagoService pagoService;

    private Pago pagoMock;
    private PagoRequestDTO requestMock;

    @BeforeEach
    void setUp() {
        pagoMock = new Pago();
        pagoMock.setId(1L);
        pagoMock.setReservaId(15L);
        pagoMock.setMonto(150000.0);
        pagoMock.setMetodoPago("TARJETA_CREDITO");
        pagoMock.setEstado("COMPLETADO");
        pagoMock.setFechaPago(LocalDateTime.now());

        requestMock = new PagoRequestDTO();
        requestMock.setReservaId(15L);
        requestMock.setMonto(150000.0);
        requestMock.setMetodoPago("TARJETA_CREDITO");
    }

    @Test
    void procesarPagoTest() {
        // Given
        when(pagoRepository.save(any(Pago.class))).thenReturn(pagoMock);

        // When
        PagoResponseDTO response = pagoService.procesarPago(requestMock);

        // Then
        assertNotNull(response);
        assertEquals("COMPLETADO", response.getEstado());
        assertEquals(150000.0, response.getMonto());
        assertEquals("TARJETA_CREDITO", response.getMetodoPago());
    }
}