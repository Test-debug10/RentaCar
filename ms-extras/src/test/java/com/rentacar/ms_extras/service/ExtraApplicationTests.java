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

import com.rentacar.ms_extras.model.Extra;
import com.rentacar.ms_extras.repository.ExtraRepository;

@SpringBootTest
public class ExtraApplicationTests {

    @Mock
    private ExtraRepository extraRepository;

    @InjectMocks
    private ExtraService extraService;

    private Extra extraMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        extraMock = new Extra();
        extraMock.setId(1L);
        extraMock.setNombre("Silla de Bebé");
        extraMock.setDescripcion("Silla homologada para niños de 0 a 3 años");
        extraMock.setPrecioDiario(5000.0);
        extraMock.setStockTotal(15);
    }

    @Test
    void registrarExtraTest() {
        when(extraRepository.save(any(Extra.class))).thenReturn(extraMock);

        Extra response = extraService.save(extraMock);

        assertNotNull(response);
        assertEquals("Silla de Bebé", response.getNombre());
        assertEquals(5000.0, response.getPrecioDiario());
        assertEquals(15, response.getStockTotal());
    }
}