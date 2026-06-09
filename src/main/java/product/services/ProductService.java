package product.services;

import org.springframework.stereotype.Service;
import product.model.ProductModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    // 1. Creamos nuestra lista estática en memoria igual que en UserService
    private static final List<ProductModel> productosDB = new ArrayList<>();

    // 2. Metemos tus productos de prueba al arrancar el sistema
    static {
        productosDB.add(new ProductModel("P01", "Termo con asa rosa-violeta", 15.50f));
        productosDB.add(new ProductModel("P02", "Almohadilla gris para el ratón", 4.00f));
        productosDB.add(new ProductModel("P03", "Monitor de 20 pulgadas Dell", 20.00f));
        productosDB.add(new ProductModel("P04", "Ventilador portátil recargable USB", 5.00f));
    }

    // Comentamos el repositorio para que no intente ir a MySQL de momento
    // @Autowired
    // private ProductRepository productRepository;

    // 3. OBTENER TODOS (Ahora los lee de la lista)
    public List<ProductModel> obtenerTodosLosProductos() {
        return productosDB;
    }

    // 4. BUSCAR POR ID (Buscamos manualmente en nuestra lista)
    public Optional<ProductModel> findById(String id) {
        for (ProductModel p : productosDB) {
            if (p.getId().equals(id)) {
                return Optional.of(p); // Lo encontramos
            }
        }
        return Optional.empty(); // No existe
    }

    // 5. GUARDAR (Por si quieres añadir nuevos desde Postman/Thunder Client)
    public ProductModel guardarProducto(ProductModel nuevoProducto) {
        productosDB.add(nuevoProducto);
        return nuevoProducto;
    }

    // 6. ACTUALIZAR
    public Optional<ProductModel> actualizarProducto(String id, ProductModel datosNuevos) {
        for (ProductModel p : productosDB) {
            if (p.getId().equals(id)) {
                p.setDesc(datosNuevos.getDesc());
                p.setPrice(datosNuevos.getPrice());
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }

    // 7. BORRAR
    public boolean borrarProducto(String id) {
        return productosDB.removeIf(p -> p.getId().equals(id));
    }
}