package de.javamaps.items;


import java.awt.Color;

public class Line {
    private int x1, x2, y1, y2;
    private double posX1, posX2, posY1, posY2;
    private Color color;


    public Line(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.posX1 = 0;
        this.posX2 = 0;
        this.posY1 = 0;
        this.posY2 = 0;

        this.color = Color.BLACK;

    }


    public Line(int x1, int y1, int x2, int y2, Color color) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.posX1 = 0;
        this.posX2 = 0;
        this.posY1 = 0;
        this.posY2 = 0;

        this.color = color;

    }

    public Line(double x1, double y1, double x2, double y2, Color color) {

        this.posX1 = x1;
        this.posX2 = x2;
        this.posY1 = y1;
        this.posY2 = y2;

        this.color = color;

    }
    public Line(double x1, double y1, double x2, double y2) {

        this.posX1 = x1;
        this.posX2 = x2;
        this.posY1 = y1;
        this.posY2 = y2;

        this.color = Color.BLACK;

    }

    public int getX1() {
        return x1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public int getX2() {
        return x2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public int getY1() {
        return y1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public int getY2() {
        return y2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }

    public double getPosX1() {
        return posX1;
    }

    public void setPosX1(double posX1) {
        this.posX1 = posX1;
    }

    public double getPosX2() {
        return posX2;
    }

    public void setPosX2(double posX2) {
        this.posX2 = posX2;
    }

    public double getPosY1() {
        return posY1;
    }

    public void setPosY1(double posY1) {
        this.posY1 = posY1;
    }

    public double getPosY2() {
        return posY2;
    }

    public void setPosY2(double posY2) {
        this.posY2 = posY2;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
