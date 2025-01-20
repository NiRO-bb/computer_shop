public class Product {
    // добавить модификаторы доступа
    String id;
    String type;
    String model;
    String manufacturer;
    double price;

    public Product() {}

    public Product (String id, String type, String model, String manufacturer, double price) {
        this.id = id;
        this.type = type;
        this.model = model;
        this.manufacturer = manufacturer;
        this.price = price;
    }

    public String toString() {
        return "%s %s - %s".formatted(type, model, price);
    }
}