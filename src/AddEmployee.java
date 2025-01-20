import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AddEmployee extends Window {
    private String login;
    private Employee employee = new Employee();
    private boolean editMode = false;

    // конструктор для добавления сотрудника
    public AddEmployee(String login) {
        super("Добавление сотрудника");

        this.login = login;

        // добавление содержимого
        getContentPane().add(CreateContent());
        // установка размеров окна
        pack();
    }

    // конструктор для изменения информации о сотруднике
    public AddEmployee(String login, Employee employee) {
        super("Изменение информации о сотруднике");

        editMode = true;

        this.login = login;
        this.employee = employee;

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

        // панель
        JPanel gridPanel = new JPanel(new GridLayout(0, 1, 0, 5));

        // id
        JPanel idPanel = new JPanel();
        idPanel.setLayout(new BoxLayout(idPanel, 0));

        JTextField idField = new JTextField(10);
        idField.setText(employee.id);

        idPanel.add(new JLabel("ID сотрудника:"));
        idPanel.add(Box.createHorizontalStrut(5));
        idPanel.add(idField);

        // shopId
        JPanel shopPanel = new JPanel();
        shopPanel.setLayout(new BoxLayout(shopPanel, 0));

        ArrayList<Shop> shops = new ArrayList<>();
        try {
            for (Object obj : SQL.getShopList())
            {
                shops.add((Shop)obj);
            }
        }
        catch (Exception e) { new Notification(e.getMessage(), 0); }

        JComboBox shopBox = new JComboBox(shops.stream().map(shop -> shop.id).toArray(String[]::new));
        shopBox.setSelectedItem(employee.id);

        shopPanel.add(new JLabel("ID магазина:"));
        shopPanel.add(Box.createHorizontalStrut(5));
        shopPanel.add(shopBox);

        // name
        JPanel namePanel = new JPanel();
        namePanel.setLayout(new BoxLayout(namePanel, 0));

        JTextField nameField = new JTextField(10);
        nameField.setText(employee.name);

        namePanel.add(new JLabel("ФИО:"));
        namePanel.add(Box.createHorizontalStrut(5));
        namePanel.add(nameField);

        // post
        JPanel postPanel = new JPanel();
        postPanel.setLayout(new BoxLayout(postPanel, 0));

        JTextField postField = new JTextField(10);
        postField.setText(employee.post);

        postPanel.add(new JLabel("Должность:"));
        postPanel.add(Box.createHorizontalStrut(5));
        postPanel.add(postField);

        // salary
        JPanel salaryPanel = new JPanel();
        salaryPanel.setLayout(new BoxLayout(salaryPanel, 0));

        JTextField salaryField = new JTextField(10);
        salaryField.setText(String.valueOf(employee.salary));

        salaryPanel.add(new JLabel("Зарплата:"));
        salaryPanel.add(Box.createHorizontalStrut(5));
        salaryPanel.add(salaryField);

        // login
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, 0));

        JTextField loginField = new JTextField(10);

        loginPanel.add(new JLabel("Логин:"));
        loginPanel.add(Box.createHorizontalStrut(5));
        loginPanel.add(loginField);

        // password
        JPanel pswdPanel = new JPanel();
        pswdPanel.setLayout(new BoxLayout(pswdPanel, 0));

        JTextField pswdField = new JTextField(10);

        pswdPanel.add(new JLabel("Пароль:"));
        pswdPanel.add(Box.createHorizontalStrut(5));
        pswdPanel.add(pswdField);

        // кнопки
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new BoxLayout(btnPanel, 0));

        JButton addButton = new JButton(!editMode ? "Добавить сотрудника" : "Изменить информацию");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!editMode) {
                        // добавление сотрудника
                        SQL.addEmployee(idField.getText(),
                                shopBox.getSelectedItem().toString(),
                                nameField.getText(),
                                postField.getText(),
                                Integer.parseInt(salaryField.getText()),
                                loginField.getText());
                        // создание учетной записи для сотрудника
                        SQL.addUser(loginField.getText(),
                                pswdField.getText(),
                                "employee");

                        new Notification("Был добавлен сотрудник - " + nameField.getText(), 1);
                    } else {
                        SQL.editEmployee(new Employee(idField.getText(),
                                shopBox.getSelectedItem().toString(),
                                nameField.getText(),
                                postField.getText(),
                                Integer.parseInt(salaryField.getText()),
                                employee.login), employee.id);

                        new Notification("Была изменена информация о сотруднике %s - %s".formatted(employee.name, employee.post), 1);
                    }

                    // очистка полей
                    idField.setText("");
                    nameField.setText("");
                    postField.setText("");
                    salaryField.setText("");
                    loginField.setText("");
                    pswdField.setText("");
                }
                catch (Exception exception) {
                    new Notification(exception.getMessage(), 0);
                }
            }
        });

        JButton exitButton = new JButton("Назад");
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!editMode) new AdminMenu(login);
                else new EmployeeList(login);

                dispose();
            }
        });

        btnPanel.add(addButton);
        btnPanel.add(Box.createHorizontalStrut(5));
        btnPanel.add(exitButton);

        // сборка интерфейса
        gridPanel.add(new JLabel("Введите данные сотрудника"));
        gridPanel.add(idPanel);
        gridPanel.add(shopPanel);
        gridPanel.add(namePanel);
        gridPanel.add(postPanel);
        gridPanel.add(salaryPanel);
        if (!editMode) {
            gridPanel.add(loginPanel);
            gridPanel.add(pswdPanel);
        }
        gridPanel.add(Box.createVerticalStrut(10));
        gridPanel.add(btnPanel);

        mainPanel.add(gridPanel);

        return mainPanel;
    }
}