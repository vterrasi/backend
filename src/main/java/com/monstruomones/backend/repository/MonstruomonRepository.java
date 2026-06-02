package com.monstruomones.backend.repository;

import com.monstruomones.backend.model.Monstruomon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonstruomonRepository extends JpaRepository<Monstruomon, Integer> {
    // Al heredar de JpaRepository pasándole <Monstruomon, Integer>,
    // Spring ya sabe que este túnel maneja Monstruomones y que su ID es un número entero.
}