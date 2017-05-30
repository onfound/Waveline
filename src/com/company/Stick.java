package com.company;

import java.util.Random;

class Stick {
    private int x1, x2, x3, x4, y;
    final static int delay = -350;
    private final int width, height;
    private static int rm, rm1;
    private boolean doubleSticks;


    Stick(int width, int height) {
        this.width = width;
        this.height = height;
        this.y = delay;
        changeStick();
    }

    void start() {
        move();
    }

    private void changeStick() {
        doubleSticks = false;

        for (int i = 0; i < 5; i++) {
            rm = new Random().nextInt(3);
            if (rm1 != rm) {
                rm1 = rm;
                break;
            }
        }

        switch (rm) {
            case 2:
                x1 = 0;
                x2 = width / 2 - 100;
                x3 = width / 2 + 100;
                x4 = width;
                doubleSticks = true;
                break;
            case 1:
                x1 = 0;
                x2 = width - 200;
                break;
            case 0:
                x1 = 200;
                x2 = width;
                break;
        }
    }

    private void move() {
        if (y > height) {
            changeStick();
            y = delay;
        }
        y += 6;
    }

    int getX3() {
        return x3;
    }

    int getX4() {
        return x4;
    }

    int getY() {
        return y;
    }

    int getX1() {
        return x1;
    }

    int getX2() {
        return x2;
    }

    boolean isDoubleSticks() {
        return doubleSticks;
    }
}
