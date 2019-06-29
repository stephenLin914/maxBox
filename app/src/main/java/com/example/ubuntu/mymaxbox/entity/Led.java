package com.example.ubuntu.mymaxbox.entity;

public class Led {
    private int ledImg;
    private String name;

    public Led(int ledImg, String name) {
        this.ledImg = ledImg;
        this.name = name;
    }

    public int getLedImg() {
        return ledImg;
    }

    public String getName() {
        return name;
    }
}
