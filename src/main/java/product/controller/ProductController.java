package product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import product.model.ProductModel;
import product.services.ProductService;

import java.util.List;

@RestController
@RequestMapping("/apicurso") // Corregido el espacio en blanco
public class ProductController {

    // Conectamos el servicio. Spring se encarga de buscarlo y meterlo aquí.
    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public List<ProductModel> getProducts() {
        // El controlador ya no inventa una lista vacía, se la pide al servicio
        return productService.obtenerTodosLosProductos();
    }
}