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
import java.util.LinkedHashMap; // Importamos esto para el mapa limpio

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
        // CANDADO 2: ¿La ID de esta compra ya se usó antes?
        // ========================================================
        for (PurchaseModel p : purchaseList) {
            if (p.getId().equals(newPurchase.getId())) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Error: Ya existe una compra registrada con la ID '" + newPurchase.getId() + "'."
                );
            }
        }

        // ========================================================
        // CANDADO 3: ¿Todos los productos del mapa existen en el catálogo?
        // ========================================================
        Map<String, Integer> items = newPurchase.getPurchaseItems();

        if (items == null || items.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Error: La compra debe tener al menos un producto."
            );
        }

        for (String productoId : items.keySet()) {
            // Le quitamos los espacios fantasmas de los lados a la ID
            String idLimpia = productoId.trim();

            // ¡OJO AQUÍ! Buscamos usando 'idLimpia' (not productoId)
            if (productService.findById(idLimpia).isEmpty()) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Error: El producto con ID '" + idLimpia + "' no existe en el catálogo."
                );
            }
        }

        // ========================================================
        // PASO FINAL: Calcular el total de la compra (Amount)
        // Y LIMPIAR de verdad las claves con espacios del mapa
        // ========================================================
        float totalCompra = 0.0f;

        // Creamos un mapa nuevo, reluciente y vacío para meter las claves ya limpias
        Map<String, Integer> itemsLimpios = new LinkedHashMap<>();

        for (Map.Entry<String, Integer> entry : items.entrySet()) {
            // Volvemos a limpiar la ID para buscar de forma segura y guardarla peinada
            String idLimpia = entry.getKey().trim();
            Integer cantidad = entry.getValue();

            // Buscamos usando 'idLimpia' en nuestro catálogo
            Optional<ProductModel> productoOpcional = productService.findById(idLimpia);
            ProductModel producto = productoOpcional.get();

            // Sumamos al total: precio del producto multiplicado por la cantidad
            totalCompra += producto.getPrice() * cantidad;

            // ¡AQUÍ ESTÁ EL CAMBIAZO! Guardamos la ID limpia en el nuevo mapa
            itemsLimpios.put(idLimpia, cantidad);
        }

        // Le quitamos el mapa feo con espacios a la compra y le plantamos el limpio
        newPurchase.setPurchaseItems(itemsLimpios);

        // Le asignamos el total calculado a la compra
        newPurchase.setAmount(totalCompra);

        // Guardamos la compra en nuestra lista y la devolvemos
        purchaseList.add(newPurchase);
        return newPurchase;
    }
}