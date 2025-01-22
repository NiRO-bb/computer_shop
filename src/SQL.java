import java.sql.*;
import java.util.ArrayList;

public class SQL {
    //private static String URL = "jdbc:mysql://localhost:3306/niro_bb";
    //private static String dbUsername = "niro_bb";
    //private static String dbPassword = "2034";

    private static String URL = "jdbc:h2:C:/Users/nikita/IdeaProjects/DB project/db/stockExchange";

    private static Connection conn;
    private static Statement statement;

    // Получить код доступа пользователя по логину
    public static User getUser(String login) throws SQLException {
        
        conn = DriverManager.getConnection(URL);
        statement = conn.createStatement();

        ResultSet data = statement.executeQuery("select * from users where login = '%s'".formatted(login));

        User user = null;
        while (data.next()) {
            user = new User(data.getString("login"), data.getString("password"), data.getString("code"));
        }

        conn.close();
        return user;
    }
    // Проверить соответствие логина и пароля при авторизации
    public static User getUser(String login, String pswd) throws SQLException {
        
        conn = DriverManager.getConnection(URL);
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

    // Получить id магазина по логину
    public static Shop getUserShop(String login) throws SQLException {
        
        conn = DriverManager.getConnection(URL);
        statement = conn.createStatement();

        ResultSet data = statement.executeQuery("select * from shop inner join employee on shop.id = employee.shop_id where login = '%s'".formatted(login));

        Shop shop = null;
        while (data.next()) {
            shop = new Shop(data.getString("id"), data.getString("city"), data.getString("street"), data.getInt("building"));
        }

        conn.close();
        return shop;
    }

    // Регистрация
    public static void addUser(String login, String password, String code) throws SQLException {
        
        conn = DriverManager.getConnection(URL);
        statement = conn.createStatement();

        statement.executeUpdate("insert into users(login, password, code) values ('%s', '%s', '%s')".formatted(login, password, code));

        conn.close();
    }

    // Занятость логина
    public static boolean checkLogin(String login) throws SQLException {
        
        conn = DriverManager.getConnection(URL);
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
        
        conn = DriverManager.getConnection(URL);
        statement = conn.createStatement();

        statement.executeUpdate("insert into product(id, type, model, manufacturer, price) values('%s', '%s', '%s', '%s', '%s')".formatted(id, type, model, manufacturer, price));

        conn.close();
    }

    // Добавить новый экземпляр товара
    public static String addProductCopy(String shopId, String productId) throws SQLException {
        
        conn = DriverManager.getConnection(URL);
        statement = conn.createStatement();

        // получить список артикулов для конкретного товара данного магазина
        ResultSet data = statement.executeQuery("select article from product_copy where product_id = '%s' and shop_id = '%s'".formatted(productId, shopId));

        // найти минимальный свободный артикул, чтобы автоматически назначить его новому экземпляру
        int num = 0;
        ArrayList<Integer> nums = new ArrayList<>();
        while (data.next()) {
            String[] articleArray = data.getString("article").split("_");
            nums.add(Integer.parseInt(articleArray[2]));
        }

        while (true) {
            if (!nums.contains(++num)) {
                break;
            }
        }

        // добавить экземпляр в бд
        String article = "%s_%s_%s".formatted(shopId, productId, num);
        statement.executeUpdate("insert into product_copy(article, product_id, shop_id) values('%s', '%s', '%s')".formatted(article, productId, shopId));

        conn.close();
        return article;
    }

    // Добавить новый магазин
    public static void addShop(String id, String city, String street, int building) throws SQLException {
        
        conn = DriverManager.getConnection(URL);
        statement = conn.createStatement();

        statement.executeUpdate("insert into shop(id, city, street, building) values('%s', '%s', '%s', '%s')".formatted(id, city, street, building));

        conn.close();
    }

    // Получить список магазинов
    public static ArrayList<Object> getShopList() throws SQLException {
        
        conn = DriverManager.getConnection(URL);
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
    // Получить список магазинов по id продукта
    public static ArrayList<Shop> getShopList(Product product) throws SQLException {
        
        conn = DriverManager.getConnection(URL);
        statement = conn.createStatement();

        ArrayList<Shop> shops = new ArrayList<>();

        ResultSet data = statement.executeQuery("select * from shop inner join product_copy on shop.id = product_copy.shop_id where product_copy.product_id = '%s'".formatted(product.id));

        while (data.next()) {
            Shop shop = new Shop(data.getString("id"),
                    data.getString("city"),
                    data.getString("street"),
                    data.getInt("building"));

            boolean isAdded = false;

            for (Shop s : shops) {
                if (s.toString().equals(shop.toString())) {
                    isAdded = true;
                    break;
                }
            }

            if (!isAdded) shops.add(shop);
        }

        conn.close();
        return shops;
    }

    // Получить список товаров
    public static ArrayList<Object> getProductList() throws SQLException {
        
        conn = DriverManager.getConnection(URL);
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

    // Получить список экземпляров
    public static ArrayList<Object> getProductCopyList(Shop shop) throws SQLException {
        
        conn = DriverManager.getConnection(URL);
        statement = conn.createStatement();

        ArrayList<Object> copies = new ArrayList<>();

        ResultSet data = statement.executeQuery("select * from product_copy where shop_id = '%s'".formatted(shop.id));
        while (data.next()) {
            copies.add(new ProductCopy(data.getString("article"),
                    data.getString("product_id"),
                    data.getString("shop_id")));
        }

        conn.close();
        return copies;
    }

    // Получить список операций
    public static ArrayList<Object> getTransactionList() throws SQLException {
        
        conn = DriverManager.getConnection(URL);
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
    // Получить список операций по id магазина
    public static ArrayList<Object> getTransactionList(Shop shop) throws SQLException {
        
        conn = DriverManager.getConnection(URL);
        statement = conn.createStatement();

        ArrayList<Object> transactions = new ArrayList<>();

        ResultSet data = statement.executeQuery("select * from transaction where shop_id = '%s'".formatted(shop.id));
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
        
        conn = DriverManager.getConnection(URL);
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
    
    // Получить список экземпляров
    public static ArrayList<Object> getProductCopyList(String shopId, String productId) throws SQLException {
        conn = DriverManager.getConnection(URL);
        statement = conn.createStatement();

        ArrayList<Object> copies = new ArrayList<>();

        ResultSet data = statement.executeQuery("select * from product_copy where product_id = '%s' and shop_id = '%s'".formatted(productId, shopId));

        while (data.next()) {
            copies.add(new ProductCopy(data.getString("article"),
                    data.getString("product_id"),
                    data.getString("shop_id")));
        }

        conn.close();
        return copies;
    }

    // Получить id сотрудника по логину
    public static Employee getEmployee(String login) throws SQLException {
        conn = DriverManager.getConnection(URL);
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
        conn = DriverManager.getConnection(URL);
        statement = conn.createStatement();

        statement.executeUpdate("insert into transaction(type, shop_id, product_id, amount, responsible) values('%s', '%s', '%s', '%s', '%s')".formatted(type, shopId, productId, amount, responsible));

        statement.executeUpdate("");

        conn.close();
    }

    // Добавить сотрудника
    public static void addEmployee(String id, String shopId, String name, String post, int salary, String login) throws SQLException {
        
        conn = DriverManager.getConnection(URL);
        statement = conn.createStatement();

        statement.executeUpdate("insert into employee(id, shop_id, full_name, post, salary, login) values('%s', '%s', '%s', '%s', '%s', '%s')".formatted(id, shopId, name, post, salary, login));

        conn.close();
    }

    // Удалить товар
    public static void deleteProduct(String id) throws SQLException {
        
        conn = DriverManager.getConnection(URL);
        statement = conn.createStatement();

        statement.executeUpdate("delete from product where id = '%s'".formatted(id));

        conn.close();
    }

    // Удалить экземпляр товара
    public static void deleteProductCopy(ProductCopy copy) throws SQLException {
        
        conn = DriverManager.getConnection(URL);
        statement = conn.createStatement();

        statement.executeUpdate("delete from product_copy where article = '%s'".formatted(copy.article));

        conn.close();
    }

    // Удалить магазин
    public static void deleteShop(String id) throws SQLException {
        
        conn = DriverManager.getConnection(URL);
        statement = conn.createStatement();

        // удалить все экземпляры этого магазина
        statement.executeUpdate("delete from product_copy where shop_id = '%s'".formatted(id));

        // удалить сам магазин
        statement.executeUpdate("delete from shop where id = '%s'".formatted(id));

        conn.close();
    }

    // Удалить сотрудника
    public static void deleteEmployee(String id) throws SQLException {
        
        conn = DriverManager.getConnection(URL);
        statement = conn.createStatement();

        statement.executeUpdate("delete from employee where id = '%s'".formatted(id));

        conn.close();
    }

    // Изменить товар
    public static void editProduct(Product p, String id) throws SQLException {
        conn = DriverManager.getConnection(URL);
        statement = conn.createStatement();

        statement.executeUpdate("update `product` set `id` = '%s', `type` = '%s', `model` = '%s', `manufacturer` = '%s', `price` = '%s' where `id` = '%s'".formatted(p.id, p.type, p.model, p.manufacturer, p.price, id));

        conn.close();
    }

    // Изменить магазин
    public static void editShop(Shop s, String id) throws SQLException {
        
        conn = DriverManager.getConnection(URL);
        statement = conn.createStatement();

        statement.executeUpdate("update `shop` set `id` = '%s', `city` = '%s', `street` = '%s', `building` = '%s' where `id` = '%s'".formatted(s.id, s.city, s.street, s.building, id));

        conn.close();
    }

    // Изменить информацию о сотруднике
    public static void editEmployee(Employee e, String id) throws SQLException {
        
        conn = DriverManager.getConnection(URL);
        statement = conn.createStatement();

        statement.executeUpdate("update `employee` set `id` = '%s', `shop_id` = '%s', `full_name` = '%s', `post` = '%s', `salary` = '%s' where `id` = '%s'".formatted(e.id, e.shopId, e.name, e.post, e.salary, id));

        conn.close();
    }
}