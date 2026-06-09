package product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
                result.isPresent() ? result.get() : "Producto no encontrado" ,
                result.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    // 1. GUARDAR UN PRODUCTO (POST)
    @PostMapping("/products") // Usa PostMapping para recibir datos
    public ResponseEntity<ProductModel> createProduct(@RequestBody ProductModel nuevoProducto) {
        // @RequestBody le dice a Spring: "Coge el JSON que viene en el cuerpo y conviértelo en un ProductModel"
        ProductModel productoCreado = productService.guardarProducto(nuevoProducto);

        // Respondemos con un estado 201 CREATED (Creado con éxito) y el producto
        return new ResponseEntity<>(productoCreado, HttpStatus.CREATED);
    }

    // 2. ACTUALIZAR UN PRODUCTO (PUT)
    @PutMapping("/products/{id}") // Usa PutMapping y necesita el ID en la URL
    public ResponseEntity<Object> updateProduct(@PathVariable String id, @RequestBody ProductModel datosNuevos) {
        Optional<ProductModel> resultado = productService.actualizarProducto(id, datosNuevos);

        return new ResponseEntity<>(
                resultado.isPresent() ? resultado.get() : "No se pudo actualizar, producto no encontrado",
                resultado.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    // 3. BORRAR UN PRODUCTO (DELETE)
    @DeleteMapping("/products/{id}") // Usa DeleteMapping y necesita el ID en la URL
    public ResponseEntity<String> deleteProduct(@PathVariable String id) {
        boolean borradoExitoso = productService.borrarProducto(id);

        if (borradoExitoso) {
            return new ResponseEntity<>("Producto eliminado correctamente", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No se pudo eliminar, producto no encontrado", HttpStatus.NOT_FOUND);
        }
    }
}