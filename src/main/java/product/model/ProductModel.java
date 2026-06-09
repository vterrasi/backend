package product.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity // <-- ¡MÁGICO! Esto le dice a Hibernate que cree la tabla en MySQL
@Table(name = "products") // <-- Así se llamará la tabla en tu base de datos
public class ProductModel {

    @Id // <-- Le dice que el "id" es la clave primaria (única)
    private String id;
    @jakarta.persistence.Column(name = "descripcion")
    private String desc;
    private float price;

    // Constructor vacío estándar (¡Súper obligatorio para Jackson y Hibernate!)
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