import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

// класс описывает общую логику всех окон приложения
public abstract class Window extends JDialog {
    public Window(String title) {
        // создание окна с заголовком
        super(new JFrame(), title);

        // завершение работы приложения при закрытии окна
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                // освобождение ресурсов при закрытии
                dispose();
                System.exit(0);
            }
        });

        // запрет на изменение размеров окна
        setResizable(false);
        // видимость окна
        setVisible(true);
    }

    // создание содержимого окна
    protected abstract JPanel CreateContent();
}