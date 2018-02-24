package com.squidsquads.model.traffic;

public class ScreenSize {
    private int height;
    private int width;

    public ScreenSize() {
    }

    public ScreenSize(int height, int width) {
        this.height = height;
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
