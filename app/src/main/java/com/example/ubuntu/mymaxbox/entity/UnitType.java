package com.example.ubuntu.mymaxbox.entity;

public class UnitType {
    private int typeImage;
    private String typeName;

    public UnitType(int typeImage, String typeName) {
        this.typeImage = typeImage;
        this.typeName = typeName;
    }

    public int getTypeImage() {
        return typeImage;
    }

    public String getTypeName() {
        return typeName;
    }
}
