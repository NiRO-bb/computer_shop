import java.math.RoundingMode;
import java.sql.*;

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
}