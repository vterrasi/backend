package com.monstruomones.backend.repository;

import com.monstruomones.backend.model.Ataque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AtaqueRepository extends JpaRepository<Ataque, Integer> {
    // ¡No hace falta escribir nada más aquí dentro!
    // Magia: Spring ya sabe hacer el CRUD (Guardar, buscar, borrar y editar) por heredar de JpaRepository
}