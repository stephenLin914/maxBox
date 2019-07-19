package com.example.ubuntu.mymaxbox.entity;

public class UnitListItem {
    private double value;
    private String name;

    public UnitListItem(double value, String name) {
        this.value = value;
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
