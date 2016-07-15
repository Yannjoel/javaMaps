package de.javamaps;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

class Map_Area extends JPanel {

	List<Line> lines = new ArrayList<Line>();
	int height;
	int width;
	double minLon = 5.864417;
	double minLat = 47.26543;
	double maxLon = 15.05078;
	double maxLat = 55.14777;
	double lon_diff;
	double lat_diff;
	double ratio;
	
	double scaleLon;
	double scaleLat;

	public Map_Area() {
		setBorder(BorderFactory.createLineBorder(Color.black));
		
		height = this.getHeight();
		width = this.getWidth();
		
		lon_diff = maxLon - minLon;		//Breite der Karte
		lat_diff = maxLat - minLat;		//Höhe der Karte
		ratio = lon_diff / lat_diff;	//Seitenverhältnis
		scaleLon = height / lon_diff;	//Pixel pro Breitengrad
		scaleLat = width / lat_diff;	//Pixel pro Längengrad
		
		
		
		if(ratio > 1){
			scaleLat = ratio*scaleLon;
		}
		else{
			scaleLon = ratio*scaleLat;
		}
	}

	public Dimension getPreferredSize() {
		return new Dimension(250, 200);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		for (Line line : lines) {
			g2d.setColor(line.color);
			g2d.drawLine(line.x1, line.y1, line.x2, line.y2);
		}

	}

	public void addLine(int x1, int y1, int x2, int y2) {
		lines.add(new Line(x1, y1, x2, y2));

	}

	public void addLine(int x1, int y1, int x2, int y2, Color color) {
		lines.add(new Line(x1, y1, x2, y2, color));

	}

	
	
	

	public void addLine(double lon, double lat, double lon2, double lat2) {
		
		Line line = new Line(lon,lat,lon2,lat2,Color.BLUE);
		line = calcPosFromCoordinates(line);
		lines.add(line);

	}

	public void addLine(double lon, double lat, double lon2, double lat2, Color color) {
		
		Line line = new Line(lon,lat,lon2,lat2,color);
		line = calcPosFromCoordinates(line);
		lines.add(line);

	}

	public void drawLines() {
		calcSizes();
		for (Line line : lines) {
			line = calcPosFromCoordinates(line);
		}
		repaint();
	}
	
	
	public Line calcPosFromCoordinates(Line line){
		if(line.pos_x1 != 0 && line.pos_x2 != 0 && line.pos_y1 != 0 && line.pos_y2 != 0){
			line.x1 = (int) (scaleLon * (line.pos_x1 - minLon));
			line.y1 = (int) Math.abs((scaleLat * (line.pos_y1 - minLat) - height));
			line.x2 = (int) (scaleLon * (line.pos_x2 - minLon));
			line.y2 = (int) Math.abs((scaleLat * (line.pos_y2 - minLat) - height));
		}
		
		return line;
	}
	
	public void calcSizes(){
		height = this.getHeight();
		width = this.getWidth();
		
		lon_diff = maxLon - minLon;		//Breite der Karte
		lat_diff = maxLat - minLat;		//Höhe der Karte
		ratio = lon_diff / lat_diff;	//Seitenverhältnis
		scaleLon = height / lon_diff;	//Pixel pro Breitengrad
		scaleLat = width / lat_diff;	//Pixel pro Längengrad
		
		
		
		if(ratio > 1){
			scaleLat = ratio*scaleLon;
		}
		else{
			scaleLon = ratio*scaleLat;
		}
	}
	
	
	
	
	public void resized(){
			
		calcSizes();
		for (Line line : lines) {
			line = calcPosFromCoordinates(line);
		}
		repaint();
		
		
	}
	
	public void removeRoute(){
		for(Line line : lines){
			if(line.color == Color.RED){
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {

							lines.remove(line);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}	
		}
		repaint();
	}
	
}

class Line {
	public int x1, x2, y1, y2;
	double pos_x1, pos_x2, pos_y1, pos_y2;
	Color color;

	
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
