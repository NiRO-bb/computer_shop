import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    // поиск - формирует список элементов, удовлетворяющих запросу
    // затем требуется самостоятельно выполнить добавление элементов в панель
    ArrayList<Object> search(JTextField searchField, ArrayList<Object> elements) {
        Pattern pattern = Pattern.compile(searchField.getText().toLowerCase(), Pattern.CASE_INSENSITIVE);
        Matcher matcher;

        // формируется новый список
        ArrayList<Object> searchResult = new ArrayList<>();
        for (Object obj : elements) {
            matcher = pattern.matcher((obj.toString()).toLowerCase());

            if (matcher.find())
                searchResult.add(obj);
        }

        // вернуть новый список
        return searchResult;
    }

    // Добавить описание элемента
    JPanel addDesc(Object element, int number) {
        // панель надписи
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, 0));

        labelPanel.add(new JLabel("%s. %s".formatted(number, element.toString())));
        labelPanel.add(Box.createGlue());

        return labelPanel;
    }
}