package product.model;

public class UserModel {
    // Atributos obligatorios que pide la práctica
    private String id;
    private String name;
    private int age;

    // Constructor vacío (obligatorio para que Spring maneje los JSON)
    public UserModel() {
    }

    // Constructor con parámetros para crear usuarios fácilmente
    public UserModel(String id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    // Getters y Setters (Las portezuelas para leer y escribir los datos)
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
}