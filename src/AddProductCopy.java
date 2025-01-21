import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AddProductCopy extends Window {
    private String login;
    private Shop shop;
    private boolean adminMode = true;

    public AddProductCopy(String login) {
        super("Добавление экземпляра");

        adminMode = false;

        this.login = login;
        try { this.shop = SQL.getUserShop(login); }
        catch (Exception e) { new Notification(e.getMessage(), 0); }

        // добавление содержимого
        getContentPane().add(CreateContent());
        // установка размеров окна
        pack();
    }

    public AddProductCopy(String login, Shop shop) {
        super("Добавление экземпляра");

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

        // product_id
        JPanel productPanel = new JPanel();
        productPanel.setLayout(new BoxLayout(productPanel, 0));

        ArrayList<Product> products = new ArrayList<>();
        try {
            for (Object obj : SQL.getProductList())
                products.add((Product)obj);
        }
        catch (Exception e) { new Notification(e.getMessage(), 0); }

        JComboBox productBox = new JComboBox(products.stream().map(prod -> prod.id).toArray(String[]::new));

        productPanel.add(new JLabel("ID товара:"));
        productPanel.add(Box.createHorizontalStrut(5));
        productPanel.add(productBox);

        // кнопки
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, 0));

        JButton exitButton = new JButton("Назад");
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (adminMode) new ShopMenu(login, shop);
                else new EmployeeMenu(login);

                dispose();
            }
        });

        JButton addButton = new JButton("Добавить экземпляр");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String article = SQL.addProductCopy(shop, productBox.getSelectedItem().toString());

                    new Notification("Был добавлен экземпляр - " + article, 1);
                }
                catch (Exception exception) {
                    new Notification(exception.getMessage(), 0);
                }
            }
        });

        buttonPanel.add(exitButton);
        buttonPanel.add(Box.createHorizontalStrut(5));
        buttonPanel.add(addButton);

        // сборка интерфейса
        gridPanel.add(new JLabel("Введите данные экземпляра"));
        gridPanel.add(productPanel);
        gridPanel.add(Box.createVerticalStrut(10));
        gridPanel.add(buttonPanel);

        mainPanel.add(gridPanel);

        return mainPanel;
    }
}