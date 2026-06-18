package com.rentacar.ms_seguros.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-reservas")
public interface ReservaClient {

    @GetMapping("/api/v2/reservas/{id}")
    Object obtenerReservaPorId(@PathVariable("id") Long id);
}