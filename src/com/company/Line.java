package com.company;


class Line {

    private int y1 = 400;
    private int dx = 0;
    private int x1;
    private boolean invert = false;
    private boolean move = false;
    private int[] lineX = new int[40];
    private int[] lineY = new int[40];
    private int count;

    Line() {
        this.x1 = 250;
    }

    void start() {
        if (move) {
            speedup();
            move();
        } else {
            move = false;
            invert = false;
            x1 = 250;
            dx =0;
        }
        paintLine();
    }

    private void speedup() {
        if (invert){
            if (dx>0) dx-=3;
            else dx -= 1;
        }
        else {
            if (dx<0) dx += 3;
            else dx += 1;
        }
    }
    
    void tup() {
        invert = !invert;
        move = true;

    }

    private void move() {
        x1+=dx;
        if (x1 <= 0 || x1 >= 490) move = false;
    }

    private void paintLine() {
        lineX[count] = x1;
        lineY[count] = y1;
        for (int i = 0; i < count; i++) {
            lineY[i] += 9;
        }
        if (count < 39) {
            count++;
        } else {
            for (int i = 1; i < lineX.length; i++) {
                lineY[i - 1] = lineY[i];
                lineX[i - 1] = lineX[i];
            }
        }
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