package product.services;

import org.springframework.stereotype.Service;
import product.model.ProductModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    // El mét0do que el controlador llamará para pedir la lista
    public List<ProductModel> obtenerTodosLosProductos() {
        return listaProductos;
    }

    public Optional<ProductModel> findById(String id) {
        Optional<ProductModel> result = Optional.empty();

        for (ProductModel productModel : listaProductos)

            if ((productModel.getId().equals(id))){
                result = Optional.of(productModel);
            }

        return result;
    }

    // 1. MÉT0DO PARA GUARDAR UN PRODUCTO NUEVO (POST)
    public ProductModel guardarProducto(ProductModel nuevoProducto) {
        // Añadimos el producto que nos llega a nuestra lista simulada
        listaProductos.add(nuevoProducto);
        // Por buena práctica, devolvemos el producto que se acaba de guardar
        return nuevoProducto;
    }

    // 2. MÉT0DO PARA ACTUALIZAR UN PRODUCTO (PUT)
    public Optional<ProductModel> actualizarProducto(String id, ProductModel datosNuevos) {
        // Primero buscamos si el producto existe usando el mét0do que ya tenías
        Optional<ProductModel> productoEncontrado = findById(id);

        // Si existe, le cambiamos los datos viejos por los nuevos
        if (productoEncontrado.isPresent()) {
            ProductModel productoViejo = productoEncontrado.get();
            productoViejo.setDesc(datosNuevos.getDesc());
            productoViejo.setPrice(datosNuevos.getPrice());
        }

        return productoEncontrado; // Devolvemos el producto modificado (o vacío si no existía)
    }

    // 3. MÉT0DO PARA BORRAR UN PRODUCTO (DELETE)
    public boolean borrarProducto(String id) {
        // Intentamos borrar de la lista el producto que coincida con ese ID
        // removeIf devuelve true si encontró el producto y lo borró, o false si no hizo nada
        return listaProductos.removeIf(producto -> producto.getId().equals(id));
    }

}