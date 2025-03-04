package com.example.domain;

public class SharedClass {

    private int x = 0;
    private int y = 0;

    public void increment() {
        x++;
        y++;
    }

    public void checkForDataRaces() {
        if (y > x) {
            System.out.println("y > x - Data race is detected");
        }
    }

}
