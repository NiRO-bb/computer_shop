import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddProduct extends Window {
    private String login;

    public AddProduct(String login) {
        super("Добавление товара");

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

        // id
        JPanel idPanel = new JPanel();
        idPanel.setLayout(new BoxLayout(idPanel, 0));

        JTextField idField = new JTextField(10);

        idPanel.add(new JLabel("ID товара:"));
        idPanel.add(Box.createHorizontalStrut(5));
        idPanel.add(idField);

        // type
        JPanel typePanel = new JPanel();
        typePanel.setLayout(new BoxLayout(typePanel, 0));

        JTextField typeField = new JTextField(20);

        typePanel.add(new JLabel("Тип товара:"));
        typePanel.add(Box.createHorizontalStrut(5));
        typePanel.add(typeField);

        // model
        JPanel modelPanel = new JPanel();
        modelPanel.setLayout(new BoxLayout(modelPanel, 0));

        JTextField modelField = new JTextField(20);

        modelPanel.add(new JLabel("Модель:"));
        modelPanel.add(Box.createHorizontalStrut(5));
        modelPanel.add(modelField);

        // manufacturer
        JPanel factoryPanel = new JPanel();
        factoryPanel.setLayout(new BoxLayout(factoryPanel, 0));

        JTextField factoryField = new JTextField(20);

        factoryPanel.add(new JLabel("Производитель:"));
        factoryPanel.add(Box.createHorizontalStrut(5));
        factoryPanel.add(factoryField);

        // price
        JPanel pricePanel = new JPanel();
        pricePanel.setLayout(new BoxLayout(pricePanel, 0));

        JTextField priceField = new JTextField(20);

        pricePanel.add(new JLabel("Цена:"));
        pricePanel.add(Box.createHorizontalStrut(5));
        pricePanel.add(priceField);

        // кнопки
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new BoxLayout(btnPanel, 0));

        JButton addButton = new JButton("Добавить товар");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    SQL.addProduct(idField.getText(),
                            typeField.getText(),
                            modelField.getText(),
                            factoryField.getText(),
                            Double.parseDouble(priceField.getText()));

                    new Notification("Был добавлен товар - " + typeField.getText(), 1);

                    // очистка полей
                    idField.setText("");
                    typeField.setText("");
                    modelField.setText("");
                    factoryField.setText("");
                    priceField.setText("");
                }
                catch(Exception exception) {
                    new Notification(exception.getMessage(), 0);
                }
            }
        });

        JButton exitButton = new JButton("Назад");
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AdminMenu(login);
                dispose();
            }
        });

        btnPanel.add(addButton);
        btnPanel.add(Box.createHorizontalStrut(5));
        btnPanel.add(exitButton);

        // сборка интерфейса
        gridPanel.add(new JLabel("Введите данные товара"));
        gridPanel.add(idPanel);
        gridPanel.add(typePanel);
        gridPanel.add(modelPanel);
        gridPanel.add(factoryPanel);
        gridPanel.add(pricePanel);
        gridPanel.add(Box.createVerticalStrut(10));
        gridPanel.add(btnPanel);

        mainPanel.add(gridPanel);

        return mainPanel;
    }
}