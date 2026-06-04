package product.services;

import org.springframework.stereotype.Service;
import product.model.ProductModel;

import java.util.ArrayList;
import java.util.List;

@Service // <-- Le dice a Spring: "¡Soy el cocinero que maneja la lógica!"
public class ProductService {

    // Simulamos una base de datos con una lista guardada aquí mismo
    private final List<ProductModel> listaProductos = new ArrayList<>();

    // Usamos un constructor para meter un par de productos de prueba al nacer el servicio
    public ProductService() {
        listaProductos.add(new ProductModel("1", "Teclado Mecánico RGB", 49.99f));
        listaProductos.add(new ProductModel("2", "Ratón Gaming Inalámbrico", 29.99f));
        listaProductos.add(new ProductModel("3", "Monitor 24 Pulgadas FHD", 119.50f));
    }

    // El método que el controlador llamará para pedir la lista
    public List<ProductModel> obtenerTodosLosProductos() {
        return listaProductos;
    }
}