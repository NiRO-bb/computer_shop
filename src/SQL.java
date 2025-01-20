import java.math.RoundingMode;
import java.sql.*;
import java.util.ArrayList;

public class SQL {
    private static String URL = "jdbc:mysql://localhost:3306/niro_bb";
    private static String dbUsername = "niro_bb";
    private static String dbPassword = "2034";

    private static Connection conn;
    private static Statement statement;

    // Авторизация
    public static User getUser(String login, String pswd) throws SQLException {
        conn = DriverManager.getConnection(URL, dbUsername, dbPassword);
        statement = conn.createStatement();

        User user = null;
        ResultSet data = statement.executeQuery("select * from users where login = '%s' AND password = '%s'".formatted(login, pswd));

        while (data.next()) {
            user = new User(data.getString("login"),
                    data.getString("password"),
                    data.getString("code"));
        }

        conn.close();
        return user;
    }

    // Регистрация
    public static void addUser(String login, String password, String code) throws SQLException {
        conn = DriverManager.getConnection(URL, dbUsername, dbPassword);
        statement = conn.createStatement();

        statement.executeUpdate("insert into users(login, password, code) values ('%s', '%s', '%s')".formatted(login, password, code));

        conn.close();
    }

    // Занятость логина
    public static boolean checkLogin(String login) throws SQLException {
        conn = DriverManager.getConnection(URL, dbUsername, dbPassword);
        statement = conn.createStatement();

        ResultSet data = statement.executeQuery("select login from users where login = '%s'".formatted(login));

        boolean isExist = false;
        while(data.next())
            isExist = true;

        conn.close();
        return isExist;
    }

    // Добавить новый товар
    public static void addProduct(String id, String type, String model, String manufacturer, double price) throws SQLException {
        conn = DriverManager.getConnection(URL, dbUsername, dbPassword);
        statement = conn.createStatement();

        statement.executeUpdate("insert into product(id, type, model, manufacturer, price) values('%s', '%s', '%s', '%s', '%s')".formatted(id, type, model, manufacturer, price));

        conn.close();
    }

    // Добавить новый магазин
    public static void addShop(String id, String city, String street, int building) throws SQLException {
        conn = DriverManager.getConnection(URL, dbUsername, dbPassword);
        statement = conn.createStatement();

        statement.executeUpdate("insert into shop(id, city, street, building) values('%s', '%s', '%s', '%s')".formatted(id, city, street, building));

        conn.close();
    }

    // Получить список магазинов
    public static ArrayList<Object> getShopList() throws SQLException {
        conn = DriverManager.getConnection(URL, dbUsername, dbPassword);
        statement = conn.createStatement();

        ArrayList<Object> shops = new ArrayList<>();

        ResultSet data = statement.executeQuery("select * from shop");
        while (data.next()) {
            shops.add(new Shop(data.getString("id"),
                    data.getString("city"),
                    data.getString("street"),
                    data.getInt("building")));
        }

        conn.close();
        return shops;
    }

    // Получить список товаров
    public static ArrayList<Object> getProductList() throws SQLException {
        conn = DriverManager.getConnection(URL, dbUsername, dbPassword);
        statement = conn.createStatement();

        ArrayList<Object> products = new ArrayList<>();

        ResultSet data = statement.executeQuery("select * from product");
        while (data.next()) {
            products.add(new Product(data.getString("id"),
                    data.getString("type"),
                    data.getString("model"),
                    data.getString("manufacturer"),
                    data.getDouble("price")));
        }

        conn.close();
        return products;
    }

    // Получить список операций
    public static ArrayList<Object> getTransactionList() throws SQLException {
        conn = DriverManager.getConnection(URL, dbUsername, dbPassword);
        statement = conn.createStatement();

        ArrayList<Object> transactions = new ArrayList<>();

        ResultSet data = statement.executeQuery("select * from transaction");
        while (data.next()) {
            transactions.add(new Transaction(data.getInt("id"),
                    data.getString("type"),
                    data.getString("shop_id"),
                    data.getString("product_id"),
                    data.getInt("amount"),
                    data.getString("responsible")));
        }

        conn.close();
        return transactions;
    }

    // Получить список сотрудников
    public static ArrayList<Object> getEmployeeList() throws SQLException {
        conn = DriverManager.getConnection(URL, dbUsername, dbPassword);
        statement = conn.createStatement();

        ArrayList<Object> employees = new ArrayList<>();

        ResultSet data = statement.executeQuery("select * from employee");
        while (data.next()) {
            employees.add(new Employee(data.getString("id"),
                    data.getString("shop_id"),
                    data.getString("full_name"),
                    data.getString("post"),
                    data.getInt("salary"),
                    data.getString("login")));
        }

        conn.close();
        return employees;
    }

    // Получить id сотрудника по логину
    public static Employee getEmployee(String login) throws SQLException {
        conn = DriverManager.getConnection(URL, dbUsername, dbPassword);
        statement = conn.createStatement();

        Employee employee = null;

        ResultSet data = statement.executeQuery("select * from employee where login = '%s'".formatted(login));

        while (data.next()) {
            employee = new Employee(data.getString("id"),
                    data.getString("shop_id"),
                    data.getString("full_name"),
                    data.getString("post"),
                    data.getInt("salary"),
                    data.getString("login"));
        }

        conn.close();
        return employee;
    }

    // Добавить новую операцию
    public static void addTransaction(String type, String shopId, String productId, int amount, String responsible) throws SQLException {
        conn = DriverManager.getConnection(URL, dbUsername, dbPassword);
        statement = conn.createStatement();

        statement.executeUpdate("insert into transaction(type, shop_id, product_id, amount, responsible) values('%s', '%s', '%s', '%s', '%s')".formatted(type, shopId, productId, amount, responsible));

        conn.close();
    }

    // Добавить сотрудника
    public static void addEmployee(String id, String shopId, String name, String post, int salary, String login) throws SQLException {
        conn = DriverManager.getConnection(URL, dbUsername, dbPassword);
        statement = conn.createStatement();

        statement.executeUpdate("insert into employee(id, shop_id, full_name, post, salary, login) values('%s', '%s', '%s', '%s', '%s', '%s')".formatted(id, shopId, name, post, salary, login));

        conn.close();
    }

    // Удалить товар
    public static void deleteProduct(String id) throws SQLException {
        conn = DriverManager.getConnection(URL, dbUsername, dbPassword);
        statement = conn.createStatement();

        statement.executeUpdate("delete from product where id = '%s'".formatted(id));

        conn.close();
    }

    // Изменить товар
    public static void editProduct(Product p, String id) throws SQLException {
        conn = DriverManager.getConnection(URL, dbUsername, dbPassword);
        statement = conn.createStatement();

        statement.executeUpdate("update `product` set `id` = '%s', `type` = '%s', `model` = '%s', `manufacturer` = '%s', `price` = '%s' where `id` = '%s'".formatted(p.id, p.type, p.model, p.manufacturer, p.price, id));

        conn.close();
    }
}