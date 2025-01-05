import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Authorization extends Window {
    public Authorization() {
        super("Авторизация");

        // добавление содержимого
        getContentPane().add(CreateContent());
        // установка размеров окна
        pack();
    }

    protected JPanel CreateContent() {
        // создание панели
        JPanel mainPanel = new JPanel();

        // установка расположения
        mainPanel.setLayout(new BoxLayout(mainPanel, 1));
        // установка границ
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // логин
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, 0));

        JTextField loginField = new JTextField(30);

        loginPanel.add(new JLabel("Логин:"));
        loginPanel.add(Box.createHorizontalStrut(5));
        loginPanel.add(loginField);

        // пароль
        JPanel pswdPanel = new JPanel();
        pswdPanel.setLayout(new BoxLayout(pswdPanel, 0));

        JPasswordField pswdField = new JPasswordField(20);

        pswdPanel.add(new JLabel("Пароль:"));
        pswdPanel.add(Box.createHorizontalStrut(5));
        pswdPanel.add(pswdField);

        // строка состояния
        JLabel infoLabel = new JLabel("Введите данные вашего аккаунта");
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // кнопки
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new BoxLayout(btnPanel, 0));

        JButton enterButton = new JButton("Войти");
        enterButton.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               try {
                   // поиск в БД пользователя
                   User user = SQL.getUser(loginField.getText(), pswdField.getText());

                   if (user != null) {
                       // проверка кода доступа
                       switch (user.code) {
                           case "admin":
                               new AdminMenu(user.login);
                               break;

                           case "employee":
                               new EmployeeMenu(user.login);
                               break;

                           case "none":
                               new CustomerMenu(user.login);
                               break;
                       }

                       // освобождение ресурсов
                       dispose();
                   }
                   else {
                       infoLabel.setText("Неверный логин и/или пароль");
                       pswdField.setText("");
                   }
               } catch(SQLException exception) {
                   new Notification(exception.getMessage());
               }
           }
        });

        JButton regButton = new JButton("Зарегистрироваться");
        regButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Registration();
                dispose();
            }
        });

        btnPanel.add(enterButton);
        btnPanel.add(Box.createHorizontalStrut(5));
        btnPanel.add(regButton);

        // сборка интерфейса
        mainPanel.add(loginPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(pswdPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(infoLabel);
        mainPanel.add(Box.createVerticalStrut(30));
        mainPanel.add(btnPanel);

        return mainPanel;
    }
}