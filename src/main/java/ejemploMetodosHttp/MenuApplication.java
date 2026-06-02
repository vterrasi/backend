package ejemploMetodosHttp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // <-- Esto le dice a Java: "¡Soy un proyecto Spring Boot!"
public class MenuApplication {
    public static void main(String[] args) {
        SpringApplication.run(MenuApplication.class, args); // <-- Esto enciende el servidor
    }
}