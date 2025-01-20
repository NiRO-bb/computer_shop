import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class EmployeeList extends Window {
    private String login;

    public EmployeeList(String login) {
        super("Список сотрудников");

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

        // список сотрудников
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, 1));

        ArrayList<Object> employees = null;
        try { employees = SQL.getEmployeeList(); }
        catch (Exception e) { new Notification(e.getMessage(), 0); }

        createList(listPanel, employees, 1);

        // строка поиска
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, 0));

        JTextField searchField = new JTextField(20);

        JButton searchButton = new JButton("Найти");
        ArrayList<Object> finalEmployees = employees;
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // поиск
                ArrayList<Object> newEmployees = search(searchField, finalEmployees);

                // показать результаты поиска
                createList(listPanel, newEmployees, 1);
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
                new AdminMenu(login);
                dispose();
            }
        });

        // сборка интерфейса
        mainPanel.add(searchPanel);
        mainPanel.add(listPanel);
        mainPanel.add(exitButton);

        return mainPanel;
    }

    void createList(JPanel panel, ArrayList<Object> employees, int pointer) {
        panel.removeAll();

        int count = 0;
        for (; pointer - 1 + count < employees.size() && count < 10; count++) {
            // конкретный элемент
            Employee employee = (Employee)employees.get(pointer - 1 + count);

            // панель элемента
            JPanel prodPanel = new JPanel();
            prodPanel.setLayout(new BoxLayout(prodPanel, 1));

            // кнопки
            JPanel buttonPanel = new JPanel();

            JButton removeButton = new JButton("Удалить");
            removeButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        SQL.deleteEmployee(employee.id);

                        new Notification("Был удален сотрудник - %s - %s".formatted(employee.name, employee.post) , 1);

                        // обновление списка
                        employees.remove(employee);
                        createList(panel, employees, pointer);
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
                    new AddEmployee(login, employee);
                    dispose();
                }
            });

            buttonPanel.add(removeButton);
            buttonPanel.add(Box.createHorizontalStrut(5));
            buttonPanel.add(editButton);
            buttonPanel.add(Box.createGlue());

            // сборка панели
            prodPanel.add(addDesc(employees.get(pointer - 1 + count), pointer + count));
            prodPanel.add(buttonPanel);
            prodPanel.add(Box.createVerticalStrut(10));

            panel.add(prodPanel);
        }

        // добавление кнопок переключения
        // кнопки только для списков с количеством элементов большим 10
        if (employees.size() > 10) {
            int finalPointer = pointer + count + 1;
            JPanel buttonPanel = new JPanel();

            JButton nextButton = new JButton(">");
            nextButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (pointer != 1 && finalPointer > employees.size() + 1) {

                    } else {
                        createList(panel, employees, pointer + 10);
                        pack();
                    }
                }
            });

            JButton prevButton = new JButton("<");
            prevButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (pointer == 1) {

                    } else {
                        createList(panel, employees, pointer - 10);
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