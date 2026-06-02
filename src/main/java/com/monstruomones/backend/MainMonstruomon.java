package com.monstruomones.backend; // 👈 Comprueba que esta línea sea igual a la tuya

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // 👈 La pegatina mágica de Spring
public class MainMonstruomon { // 👈 Cambiado para que coincida exactamente con el nombre del archivo

    public static void main(String[] args) {
        // Esto enciende el motor de Spring Boot
        SpringApplication.run(MainMonstruomon.class, args);
    }
}