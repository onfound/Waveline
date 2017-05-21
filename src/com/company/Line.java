package com.company;


class Line {

    private double dx = 0.0;
    private boolean move, invert, crashed = false;
    private int[] lineX = new int[50];
    private int[] lineY = new int[50];
    private int x1, y1, count;
    private static int width, widthLine;

    Line(int width, int height, int widthLine) {
        Line.width = width;
        Line.widthLine = widthLine;
        x1 = width / 2;
        y1 = height / 5 * 3;
    }

    void start() {
        if (move) {
            speedup();
            move();
        } else {
            invert = false;
            x1 = width / 2;
            dx = 0.0;
        }
        paintLine();
    }

    private void speedup() {
        if (invert) {
            if (dx > 0) dx -= 1;
            else dx -= 0.5;
        } else {
            if (dx < 0) dx += 1;
            else dx += 0.5;
        }
    }

    void tap() {
        invert = !invert;
        move = true;
    }

    private void move() {
        x1 += dx;
        if (x1 <= 0 || x1 >= width - widthLine) {
            move = false;
            crashed = true;
        }
    }

    private void paintLine() {
        lineX[count] = x1;
        lineY[count] = y1;
        for (int i = 0; i < count; i++) {
            lineY[i] += 6;                  //длина/скорость/закругление line
        }
        if (count < 49) {
            count++;
        } else {
            for (int i = 1; i < lineX.length; i++) {
                lineY[i - 1] = lineY[i];
                lineX[i - 1] = lineX[i];
            }
        }
    }

    boolean isCrashed() {
        return crashed;
    }

    int getX1() {
        return x1;
    }

    int getY1() {
        return y1;
    }

    int[] getLineX() {
        return lineX;
    }

    int[] getLineY() {

        return lineY;
    }

    int getCount() {
        return count;
    }
}