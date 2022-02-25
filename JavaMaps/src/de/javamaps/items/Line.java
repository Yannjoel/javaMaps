package de.javamaps.items;


import java.awt.Color;

public class Line {
    public int x1, x2, y1, y2;
    public double pos_x1, pos_x2, pos_y1, pos_y2;
    public Color color;


    public Line(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.pos_x1 = 0;
        this.pos_x2 = 0;
        this.pos_y1 = 0;
        this.pos_y2 = 0;

        this.color = Color.BLACK;

    }


    public Line(int x1, int y1, int x2, int y2, Color color) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.pos_x1 = 0;
        this.pos_x2 = 0;
        this.pos_y1 = 0;
        this.pos_y2 = 0;

        this.color = color;

    }

    public Line(double x1, double y1, double x2, double y2, Color color) {

        this.pos_x1 = x1;
        this.pos_x2 = x2;
        this.pos_y1 = y1;
        this.pos_y2 = y2;

        this.color = color;

    }
    public Line(double x1, double y1, double x2, double y2) {

        this.pos_x1 = x1;
        this.pos_x2 = x2;
        this.pos_y1 = y1;
        this.pos_y2 = y2;

        this.color = Color.BLACK;

    }


}
