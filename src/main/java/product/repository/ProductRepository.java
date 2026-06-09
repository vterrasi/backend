package product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import product.model.ProductModel;

@Repository // <-- Le dice a Spring: "Soy el encargado de hablar con MySQL"
public interface ProductRepository extends JpaRepository<ProductModel, String> {
    // ¡No hace falta escribir ningún mét0do!
    // JpaRepository ya te regala gratis: save(), findAll(), findById(), deleteById()...
}