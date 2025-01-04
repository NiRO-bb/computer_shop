import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmployeeMenu extends Window {
    private String login;

    public EmployeeMenu(String login) {
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
        JButton catalogButton = new JButton("Открыть каталог");
        catalogButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //new Catalog();
                dispose();
            }
        });

        // кнопки - экземпляры
        JButton addCopy = new JButton("Добавить запись");
        addCopy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //new AddProductCopy(login, null);
                dispose();
            }
        });

        JButton editCopy = new JButton("Изменить запись");
        editCopy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //new EditProductCopy(login);
                dispose();
            }
        });

        // кнопки - операции
        JButton transactionAdd = new JButton("Новая операция");
        transactionAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //new AddTransaction(login);
                dispose();
            }
        });
        JButton transactionHistory = new JButton("История операций");
        transactionHistory.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //new HistoryOfTransaction(login);
                dispose();
            }
        });

        // сборка интерфейса
        gridPanel.add(new JLabel("Здравствуйте, '%s'!".formatted(login)));
        gridPanel.add(Box.createVerticalStrut(10));

        gridPanel.add(new JLabel("Вернуться к авторизации:"));
        gridPanel.add(exitButton);
        //gridPanel.add(Box.createVerticalStrut(10));

        gridPanel.add(new JLabel("Ассортимент:"));
        gridPanel.add(catalogButton);
        //gridPanel.add(Box.createVerticalStrut(10));

        gridPanel.add(new JLabel("Работа с товаром:"));
        gridPanel.add(addCopy);
        gridPanel.add(editCopy);
        //gridPanel.add(Box.createVerticalStrut(10));

        gridPanel.add(new JLabel("Операции:"));
        gridPanel.add(transactionAdd);
        gridPanel.add(transactionHistory);

        mainPanel.add(gridPanel);

        return mainPanel;
    }
}