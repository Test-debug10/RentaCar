package com.rentacar.ms_reservas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rentacar.ms_reservas.model.Reserva;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
}