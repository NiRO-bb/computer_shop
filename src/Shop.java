public class Shop {
    // добавить модификатор доступа
    String id;
    String city;
    String street;
    int building;

    public Shop() {};

    public Shop(String id, String city, String street, int building) {
        this.id = id;
        this.city = city;
        this.street = street;
        this.building = building;
    }

    public String toString() {
        return "ID - %s, г. %s, ул. %s, д. %s".formatted(id, city, street, building);
    }
}