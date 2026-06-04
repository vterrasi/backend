package product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import product.model.ProductModel;
import product.services.ProductService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/apicurso") // Corregido el espacio en blanco
public class ProductController {

    // Conectamos el servicio. Spring se encarga de buscarlo y meterlo aquí.
    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<ProductModel>> getProducts() {
        // El controlador ya no inventa una lista vacía, se la pide al servicio

        return new ResponseEntity<>(
            productService.obtenerTodosLosProductos()
            , HttpStatus.OK
        );
    }
    @GetMapping ("/products/{id}")
    public ResponseEntity<Object> getProductById(
        @PathVariable String id
    ) {
        Optional<ProductModel> result = productService.findById(id);

        return new ResponseEntity<>(
                result.isPresent() ? result.get() : "producto no encontrado" ,
                result.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }
}