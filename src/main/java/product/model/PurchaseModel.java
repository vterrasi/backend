package product.model;

import java.util.Map;

public class PurchaseModel {
    private String id;
    private String userID;

    // 1. CAMBIAMOS ESTA LÍNEA (Ponemos la F mayúscula)
    private Float amount;

    private Map<String, Integer> purchaseItems;

    public PurchaseModel() {
    }

    public PurchaseModel(String id, String userID, Float amount, Map<String, Integer> purchaseItems) {
        this.id = id;
        this.userID = userID;
        this.amount = amount;
        this.purchaseItems = purchaseItems;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserID() { return userID; }
    public void setUserID(String userID) { this.userID = userID; }

    // 2. CAMBIAMOS TAMBIÉN EL GETTER Y SETTER (Con F mayúscula)
    public Float getAmount() { return amount; }
    public void setAmount(Float amount) { this.amount = amount; }

    public Map<String, Integer> getPurchaseItems() { return purchaseItems; }
    public void setPurchaseItems(Map<String, Integer> purchaseItems) { this.purchaseItems = purchaseItems; }
}