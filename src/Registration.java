import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Registration extends Window{
    public Registration(){
        super("Регистрация");

        // добавление содержимого
        getContentPane().add(CreateContent());

        // установка размеров окна
        pack();
        setSize(new Dimension(getWidth() + 50, getHeight()));
    }

    protected JPanel CreateContent() {
        // создание панели
        JPanel mainPanel = new JPanel();

        // установка расположения
        mainPanel.setLayout(new BoxLayout(mainPanel, 1));
        // установка границ
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // строка состояния
        JLabel infoLabel = new JLabel("Укажите логин и пароль для вашей учетной записи");
        infoLabel.setAlignmentX(CENTER_ALIGNMENT);

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

        // подтверждение пароля
        JPanel confirmPanel = new JPanel();
        confirmPanel.setLayout(new BoxLayout(confirmPanel, 0));

        JPasswordField confirmField = new JPasswordField(20);

        confirmPanel.add(new JLabel("Подтвердите пароль:"));
        confirmPanel.add(Box.createHorizontalStrut(5));
        confirmPanel.add(confirmField);

        // кнопки
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new BoxLayout(btnPanel, 0));

        JButton createButton = new JButton("Создать запись");
        createButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!pswdField.getText().equals(confirmField.getText())) {
                        infoLabel.setText("Введённые пароли не совпадают! Попробуйте еще раз");
                    } else if (SQL.checkLogin(loginField.getText())) {
                        infoLabel.setText("Указанный Вами логин уже занят!");
                    } else if (loginField.getText().equals("") || pswdField.getText().equals("")) {
                        infoLabel.setText("Вы заполнили не все поля!");
                    } else {
                        SQL.addUser(loginField.getText(), pswdField.getText(), "none");

                        // открытие меню клиента
                        new CustomerMenu(loginField.getText());

                        // закрытие окна
                        dispose();
                    }

                    // очистка полей
                    pswdField.setText("");
                    confirmField.setText("");
                }
                catch (SQLException exception) {
                    new Notification(exception.getMessage(), 0);
                }
            }
        });

        JButton exitButton = new JButton("Назад");
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Authorization();
                dispose();
            }
        });

        btnPanel.add(createButton);
        btnPanel.add(Box.createHorizontalStrut(5));
        btnPanel.add(exitButton);

        // сборка интерфейса
        mainPanel.add(infoLabel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(loginPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(pswdPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(confirmPanel);
        mainPanel.add(Box.createVerticalStrut(30));
        mainPanel.add(btnPanel);

        return mainPanel;
    }
}