import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminMenu extends Window {
    private String login;

    public AdminMenu(String login) {
        super("Главное меню");

        this.login = login;

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

        // кнопки
        JButton exitButton = new JButton("Выйти");
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Authorization();
            }
        });

        // кнопки - товары
        JButton addProductButton = new JButton("Добавить товар");
        addProductButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // new AddProduct();
                dispose();
            }
        });

        JButton productListButton = new JButton("Список товаров");
        productListButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // new Catalog();
                dispose();
            }
        });

        // кнопки - магазины
        JButton addShopButton = new JButton("Добавить магазин");
        addShopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //new AddShop();
                dispose();
            }
        });

        JButton shopListButton = new JButton("Список магазинов");
        shopListButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // new ShopList();
                dispose();
            }
        });

        // кнопки - операции
        JButton addTransactionButton = new JButton("Новая операция");
        addTransactionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // new AddTransaction();
                dispose();
            }
        });

        JButton transactionHistoryButton = new JButton("История операций");
        transactionHistoryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // new HistoryOfTransaction();
                dispose();
            }
        });

        // кнопки - сотрудники
        JButton addEmployeeButton = new JButton("Новый сотрудник");
        addEmployeeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // new AddEmployee();
                dispose();
            }
        });

        JButton employeeListButton = new JButton("Список сотрудников");
        employeeListButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // new EmployeeList();
                dispose();
            }
        });

        // кнопки - отчеты
        JButton productReportButton = new JButton("Отчет по товарам");
        productReportButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // new reportProduct();
                dispose();
            }
        });
        JButton salesReportButton = new JButton("Отчет по продажам");
        salesReportButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // new reportSales();
                dispose();
            }
        });

        // сборка интерфейса
        gridPanel.add(new JLabel("Здравствуйте, '%s'!".formatted(login)));
        gridPanel.add(Box.createVerticalStrut(10));

        gridPanel.add(new JLabel("Вернуться к авторизации:"));
        gridPanel.add(exitButton);

        gridPanel.add(new JLabel("Товары:"));
        gridPanel.add(addProductButton);
        gridPanel.add(productListButton);

        gridPanel.add(new JLabel("Магазины:"));
        gridPanel.add(addShopButton);
        gridPanel.add(shopListButton);

        gridPanel.add(new JLabel("Операции:"));
        gridPanel.add(addTransactionButton);
        gridPanel.add(transactionHistoryButton);

        gridPanel.add(new JLabel("Сотрудники:"));
        gridPanel.add(addEmployeeButton);
        gridPanel.add(employeeListButton);

        gridPanel.add(new JLabel("Отчеты:"));
        gridPanel.add(productReportButton);
        gridPanel.add(salesReportButton);

        mainPanel.add(gridPanel);

        return mainPanel;
    }
}
