package com.company.cafe.view;

import com.company.cafe.menu.Dish;
import com.company.cafe.menu.Drink;
import com.company.cafe.menu.DrinkTypeEnum;
import com.company.cafe.menu.MenuItem;
import com.company.cafe.order.InternetOrder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;


public class MainWindow extends JFrame {

    private JTextArea textArea;
    private InternetOrder internetOrder;

    private static final Drink[] drinks = new Drink[] {
            new Drink(50, "Вода", "Обычная вода", .0, DrinkTypeEnum.Water),
            new Drink(70, "Спрайт", "шкрвечащий", .0, DrinkTypeEnum.Sprite),
            new Drink(150, "Вино", "лечебное", 5, DrinkTypeEnum.Wine),
            new Drink(120, "Пиво", "Козел", .0, DrinkTypeEnum.Beer),
            new Drink(60, "Швепс", "Малиновый", .0, DrinkTypeEnum.Schweps),
    };

    private static final Dish[] dishes = new Dish[] {
            new Dish(200, "Курица", "Бедро"),
            new Dish(200, "Картошка фри", "Обжаренная"),
            new Dish(200, "макароны", "с павидлом"),
            new Dish(99, "конфеты", "шоколадные"),
    };


    public MainWindow() {
        super("Создание заказа");

        internetOrder = new InternetOrder();

        setSize(new Dimension(650, 350));
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        construct();
    }


    private void construct() {
        var panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2, 10, 0));
        add(panel);

        textArea = new JTextArea();
        textArea.setFont(new Font("TimesNewRoman", Font.PLAIN,20));
        textArea.setEnabled(false);
        textArea.setDisabledTextColor(Color.black);

        var scroll = new JScrollPane(textArea);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        panel.add(scroll);

        var rightPanel = new JPanel(new GridLayout(5, 1, 0, 10));
        panel.add(rightPanel);

        var checkout = new JButton("Заказать");
        checkout.setSize(new Dimension(200, 100));
        checkout.addActionListener(e -> {
            if (internetOrder.costTotal() == 0)
                JOptionPane.showMessageDialog(MainWindow.this,
                        "Выберите товары для покупки",
                        "Пустой заказ",
                        JOptionPane.WARNING_MESSAGE);
            else {
                var endWindow = new OrderWindow(MainWindow.this, internetOrder);
                endWindow.setVisible(true);
                setVisible(false);
            }
        });
        rightPanel.add(Box.createVerticalGlue());
        rightPanel.add(Box.createVerticalGlue());
        rightPanel.add(checkout);
        rightPanel.add(Box.createVerticalGlue());
        rightPanel.add(Box.createVerticalGlue());

        var drinkMenu = new JMenu("Добавить напиток");
        drinkMenu.setMnemonic(KeyEvent.VK_ALT);
        addMenuItemsToMenu(drinkMenu, drinks);

        var dishMenu = new JMenu("Добавить блюдо");
        addMenuItemsToMenu(dishMenu, dishes);

        var resetOrder = new JMenuItem("Убрать всё");
        resetOrder.addActionListener(e -> reset());

        var menuBar = new JMenuBar();
        menuBar.add(drinkMenu);
        menuBar.add(dishMenu);
        menuBar.add(resetOrder);
        setJMenuBar(menuBar);
    }


    private void addMenuItemsToMenu(JMenu menu, MenuItem[] items) {
        for (int i = 0; i < items.length; ++i) {
            var menuItem = new JMenuItem(items[i].getName());

            int finalI = i;
            menuItem.addActionListener(e -> {
                internetOrder.add(items[finalI]);
                textArea.setText(textArea.getText() + "Добавлено в заказ: " + items[finalI].getName() + '\n');
            });

            menu.add(menuItem);
        }
    }


    public void reset() {
        internetOrder = new InternetOrder();
        textArea.setText("");
    }
}