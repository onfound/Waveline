package com.company;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class MainPanel extends JPanel {

    private static final int widthLine = 10;
    private static final int edge = widthLine / 2;
    private final Line line;
    private final Sticks sticks1, sticks2, sticks3;
    private final int lineY, distanceSticks;
    private static boolean startGame, gameOver, start2, start3 = false;
    private int score;


    MainPanel(int widthPanel, int heightPanel) {
        line = new Line(widthPanel, heightPanel, edge);
        lineY = line.getY1();
        sticks1 = new Sticks(widthPanel, heightPanel);
        sticks2 = new Sticks(widthPanel, heightPanel);
        sticks3 = new Sticks(widthPanel, heightPanel);
        score = 0;
        distanceSticks = heightPanel / 3;

        /* Default paintComponent*/

        setBackground(Color.decode("#090f05"));
        setFont(new Font("Sprite Graffiti Shadow", Font.BOLD, 30));
        setFocusable(true);

        KeyListener keyListener = new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    startGame = true;
                    line.tap();
                }
                if (gameOver && e.getKeyCode() == KeyEvent.VK_ENTER) {
                    gameOver = false;
                }
            }
        };
        addKeyListener(keyListener);

        ActionListener timeListener = e -> repaint();
        ActionListener timeListener1 = e -> {
            if (!gameOver) {
                line.start();
                if (startGame) {
                    final int startStick = Sticks.delay + distanceSticks;
                    if (!start2 && sticks1.getY() > startStick) start2 = true;
                    if (!start3 && sticks2.getY() > startStick) start3 = true;
                    sticks1.start();
                    if (start2) sticks2.start();
                    if (start3) sticks3.start();
                    gameOver();
                }
            }
        };
        Timer timer = new Timer(20, timeListener);              //перерисовка
        Timer timer1 = new Timer(30, timeListener1);           //перерасчет координат и тп
        timer.start();
        timer1.start();
    }

    private void gameOver() {
        if (sticks1.getY() + widthLine > lineY && sticks1.getY() - widthLine < lineY) {
            if (line.getX1() > sticks1.getX1() - widthLine && line.getX1() < sticks1.getX2() + widthLine) gameOver = true;
            else score++;
        }
        if (sticks2.getY()+ widthLine > lineY && sticks2.getY() - widthLine < lineY) {
            if (line.getX1() > sticks2.getX1() - widthLine && line.getX1() < sticks2.getX2() + widthLine) gameOver = true;
            else score++;
        }
        if (sticks3.getY() + widthLine > lineY && sticks3.getY() - widthLine < lineY) {
            if (line.getX1() > sticks3.getX1() - widthLine && line.getX1() < sticks3.getX2() + widthLine) gameOver = true;
            else score++;
        }
        if (line.isCrashed()) gameOver = true;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        BasicStroke bs = new BasicStroke(widthLine, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        g2d.setFont(new Font("Sprite Graffiti Shadow", Font.BOLD, 30));
        g2d.setStroke(bs);

        if (!gameOver) {
            g2d.setColor(Color.decode("#ff2e2e"));
            paintCurveLine(g2d);

            g2d.setColor(Color.decode("#eee8e0"));
            paintSticks(g2d);

            g2d.setColor(Color.WHITE);
            g2d.drawString("" + score, 240, 100);

        } else {
            paintResult(g2d);
        }
        if (!startGame) {
            g2d.setColor(Color.WHITE);
            g2d.drawString("tap to play", 150, 100);
        }
    }

    private void paintCurveLine(Graphics2D g2d) {
        for (int i = 1; i < line.getCount(); i++) {
            g2d.drawLine(line.getLineX()[i - 1], line.getLineY()[i - 1], line.getLineX()[i], line.getLineY()[i]);
        }
    }

    private void paintSticks(Graphics2D g2d) {
        int y1 = sticks1.getY();
        int y2 = sticks2.getY();
        int y3 = sticks3.getY();

        g2d.drawLine(sticks1.getX1(), y1, sticks1.getX2(), y1);
        if (sticks1.isDoubleSticks()) {
            g2d.drawLine(sticks1.getX3(), y1, sticks1.getX4(), y1);
        }
        g2d.drawLine(sticks2.getX1(), y2, sticks2.getX2(), y2);
        if (sticks2.isDoubleSticks()) {
            g2d.drawLine(sticks2.getX3(), y2, sticks2.getX4(), y2);
        }
        g2d.drawLine(sticks3.getX1(), y3, sticks3.getX2(), y3);
        if (sticks3.isDoubleSticks()) {
            g2d.drawLine(sticks3.getX3(), y3, sticks3.getX4(), y3);
        }
    }

    private void paintResult(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        g2d.drawString("Game over :(", 150, 300);
        g2d.drawString("" + score, 230, 400);
    }

}
