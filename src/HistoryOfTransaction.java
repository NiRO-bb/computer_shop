import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class HistoryOfTransaction extends Window {
    private String login;

    public HistoryOfTransaction(String login) {
        super("История операций");

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

        // список операций
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, 1));

        ArrayList<Object> transactions = null;
        try { transactions = SQL.getTransactionList(); }
        catch (Exception e) { new Notification(e.getMessage(), 0); }

        createList(listPanel, transactions, 1);

        // строка поиска
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, 0));

        JTextField searchField = new JTextField(20);

        JButton searchButton = new JButton("Найти");
        ArrayList<Object> finalTransactions = transactions;
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // поиск
                ArrayList<Object> newTransactions = search(searchField, finalTransactions);

                // показать результаты поиска
                createList(listPanel, newTransactions, 1);
                pack();
            }
        });

        searchPanel.add(new JLabel("Введите данные операции:"));
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

    void createList(JPanel panel, ArrayList<Object> transactions, int pointer) {
        panel.removeAll();

        int count = 0;
        for (; pointer - 1 + count < transactions.size() && count < 10; count++) {
            // панель элемента
            JPanel prodPanel = new JPanel();
            prodPanel.setLayout(new BoxLayout(prodPanel, 1));

            // сборка панели
            prodPanel.add(addDesc(transactions.get(pointer - 1 + count), pointer + count));
            prodPanel.add(Box.createVerticalStrut(10));

            panel.add(prodPanel);
        }

        // добавление кнопок переключения
        // кнопки только для списков с количеством элементов большим 10
        if (transactions.size() > 10) {
            int finalPointer = pointer + count + 1;
            JPanel buttonPanel = new JPanel();

            JButton nextButton = new JButton(">");
            nextButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (pointer != 1 && finalPointer > transactions.size() + 1) {

                    } else {
                        createList(panel, transactions, pointer + 10);
                        pack();
                    }
                }
            });

            JButton prevButton = new JButton("<");
            prevButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (pointer == 1) {

                    } else {
                        createList(panel, transactions, pointer - 10);
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
