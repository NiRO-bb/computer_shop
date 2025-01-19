public class Employee {
    // указать модификаторы доступа
    String id;
    String shopId;
    String name;
    String post;
    int salary;
    String login;

    public Employee (String id, String shopId, String name, String post, int salary, String login) {
        this.id = id;
        this.shopId = shopId;
        this.name = name;
        this.post = post;
        this.salary = salary;
        this.login = login;
    }

    public String getId() {
        return id;
    }

    public String toString() {
        return "ID - %s, магазин - %s, ФИО - %s, должность - %s, зарплата - %s руб., логин - %s".formatted(id, shopId, name, post, salary, login);
    }
}