import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AddTransaction extends Window {
    private String login;

    public AddTransaction(String login) {
        super("Добавление магазина");

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

        // type
        JPanel typePanel = new JPanel();
        typePanel.setLayout(new BoxLayout(typePanel, 0));

        String[] types = new String[] {"Продажа", "Поставка", "Возврат"};
        JComboBox typeBox = new JComboBox(types);

        typePanel.add(new JLabel("Тип операции:"));
        typePanel.add(Box.createHorizontalStrut(5));
        typePanel.add(typeBox);

        // shopId
        JPanel shopPanel = new JPanel();
        shopPanel.setLayout(new BoxLayout(shopPanel, 0));

        ArrayList<Shop> shops = null;
        try {
            for (Object obj : SQL.getShopList())
                shops.add((Shop) obj);
        }
        catch (Exception e) { new Notification(e.getMessage(), 0); }

        JComboBox shopBox = new JComboBox(shops.stream().map(shop -> shop.id).toArray(String[]::new));

        shopPanel.add(new JLabel("ID магазина:"));
        shopPanel.add(Box.createHorizontalStrut(5));
        shopPanel.add(shopBox);

        // productId
        JPanel productPanel = new JPanel();
        productPanel.setLayout(new BoxLayout(productPanel, 0));

        ArrayList<Product> products = null;
        try {
            for (Object obj : SQL.getProductList())
                products.add((Product)obj);
        }
        catch (Exception e) { new Notification(e.getMessage(), 0); }

        JComboBox productBox = new JComboBox(products.stream().map(prod -> prod.id).toArray(String[]::new));

        productPanel.add(new JLabel("ID товара:"));
        productPanel.add(Box.createHorizontalStrut(5));
        productPanel.add(productBox);

        // amount
        JPanel amountPanel = new JPanel();
        amountPanel.setLayout(new BoxLayout(amountPanel, 0));

        JTextField amountField = new JTextField(5);

        amountPanel.add(new JLabel("Укажите кол-во экземпляров:"));
        amountPanel.add(Box.createHorizontalStrut(5));
        amountPanel.add(amountField);

        // кнопки
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new BoxLayout(btnPanel, 0));

        JButton addButton = new JButton("Внести данные");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    SQL.addTransaction(typeBox.getSelectedItem().toString(),
                            shopBox.getSelectedItem().toString(),
                            productBox.getSelectedItem().toString(),
                            Integer.parseInt(amountField.getText()),
                            SQL.getEmployee(login).getId());

                    new Notification("Была совершена операция - " + typeBox.getSelectedItem().toString(), 1);

                    // очистка полей
                    amountField.setText("");
                }
                catch (Exception exception) {
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
        gridPanel.add(new JLabel("Введите данные операции"));
        gridPanel.add(typePanel);
        gridPanel.add(shopPanel);
        gridPanel.add(productPanel);
        gridPanel.add(amountPanel);
        gridPanel.add(Box.createVerticalStrut(10));
        gridPanel.add(btnPanel);

        mainPanel.add(gridPanel);

        return mainPanel;
    }
}