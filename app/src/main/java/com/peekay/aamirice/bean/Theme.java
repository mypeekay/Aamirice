package com.peekay.aamirice.bean;

public class Theme {
    int color;
    String name;

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Theme(int color, String name) {
        this.color = color;
        this.name = name;
    }
}
