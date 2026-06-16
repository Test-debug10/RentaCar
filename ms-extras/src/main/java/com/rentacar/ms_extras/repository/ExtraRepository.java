package com.rentacar.ms_extras.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rentacar.ms_extras.model.Extra;

@Repository
public interface ExtraRepository extends JpaRepository<Extra, Long> {
}