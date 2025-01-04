import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Notification extends JDialog {
    private String message;

    public Notification(String message){
        // создание окна с заголовком
        super(new JFrame(), "Уведомление", true);

        this.message = message;

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

        // кнопка
        JButton closeButton = new JButton("Закрыть");
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        closeButton.setAlignmentX(CENTER_ALIGNMENT);

        // сборка интерфейса
        mainPanel.add(new JLabel("Во время работы возникла ошибка:"));
        mainPanel.add(new JLabel(message));
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(closeButton);

        return mainPanel;
    }
}
