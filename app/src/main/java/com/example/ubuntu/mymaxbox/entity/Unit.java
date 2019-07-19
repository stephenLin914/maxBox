package com.example.ubuntu.mymaxbox.entity;

public class Unit {
    private String name;
    private double rate;

    public Unit(String name, double rate) {
        this.name = name;
        this.rate = rate;
    }

    public String getName() {
        return name;
    }

    public double getRate() {
        return rate;
    }
}
