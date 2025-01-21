import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerMenu extends Window {
    private String login;
    public CustomerMenu(String login) {
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
                new Authorization();
                dispose();
            }
        });

        JButton catalogButton = new JButton("Открыть каталог");
        catalogButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Catalog(login);
                dispose();
            }
        });

        // сборка интерфейса
        gridPanel.add(new JLabel("Здравствуйте, '%s'!".formatted(login)));
        gridPanel.add(Box.createVerticalStrut(10));

        gridPanel.add(new JLabel("Вернуться к авторизации:"));
        gridPanel.add(exitButton);

        gridPanel.add(new JLabel("Ассортимент:"));
        gridPanel.add(catalogButton);

        mainPanel.add(gridPanel);

        return mainPanel;
    }
}
