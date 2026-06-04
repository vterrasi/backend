package product.model;

public class ProductModel {
    private String id;
    private String desc;
    private float price;

    // Constructor vacío estándar para Spring/Jackson
    public ProductModel() {
    }

    public ProductModel(String id, String desc, float price) {
        this.id = id;
        this.desc = desc;
        this.price = price;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getDesc() { return desc; }
    public void setDesc(String desc) { this.desc = desc; }

    public float getPrice() { return price; }
    public void setPrice(float price) { this.price = price; }
}