package com.monstruomones.backend.controller;

import com.monstruomones.backend.model.Monstruomon;
import com.monstruomones.backend.repository.MonstruomonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// 1. Le decimos a Spring que este archivo es un Controlador Web (un camarero de API)
@RestController
// 2. Esta es la calle principal del restaurante. Cualquier petición que empiece por /api va a venir aquí
@RequestMapping("/api")
public class MonstruomonController {

    // 3. El "Autowired" es magia pura: le dice a Spring que nos traiga las herramientas de la cocina automáticamente
    @Autowired
    private MonstruomonRepository monstruomonRepository;

    // 4. Creamos una ventanilla específica: cuando alguien entre en "http://localhost:8080/api/monstruomones"
    @GetMapping("/monstruomones")
    public List<Monstruomon> obtenerTodosLosMonstruomones() {
        // 5. El camarero corre a la cocina (Repository), le pide la lista completa a MySQL y se la devuelve al cliente
        return monstruomonRepository.findAll();
    }
}