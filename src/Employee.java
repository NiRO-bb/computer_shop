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
}