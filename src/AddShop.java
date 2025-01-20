import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddShop extends Window {
    private String login;
    private Shop shop = new Shop();
    private boolean editMode = false;

    // конструктор для добавления магазина
    public AddShop(String login) {
        super("Добавление магазина");

        this.login = login;

        // добавление содержимого
        getContentPane().add(CreateContent());
        // установка размеров окна
        pack();
    }

    // конструктор для изменения магазина
    public AddShop(String login, Shop shop) {
        super("Изменение магазина");

        editMode = true;

        this.login = login;
        this.shop = shop;

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
        idField.setText(shop.id);

        idPanel.add(new JLabel("ID магазина:"));
        idPanel.add(Box.createHorizontalStrut(5));
        idPanel.add(idField);

        // city
        JPanel cityPanel = new JPanel();
        cityPanel.setLayout(new BoxLayout(cityPanel, 0));

        JTextField cityField = new JTextField(10);
        cityField.setText(shop.city);

        cityPanel.add(new JLabel("Город:"));
        cityPanel.add(Box.createHorizontalStrut(5));
        cityPanel.add(cityField);

        // street
        JPanel streetPanel = new JPanel();
        streetPanel.setLayout(new BoxLayout(streetPanel, 0));

        JTextField streetField = new JTextField(10);
        streetField.setText(shop.street);

        streetPanel.add(new JLabel("Улица:"));
        streetPanel.add(Box.createHorizontalStrut(5));
        streetPanel.add(streetField);

        // building
        JPanel buildPanel = new JPanel();
        buildPanel.setLayout(new BoxLayout(buildPanel, 0));

        JTextField buildField = new JTextField(10);
        buildField.setText(String.valueOf(shop.building));

        buildPanel.add(new JLabel("Здание:"));
        buildPanel.add(Box.createHorizontalStrut(5));
        buildPanel.add(buildField);

        // кнопки
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new BoxLayout(btnPanel, 0));

        JButton addButton = new JButton(!editMode ? "Добавить магазин" : "Изменить магазин");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!editMode) {
                        SQL.addShop(idField.getText(),
                                cityField.getText(),
                                streetField.getText(),
                                Integer.parseInt(buildField.getText()));

                        new Notification("Был добавлен магазин - " + idField.getText(), 1);
                    } else {
                        SQL.editShop(new Shop(idField.getText(),
                                cityField.getText(),
                                streetField.getText(),
                                Integer.parseInt(buildField.getText())), shop.id);

                        new Notification("Был изменен магазин - " + shop.toString(), 1);
                    }

                    // очистка полей
                    idField.setText("");
                    cityField.setText("");
                    streetField.setText("");
                    buildField.setText("");
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
               else new ShopList(login);

               dispose();
           }
        });

        btnPanel.add(addButton);
        btnPanel.add(Box.createHorizontalStrut(5));
        btnPanel.add(exitButton);

        // сборка интерфейса
        gridPanel.add(new JLabel("Введите данные магазина"));
        gridPanel.add(idPanel);
        gridPanel.add(cityPanel);
        gridPanel.add(streetPanel);
        gridPanel.add(buildPanel);
        gridPanel.add(Box.createVerticalStrut(10));
        gridPanel.add(btnPanel);

        mainPanel.add(gridPanel);

        return mainPanel;
    }
}
