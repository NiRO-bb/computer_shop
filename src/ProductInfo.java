import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ProductInfo extends JDialog {
    Product product;
    public ProductInfo(Product product) {
        super(new JFrame(), "Информация о товаре", true);
        this.product = product;

        // добавление содержимого
        getContentPane().add(CreateContent());
        // установка размеров окна
        pack();

        // запрет на изменение размеров окна
        setResizable(false);
        // видимость окна
        setVisible(true);
    }

    protected JPanel CreateContent() {
        // создание панели
        JPanel mainPanel = new JPanel();

        // установка расположения
        mainPanel.setLayout(new BoxLayout(mainPanel, 1));
        // установка границ
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // описание
        mainPanel.add(new JLabel("Тип: " + product.type));
        mainPanel.add(Box.createVerticalStrut(5));

        mainPanel.add(new JLabel("Модель: " + product.model));
        mainPanel.add(Box.createVerticalStrut(5));

        mainPanel.add(new JLabel("Производитель: " + product.manufacturer));
        mainPanel.add(Box.createVerticalStrut(5));

        mainPanel.add(new JLabel("Цена: " + product.price));
        mainPanel.add(Box.createVerticalStrut(20));

        mainPanel.add(new JLabel("Наличие в магазинах:"));
        mainPanel.add(Box.createVerticalStrut(5));

        // список магазинов с товаром
        try {
            // список магазинов
            ArrayList<Shop> shops = SQL.getShopList(product);

            // добавление магазинов в панель
            int count = 0;
            for (Shop s : shops) {
                mainPanel.add(new JLabel("%s. г. %s, ул. %s, д. %s".formatted(++count, s.city, s.street, s.building)));
                mainPanel.add(Box.createVerticalStrut(10));
            }

            if (count == 0) {
                mainPanel.add(new JLabel("Товар временно отсутствует во всех магазинах"));
                mainPanel.add(Box.createVerticalStrut(10));
            }
        }
        catch (Exception e) {
            new Notification(e.getMessage(), 0);
        }

        // кнопка
        JButton exitButton = new JButton("Назад");
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        exitButton.setAlignmentX(CENTER_ALIGNMENT);
        mainPanel.add(exitButton);

        return mainPanel;
    }
}