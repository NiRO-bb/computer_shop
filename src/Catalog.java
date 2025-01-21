import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;

public class Catalog extends Window {
    private String login;
    private String code;

    public Catalog(String login) {
        super("Каталог товаров");

        this.login = login;

        // проверка кода пользователя
        try { code = SQL.getUser(login).code; }
        catch (Exception e) { new Notification(e.getMessage(), 0); }

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

        ArrayList<Object> products = null;
        try { products = SQL.getProductList(); }
        catch (Exception e) { new Notification(e.getMessage(), 0); }

        createList(listPanel, products, 1);

        // строка поиска
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, 0));

        JTextField searchField = new JTextField(20);

        JButton searchButton = new JButton("Найти");
        ArrayList<Object> finalProducts = products;
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // поиск
                ArrayList<Object> newProducts = search(searchField, finalProducts);

                // показать результаты поиска
                createList(listPanel, newProducts, 1);
                pack();
            }
        });

        searchPanel.add(new JLabel("Введите название товара:"));
        searchPanel.add(Box.createHorizontalStrut(5));
        searchPanel.add(searchField);
        searchPanel.add(Box.createHorizontalStrut(5));
        searchPanel.add(searchButton);

        // кнопка
        JButton exitButton = new JButton("Назад");
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (code.equals("admin")) new AdminMenu(login);
                else if (code.equals("employee")) new EmployeeMenu(login);

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

    void createList(JPanel panel, ArrayList<Object> products, int pointer) {
        panel.removeAll();

        int count = 0;
        for (; pointer - 1 + count < products.size() && count < 10; count++) {
            // Конкретный элемент
            Product p = (Product)products.get(pointer - 1 + count);

            // панель элемента
            JPanel prodPanel = new JPanel();
            prodPanel.setLayout(new BoxLayout(prodPanel, 1));

            // кнопки
            JPanel buttonPanel = new JPanel();

            JButton infoButton = new JButton("Подробнее");
            infoButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    new ProductInfo(p);
                }
            });

            JButton removeButton = new JButton("Удалить");
            removeButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        SQL.deleteProduct(p.id);

                        new Notification("Был удален товар - %s %s".formatted(p.type, p.model) , 1);

                        // обновление списка
                        products.remove(p);
                        createList(panel, products, pointer);
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
                    new AddProduct(login, p);
                    dispose();
                }
            });

            buttonPanel.add(infoButton);
            if (code.equals("admin")) {
                buttonPanel.add(Box.createHorizontalStrut(5));
                buttonPanel.add(removeButton);
                buttonPanel.add(Box.createHorizontalStrut(5));
                buttonPanel.add(editButton);
            }
            buttonPanel.add(Box.createGlue());

            // сборка панели
            prodPanel.add(addDesc(products.get(pointer - 1 + count), pointer + count));
            prodPanel.add(buttonPanel);
            prodPanel.add(Box.createVerticalStrut(10));

            panel.add(prodPanel);
        }

        // добавление кнопок переключения
        // кнопки только для списков с количеством элементов большим 10
        if (products.size() > 10) {
            int finalPointer = pointer + count + 1;
            JPanel buttonPanel = new JPanel();

            JButton nextButton = new JButton(">");
            nextButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (pointer != 1 && finalPointer > products.size() + 1) {

                    } else {
                        createList(panel, products, pointer + 10);
                        pack();
                    }
                }
            });

            JButton prevButton = new JButton("<");
            prevButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (pointer == 1) {

                    } else {
                        createList(panel, products, pointer - 10);
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