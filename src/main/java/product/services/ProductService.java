package product.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import product.model.ProductModel;
import product.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    // Conectamos el mando a distancia real de la BD
    @Autowired
    private ProductRepository productRepository;

    // 1. OBTENER TODOS
    public List<ProductModel> obtenerTodosLosProductos() {
        return productRepository.findAll(); // Busca en MySQL directo
    }

    // 2. BUSCAR POR ID (¡JPA ya te devuelve un Optional perfecto!)
    public Optional<ProductModel> findById(String id) {
        return productRepository.findById(id);
    }

    // 3. GUARDAR (POST)
    public ProductModel guardarProducto(ProductModel nuevoProducto) {
        return productRepository.save(nuevoProducto);
    }

    // 4. ACTUALIZAR (PUT)
    public Optional<ProductModel> actualizarProducto(String id, ProductModel datosNuevos) {
        // Aprovechamos el Optional que nos da findById
        return productRepository.findById(id).map(productoViejo -> {
            productoViejo.setDesc(datosNuevos.getDesc());
            productoViejo.setPrice(datosNuevos.getPrice());
            return productRepository.save(productoViejo); // Guarda y devuelve el actualizado
        });
    }

    // 5. BORRAR (DELETE)
    public boolean borrarProducto(String id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }
}