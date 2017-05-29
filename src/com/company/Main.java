package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class Main extends JFrame {
    private static final int DEFAULT_WIDTH = 500;
    private static final int DEFAULT_HEIGHT = 700;

    private Main(String title) {
        super(title);
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        add(new MainPanel(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        try {
            setIconImage(ImageIO.read(new File("data\\icon.png")));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main("Waveline"));
    }
}
