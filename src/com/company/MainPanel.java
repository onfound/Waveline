package com.company;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class MainPanel extends JPanel {

    private static final int widthLine = 10;
    private Line line;
    private Stick stick1;
    private Stick stick2;
    private Stick stick3;
    private int lineY;
    private final int distanceSticks;
    private static boolean startGame, gameOver, start2, start3 = false;
    private int score, count, heightPanel, widthPanel;


    MainPanel(int widthPanel, int heightPanel) {
        this.heightPanel = heightPanel;
        this.widthPanel = widthPanel;
        line = new Line(widthPanel, heightPanel, widthLine);
        lineY = line.getY1();
        stick1 = new Stick(widthPanel, heightPanel);
        stick2 = new Stick(widthPanel, heightPanel);
        stick3 = new Stick(widthPanel, heightPanel);
        score = 0;
        count = 0;
        distanceSticks = heightPanel / 3;

        /* Default paintComponent*/

        setBackground(Color.decode("#090f05"));
        setFont(new Font("Sprite Graffiti Shadow", Font.BOLD, 30));
        setFocusable(true);

        /*Listeners*/

        KeyListener keyListener = new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    startGame = true;
                    line.tap();
                }
                if (gameOver && e.getKeyCode() == KeyEvent.VK_ENTER) {
                    gameOver = false;
                    startGame = false;
                    start2 = false;
                    start3 = false;
                    line = new Line(widthPanel, heightPanel, widthLine);
                    stick1 = new Stick(widthPanel, heightPanel);
                    stick2 = new Stick(widthPanel, heightPanel);
                    stick3 = new Stick(widthPanel, heightPanel);
                    score = 0;
                    count = 0;
                }
            }
        };
        addKeyListener(keyListener);

        ActionListener timeListener = e -> repaint();
        ActionListener timeListener1 = e -> {
            if (!gameOver) {
                line.start();
                if (startGame) {
                    final int startStick = Stick.delay + distanceSticks;
                    if (!start2 && stick1.getY() > startStick) start2 = true;
                    if (!start3 && stick2.getY() > startStick) start3 = true;
                    stick1.start();
                    if (start2) stick2.start();
                    if (start3) stick3.start();
                    isGameOver();
                }
            }
        };
        Timer timer = new Timer(10, timeListener);              //перерисовка
        Timer timer1 = new Timer(20, timeListener1);           //перерасчет координат и тп
        timer.start();
        timer1.start();
    }

    private void isGameOver() {
        if (stick1.getY() + widthLine > lineY && stick1.getY() - widthLine < lineY) {
            if (stick1.isDoubleSticks() && line.getX1() > stick1.getX3() - widthLine && line.getX1() < stick1.getX4() + widthLine)
                gameOver = true;
            if (line.getX1() > stick1.getX1() - widthLine && line.getX1() < stick1.getX2() + widthLine) gameOver = true;
            else score++;
        }

        if (stick2.getY() + widthLine > lineY && stick2.getY() - widthLine < lineY) {
            if (stick2.isDoubleSticks() && line.getX1() > stick2.getX3() - widthLine && line.getX1() < stick2.getX4() + widthLine)
                gameOver = true;
            if (line.getX1() > stick2.getX1() - widthLine && line.getX1() < stick2.getX2() + widthLine) gameOver = true;
            else score++;
        }

        if (stick3.getY() + widthLine > lineY && stick3.getY() - widthLine < lineY) {
            if (stick3.isDoubleSticks() && line.getX1() > stick3.getX3() - widthLine && line.getX1() < stick3.getX4() + widthLine)
                gameOver = true;
            if (line.getX1() > stick3.getX1() - widthLine && line.getX1() < stick3.getX2() + widthLine) gameOver = true;
            else score++;
        }

        if (line.isCrashed()) gameOver = true;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g2d);
        g2d.setFont(new Font("Bebas Neue", Font.BOLD, 50));
        g2d.setStroke(new BasicStroke(widthLine, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        if (!gameOver) {
            g2d.setColor(Color.decode("#ff2e2e"));
            paintCurveLine(g2d);

            g2d.setColor(Color.decode("#eee8e0"));
            paintSticks(g2d);
            if (startGame) {
                g2d.setColor(Color.WHITE);
                g2d.drawString(String.valueOf(score), 240, 100);
            }

        } else {
            paintGameOver(g2d);
        }
        if (!startGame) {
            g2d.setColor(Color.WHITE);
            g2d.drawString("Tap to play", 150, 100);
        }
    }

    private void paintCurveLine(Graphics2D g2d) {
        for (int i = 1; i < line.getCount(); i++) {
            g2d.drawLine(line.getLineX()[i - 1], line.getLineY()[i - 1], line.getLineX()[i], line.getLineY()[i]);
        }
    }

    private void paintSticks(Graphics2D g2d) {
        int y1 = stick1.getY();
        int y2 = stick2.getY();
        int y3 = stick3.getY();

        g2d.drawLine(stick1.getX1(), y1, stick1.getX2(), y1);
        if (stick1.isDoubleSticks()) {
            g2d.drawLine(stick1.getX3(), y1, stick1.getX4(), y1);
        }
        g2d.drawLine(stick2.getX1(), y2, stick2.getX2(), y2);
        if (stick2.isDoubleSticks()) {
            g2d.drawLine(stick2.getX3(), y2, stick2.getX4(), y2);
        }
        g2d.drawLine(stick3.getX1(), y3, stick3.getX2(), y3);
        if (stick3.isDoubleSticks()) {
            g2d.drawLine(stick3.getX3(), y3, stick3.getX4(), y3);
        }
    }

    private void paintGameOver(Graphics2D g2d) {
        if (count < heightPanel * 2) {
            g2d.setColor((Color.decode("#ff2e2e")));
            g2d.setStroke(new BasicStroke(count, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2d.drawLine(line.getX1(), lineY, line.getX1(), lineY);
            count += 20;
        } else paintResult(g2d);
    }

    private void paintResult(Graphics2D g2d) {
        g2d.drawString("Game over :(", 150, 150);
        g2d.setColor(Color.WHITE);
        g2d.drawString("you scrore: " + score, 120, 300);
        g2d.setFont(new Font("Bebas Neue", Font.BOLD, 20));
        g2d.drawString("press enter to replay", 170, 500);
    }

}
