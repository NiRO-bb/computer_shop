public class User {
    // добавить модификатор доступа
    String login;
    String password;
    String code;

    public User(String login, String password, String code) {
        this.login = login;
        this.password = password;
        this.code = code;
    }
}