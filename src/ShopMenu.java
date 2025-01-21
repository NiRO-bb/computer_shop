import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ShopMenu extends Window {
    private String login;
    private Shop shop;

    public ShopMenu(String login, Shop shop) {
        super("Список магазинов");

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

        // список товаров
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, 1));

        ArrayList<Object> copies = new ArrayList<>();
        try { copies = SQL.getProductCopyList(shop); }
        catch (Exception e) { new Notification(e.getMessage(), 0); }

        createList(listPanel, copies, 1);

        // строка поиска
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, 0));

        JTextField searchField = new JTextField(20);

        JButton searchButton = new JButton("Найти");
        ArrayList<Object> finalCopies = copies;
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // поиск
                ArrayList<Object> newProducts = search(searchField, finalCopies);

                // показать результаты поиска
                createList(listPanel, newProducts, 1);
                pack();
            }
        });

        searchPanel.add(new JLabel("Введите данные экземпляра:"));
        searchPanel.add(Box.createHorizontalStrut(5));
        searchPanel.add(searchField);
        searchPanel.add(Box.createHorizontalStrut(5));
        searchPanel.add(searchButton);

        // кнопки
        JPanel buttonPanel = new JPanel();

        JButton exitButton = new JButton("Назад");
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ShopList(login);
                dispose();
            }
        });

        JButton addButton = new JButton("Добавить запись об экземпляре");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // new AddProductCopy(login, shop);
                dispose();
            }
        });

        buttonPanel.add(exitButton);
        buttonPanel.add(Box.createHorizontalStrut(5));
        buttonPanel.add(addButton);

        // сборка интерфейса
        JPanel shopPanel = new JPanel();
        shopPanel.add(new JLabel("Магазин: " + shop.toString()));
        shopPanel.add(Box.createGlue());

        mainPanel.add(shopPanel);
        mainPanel.add(Box.createVerticalStrut(15));

        JPanel labelPanel = new JPanel();
        labelPanel.add(new JLabel("Работа с ассортиментом магазина"));
        labelPanel.add(Box.createGlue());

        mainPanel.add(labelPanel);
        mainPanel.add(Box.createVerticalStrut(5));
        mainPanel.add(searchPanel);
        mainPanel.add(listPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(buttonPanel);

        return mainPanel;
    }

    void createList(JPanel panel, ArrayList<Object> copies, int pointer) {
        panel.removeAll();

        int count = 0;
        for (; pointer - 1 + count < copies.size() && count < 10; count++) {
            // Конкретный элемент
            ProductCopy p = (ProductCopy)copies.get(pointer - 1 + count);

            // панель элемента
            JPanel prodPanel = new JPanel();
            prodPanel.setLayout(new BoxLayout(prodPanel, 1));

            // кнопки
            JPanel buttonPanel = new JPanel();

            JButton removeButton = new JButton("Удалить");
            removeButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        SQL.deleteProductCopy(p);

                        new Notification("Был удален экземпляр товара - %s".formatted(p.toString()) , 1);

                        // обновление списка
                        copies.remove(p);
                        createList(panel, copies, pointer);
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
                    //new AddProductCopy(login, p);
                    dispose();
                }
            });

            buttonPanel.add(removeButton);
            buttonPanel.add(Box.createHorizontalStrut(5));
            buttonPanel.add(editButton);
            buttonPanel.add(Box.createGlue());

            // сборка панели
            prodPanel.add(addDesc(copies.get(pointer - 1 + count), pointer + count));
            prodPanel.add(buttonPanel);
            prodPanel.add(Box.createVerticalStrut(10));

            panel.add(prodPanel);
        }

        // добавление кнопок переключения
        // кнопки только для списков с количеством элементов большим 10
        if (copies.size() > 10) {
            int finalPointer = pointer + count + 1;
            JPanel buttonPanel = new JPanel();

            JButton nextButton = new JButton(">");
            nextButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (pointer != 1 && finalPointer > copies.size() + 1) {

                    } else {
                        createList(panel, copies, pointer + 10);
                        pack();
                    }
                }
            });

            JButton prevButton = new JButton("<");
            prevButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (pointer == 1) {

                    } else {
                        createList(panel, copies, pointer - 10);
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
