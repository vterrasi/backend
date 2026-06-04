package product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProductApplication {
    public static void main(String[] args) {
        // CORREGIDO: Aquí va ProductApplication.class, no el controlador
        SpringApplication.run(ProductApplication.class, args);
    }
}