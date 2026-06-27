package com.rentacar.ms_mantenimiento.service;

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

import com.rentacar.ms_mantenimiento.client.VehiculoClient;
import com.rentacar.ms_mantenimiento.model.Mantenimiento;
import com.rentacar.ms_mantenimiento.repository.MantenimientoRepository;

@SpringBootTest
public class MantenimientoApplicationTests {

    @Mock
    private MantenimientoRepository repo;
    
    @Mock
    private VehiculoClient client;
    
    @InjectMocks private MantenimientoService service;

    private Mantenimiento mantMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mantMock = new Mantenimiento();
        mantMock.setVehiculoId(5L);
        mantMock.setTipo("PREVENTIVO");
        mantMock.setCosto(45000.0);
    }

    @Test
    void registrarMantenimientoExitosoTest() {
        when(client.obtenerVehiculoPorId(5L)).thenReturn(new Object());
        when(repo.save(any(Mantenimiento.class))).thenReturn(mantMock);

        Mantenimiento response = service.save(mantMock);

        assertNotNull(response);
        assertEquals(45000.0, response.getCosto());
    }
}