package com.rentacar.ms_extras.service;

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

import com.rentacar.ms_extras.dto.ExtraRequestDTO;
import com.rentacar.ms_extras.dto.ExtraResponseDTO;
import com.rentacar.ms_extras.model.Extra;
import com.rentacar.ms_extras.repository.ExtraRepository;

@SpringBootTest
public class ExtraApplicationTests {

    @Mock
    private ExtraRepository extraRepository;

    @InjectMocks
    private ExtraService extraService;

    private Extra extraMock;
    private ExtraRequestDTO requestMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        extraMock = new Extra();
        extraMock.setId(1L);
        extraMock.setNombre("Silla de Bebé");
        extraMock.setDescripcion("Silla homologada para niños de 0 a 3 años");
        extraMock.setPrecioDiario(5000.0);
        extraMock.setStockTotal(15);

        requestMock = new ExtraRequestDTO();
        requestMock.setNombre("Silla de Bebé");
        requestMock.setDescripcion("Silla homologada para niños de 0 a 3 años");
        requestMock.setPrecioDiario(5000.0);
        requestMock.setStockTotal(15);
    }

    @Test
    void registrarExtraTest() {
        // Given
        when(extraRepository.save(any(Extra.class))).thenReturn(extraMock);

        // When
        ExtraResponseDTO response = extraService.registrarExtra(requestMock);

        // Then
        assertNotNull(response);
        assertEquals("Silla de Bebé", response.getNombre());
        assertEquals(5000.0, response.getPrecioDiario());
        assertEquals(15, response.getStockTotal());
    }
}