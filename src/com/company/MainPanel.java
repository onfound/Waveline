package com.company;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class MainPanel extends JPanel {

    private Line line;

    MainPanel(int width, int height) {
        line = new Line();
        setBackground(Color.decode("#090f05"));

        setFocusable(true);

        KeyListener keyListener = new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    line.tup();
                }
            }
        };
        addKeyListener(keyListener);
        ActionListener timeListener = e -> start();
        Timer timer = new Timer(10, timeListener);
        timer.start();
    }
    int k = 1;
    private void start(){
        if (k%2 == 0) line.start();
        repaint();
        k++;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        paintCurveLine(g2d);
        g2d.setColor(Color.WHITE);
        Font font = new Font("TimesNewRoman", Font.BOLD, 30);
        g2d.setFont(font);
        g2d.drawString("TAP TO PLAY", 150, 100);
    }

    private void paintCurveLine(Graphics2D g2d){
        g2d.setColor(Color.decode("#eb2228"));
        BasicStroke bs = new BasicStroke(10, BasicStroke.CAP_ROUND, BasicStroke.CAP_ROUND);
        g2d.setStroke(bs);
        for (int i = 1; i < line.getCount() ; i++) {
            g2d.drawLine(line.getLineX()[i-1],line.getLineY()[i-1],line.getLineX()[i],line.getLineY()[i]);
        }
    }

}
