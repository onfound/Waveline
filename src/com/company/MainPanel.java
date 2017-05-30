package com.company;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;

class MainPanel extends JPanel {
    private static final int widthLine = 10;
    private Line line;
    private Stick[] sticks;
    private boolean startGame, gameOver = false;
    private int score;
    private int oldScore;
    private int maxScore;
    private int count;
    private final int heightPanel;
    private long timeMS;
    private int distanceStick;
    private String text;
    private Font font;
    private File file;

    MainPanel(int widthPanel, int heightPanel) {
        this.heightPanel = heightPanel;

        sticks = new Stick[3];
        for (int i = 0; i < 3; i++) {
            sticks[i] = new Stick(widthPanel, heightPanel);
        }

        line = new Line(widthPanel, heightPanel, widthLine);

        text = new TextForPlay().getText();
        file = new File("data\\db\\score.txt");
        score = oldScore = 0;
        maxScore = readScore();

        /* Default paintComponent*/

        font = new Font("Bebas Neue", Font.BOLD, 50);
        setBackground(Color.decode("#170c24"));
        setFont(font);
        setFocusable(true);

        /*Listeners*/

        KeyListener keyListener = new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    long currentTime = System.currentTimeMillis();
                    if (currentTime > timeMS + 50) {
                        startGame = true;
                        line.tap();
                    }
                    timeMS = currentTime;
                }

                if (gameOver && e.getKeyCode() == KeyEvent.VK_ENTER) {
                    line = new Line(widthPanel, heightPanel, widthLine);
                    for (int i = 0; i < 3; i++) {
                        sticks[i] = new Stick(widthPanel, heightPanel);
                    }
                    text = new TextForPlay().getText();
                    gameOver = false;
                    startGame = false;
                    score = oldScore = 0;
                }
            }
        };
        addKeyListener(keyListener);

        ActionListener timeListener = e -> repaint();

        ActionListener timeListener1 = e -> {
            if (!gameOver) {
                line.start();
                if (startGame) {
                    int y0 = sticks[0].getY();
                    int y1 = sticks[1].getY();
                    int y2 = sticks[2].getY();
                    distanceStick = heightPanel / 2 + Stick.delay;
                    if (y2 == Stick.delay || y1 > distanceStick || y2 > distanceStick) sticks[0].start();
                    if (y0 > distanceStick || y1 > distanceStick) sticks[1].start();
                    if (y1 > distanceStick || y2 > distanceStick) sticks[2].start();
                    isGameOver();
                }
            }
        };
        Timer timer1 = new Timer(10, timeListener);
        Timer timer2 = new Timer(14, timeListener1);
        timer1.start();
        timer2.start();
    }

    private void isGameOver() {
        for (Stick element : sticks) {
            if (element.getY() + 3 > line.getY1() && element.getY() - 3 < line.getY1()) {
                if (element.isDoubleSticks() && line.getX1() > element.getX3() - widthLine && line.getX1() < element.getX4() + widthLine)
                    gameOver = true;
                if (line.getX1() > element.getX1() - widthLine && line.getX1() < element.getX2() + widthLine)
                    gameOver = true;
                else score++;
            }
        }
        if (line.isCrashed()) gameOver = true;
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(widthLine, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        if (!gameOver) {
            paintCurveLine(g2d);
            paintSticks(g2d);
            if (startGame) {
                g2d.setColor(Color.WHITE);
                if (score != oldScore) {
                    g2d.setFont(font.deriveFont(Font.BOLD, count));
                    count -= 1;
                } else count = 60;  // анимация для score
                if (count == 50) {
                    oldScore = score;
                }
                g2d.drawString(String.valueOf(score), getWidth() / 2, 100);   // после 100 очков счет пишется также по центру
            }

        } else {
            g2d.setColor(Color.decode("#e8dc00"));
            paintBang(g2d);
        }
        if (!startGame) {
            g2d.setColor(Color.WHITE);
            g2d.drawString(text, (getWidth() - text.length() * 20) / 2, 100);
        }
    }

    private void paintCurveLine(Graphics2D g2d) {
        for (int i = 1; i < line.getCount(); i++) {
            if (i % 2 != 0) g2d.setColor(Color.decode("#e8dc00"));
            else g2d.setColor(Color.BLACK);
            g2d.drawLine(line.getLineX()[i - 1], line.getLineY()[i - 1], line.getLineX()[i], line.getLineY()[i]);
        }
    }

    private void paintSticks(Graphics2D g2d) {
        g2d.setColor(Color.decode("#02eff7"));
        for (Stick element : sticks) {
            int y = element.getY();
            g2d.drawLine(element.getX1(), y, element.getX2(), y);
            if (element.isDoubleSticks()) {
                g2d.drawLine(element.getX3(), y, element.getX4(), y);
            }
        }
    }

    private void paintBang(Graphics2D g2d) {
        if (count < heightPanel * 2) {
            int radius = count / 2;
            g2d.fillOval(line.getX1() - radius, line.getY1() - radius, count, count);
            count += 40;
        } else paintResult(g2d);
    }

    private void paintResult(Graphics2D g2d) {
        g2d.drawString("Game over", 150, 150);
        g2d.setColor(Color.WHITE);
        if (score > maxScore) {
            maxScore = score;
            g2d.drawString("you new record: " + score, 120, 300);
            writeScore(maxScore);
        } else {
            g2d.drawString("Best scrore: " + maxScore, 100, 400);
            g2d.setFont(font.deriveFont(Font.BOLD, 35));
            g2d.drawString("You scrore: " + score, 150, 300);
        }
        g2d.setFont(font.deriveFont(Font.BOLD, 20));
        g2d.drawString("press enter to replay", 170, 500);
    }

    private void writeScore(int maxScore) {
        try {
            try (PrintWriter out = new PrintWriter(file.getAbsoluteFile())) {
                out.print(maxScore);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Integer readScore() {
        try (BufferedReader in = new BufferedReader(new FileReader(file.getAbsoluteFile()))) {
            return new Integer(in.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}