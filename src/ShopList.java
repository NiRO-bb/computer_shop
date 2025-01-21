import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ShopList extends Window {
    private String login;

    public ShopList(String login) {
        super("Список магазинов");

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

        // список магазинов
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, 1));

        ArrayList<Object> shops = null;
        try { shops = SQL.getShopList(); }
        catch (Exception e) { new Notification(e.getMessage(), 0); }

        createList(listPanel, shops, 1);

        // строка поиска
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, 0));

        JTextField searchField = new JTextField(20);

        JButton searchButton = new JButton("Найти");
        ArrayList<Object> finalShops = shops;
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // поиск
                ArrayList<Object> newShops = search(searchField, finalShops);

                // показать результаты поиска
                createList(listPanel, newShops, 1);
                pack();
            }
        });

        searchPanel.add(new JLabel("Введите адрес магазина:"));
        searchPanel.add(Box.createHorizontalStrut(5));
        searchPanel.add(searchField);
        searchPanel.add(Box.createHorizontalStrut(5));
        searchPanel.add(searchButton);

        // кнопка
        JButton exitButton = new JButton("Назад");
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AdminMenu(login);
                dispose();
            }
        });

        exitButton.setAlignmentX(CENTER_ALIGNMENT);

        // сборка интерфейса
        mainPanel.add(searchPanel);
        mainPanel.add(listPanel);
        mainPanel.add(exitButton);

        return mainPanel;
    }

    void createList(JPanel panel, ArrayList<Object> shops, int pointer) {
        panel.removeAll();

        int count = 0;
        for (; pointer - 1 + count < shops.size() && count < 10; count++) {
            // конкретный элемент
            Shop s = (Shop) shops.get(pointer - 1 + count);

            // панель элемента
            JPanel prodPanel = new JPanel();
            prodPanel.setLayout(new BoxLayout(prodPanel, 1));

            // кнопки
            JPanel buttonPanel = new JPanel();

            JButton infoButton = new JButton("Подробнее");
            infoButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    new ShopMenu(login, s);
                    dispose();
                }
            });

            JButton removeButton = new JButton("Удалить");
            removeButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        SQL.deleteShop(s.id);

                        new Notification("Был удален магазин - г. %s, ул. %s, д. %s".formatted(s.city, s.street, s.building) , 1);

                        // обновление списка
                        shops.remove(s);
                        createList(panel, shops, pointer);
                        pack();
                    }
                    catch(Exception exception) {
                        new Notification(exception.getMessage(), 0);
                    }
                }
            });

            JButton editButton = new JButton("Изменить");
            editButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    new AddShop(login, s);
                    dispose();
                }
            });

            buttonPanel.add(infoButton);
            buttonPanel.add(Box.createHorizontalStrut(5));
            buttonPanel.add(removeButton);
            buttonPanel.add(Box.createHorizontalStrut(5));
            buttonPanel.add(editButton);
            buttonPanel.add(Box.createGlue());

            // сборка панели
            prodPanel.add(addDesc(shops.get(pointer - 1 + count), pointer + count));
            prodPanel.add(buttonPanel);
            prodPanel.add(Box.createVerticalStrut(10));

            panel.add(prodPanel);
        }

        // добавление кнопок переключения
        // кнопки только для списков с количеством элементов большим 10
        if (shops.size() > 10) {
            int finalPointer = pointer + count + 1;
            JPanel buttonPanel = new JPanel();

            JButton nextButton = new JButton(">");
            nextButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (pointer != 1 && finalPointer > shops.size() + 1) {

                    } else {
                        createList(panel, shops, pointer + 10);
                        pack();
                    }
                }
            });

            JButton prevButton = new JButton("<");
            prevButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (pointer == 1) {

                    } else {
                        createList(panel, shops, pointer - 10);
                        pack();
                    }
                }
            });

            buttonPanel.add(prevButton);
            buttonPanel.add(Box.createHorizontalStrut(5));
            buttonPanel.add(nextButton);

            panel.add(buttonPanel);
        }
    }
}