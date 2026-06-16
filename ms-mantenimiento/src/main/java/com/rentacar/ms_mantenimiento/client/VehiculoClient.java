package com.rentacar.ms_mantenimiento.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-vehiculos")
public interface VehiculoClient {

    @GetMapping("/api/vehiculos/{id}")
    Object obtenerVehiculoPorId(@PathVariable("id") Long id);
}