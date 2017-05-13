package com.company;

import javax.swing.*;

public class Main extends JFrame {
    private static final int DEFAULT_WIDTH = 500;
    private static final int DEFAULT_HEIGHT = 700;

    private Main(String title) {
        super(title);
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        MainPanel mainPanel = new MainPanel(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        add(mainPanel);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main("myFirstGUIApplication"));
    }
}
