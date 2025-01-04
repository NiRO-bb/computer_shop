import java.sql.*;

public class SQL {
    private static String URL = "jdbc:mysql://localhost:3306/niro_bb";
    private static String dbUsername = "niro_bb";
    private static String dbPassword = "2034";

    private static Connection conn;
    private static Statement statement;

    // Авторизация
    public static User getUser(String login, String pswd) throws SQLException{
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
}