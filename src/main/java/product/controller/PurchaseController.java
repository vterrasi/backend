package product.controller;

import org.springframework.http.HttpStatus;
import product.model.PurchaseModel;
import product.services.PurchaseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// Cambiamos esto para que vaya por la misma zona que "users" y "products"
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
    @ResponseStatus(HttpStatus.CREATED) // <-- Añade esto para que devuelva un 201
    public PurchaseModel create(@RequestBody PurchaseModel purchase) {
        return purchaseService.createPurchase(purchase);
    }
}