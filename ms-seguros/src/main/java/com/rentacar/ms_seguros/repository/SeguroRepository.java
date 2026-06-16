package com.rentacar.ms_seguros.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rentacar.ms_seguros.model.Seguro;

@Repository
public interface SeguroRepository extends JpaRepository<Seguro, Long> {
}