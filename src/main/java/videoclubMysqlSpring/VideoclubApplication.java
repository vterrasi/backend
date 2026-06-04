package videoclubMysqlSpring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // <- Esta es la varita mágica que enciende todo Spring
public class VideoclubApplication {

    public static void main(String[] args) {
        // Le decimos a Spring que arranque toda la maquinaria desde aquí
        SpringApplication.run(VideoclubApplication.class, args);
    }
}