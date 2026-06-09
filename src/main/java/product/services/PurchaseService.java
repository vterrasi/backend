package product.services;

import product.model.PurchaseModel;
import product.model.ProductModel;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PurchaseService {

    // Nuestra base de datos temporal de compras en memoria
    private final List<PurchaseModel> purchaseList = new ArrayList<>();

    // Traemos los otros dos servicios para poder hacerles preguntas
    private final UserService userService;
    private final ProductService productService;

    // El constructor recibe ambos servicios de forma automática gracias a Spring
    public PurchaseService(UserService userService, ProductService productService) {
        this.userService = userService;
        this.productService = productService;
    }

    // Petición 1: Obtener todas las compras
    public List<PurchaseModel> getAllPurchases() {
        return purchaseList;
    }

    // Petición 2: Crear una compra con todas las de la ley
    public PurchaseModel createPurchase(PurchaseModel newPurchase) {

        // ========================================================
        // CANDADO 1: ¿Existe el usuario que intenta comprar?
        // ========================================================
        if (userService.findById(newPurchase.getUserID()).isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Error: El usuario con ID '" + newPurchase.getUserID() + "' no existe."
            );
        }

        // ========================================================
        // CANDADO 2: ¿La ID de esta compra ya está repetida?
        // ========================================================
        for (PurchaseModel compraExistente : purchaseList) {
            if (compraExistente.getId().equals(newPurchase.getId())) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Error: Ya existe una compra registrada con la ID '" + newPurchase.getId() + "'."
                );
            }
        }

        // ========================================================
        // CANDADO 3: ¿Existen todos los productos en el catálogo?
        // ========================================================
        Map<String, Integer> items = newPurchase.getPurchaseItems();

        for (String productoId : items.keySet()) {
            // Limpiamos los espacios rebeldes por si acaso
            String idLimpia = productoId.trim();

            // ¡OJO AQUÍ! Buscamos usando 'idLimpia' (no productoId)
            if (productService.findById(idLimpia).isEmpty()) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Error: El producto con ID '" + idLimpia + "' no existe en el catálogo."
                );
            }
        }

        // ========================================================
        // PASO FINAL: Calcular el total de la compra (Amount)
        // ========================================================
        float totalCompra = 0.0f;

        for (Map.Entry<String, Integer> entry : items.entrySet()) {
            // Volvemos a limpiar la ID para buscar de forma segura
            String idLimpia = entry.getKey().trim();
            Integer cantidad = entry.getValue();

            // ¡OJO AQUÍ! Buscamos usando 'idLimpia'
            Optional<ProductModel> productoOpcional = productService.findById(idLimpia);
            ProductModel producto = productoOpcional.get();

            // Sumamos al total: precio del producto multiplicado por la cantidad
            totalCompra += producto.getPrice() * cantidad;
        }

        // Le asignamos el total calculado a la compra
        newPurchase.setAmount(totalCompra);

        // Guardamos la compra en nuestra lista y la devolvemos
        purchaseList.add(newPurchase);
        return newPurchase;
    }
}