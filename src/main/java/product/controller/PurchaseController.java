package product.controller;

import product.model.PurchaseModel;
import product.services.PurchaseService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/apicurso/purchases")
public class PurchaseController {

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping
    public List<PurchaseModel> getAll() {
        return purchaseService.getAllPurchases();
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody PurchaseModel purchase) {
        try {
            // Intentamos hacer la compra normal
            PurchaseModel compraCreada = purchaseService.createPurchase(purchase);
            // Si sale bien, devolvemos un 201 CREATED con la compra calculada
            return new ResponseEntity<>(compraCreada, HttpStatus.CREATED);
        } catch (ResponseStatusException e) {
            // ¡AQUÍ ESTÁ EL TRUCO!
            // Si salta un candado del Service, agarramos tu mensaje (e.getReason())
            // y obligamos a Thunder Client a pintarlo en la pantalla
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
    }
}