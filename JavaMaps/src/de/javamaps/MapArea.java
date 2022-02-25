package de.javamaps;

import de.javamaps.items.Line;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

class MapArea extends JPanel {

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

	public MapArea() {
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

		setOpaque(true);
		setBackground(new Color(0,0,0,0));
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
			Line2D line2D = new Line2D.Double(line.x1, line.y1, line.x2, line.y2);
			g2d.draw(line2D);
		}

		setOpaque(true);
		setBackground(new Color(0,0,0,0));
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
		long startTime = System.currentTimeMillis();
		calcSizes();
		System.out.println(lines.size());
		lines.parallelStream().forEach(this::calcPosFromCoordinates);
		long endTime = System.currentTimeMillis();
		paintComponent(getGraphics());
		System.out.println("total pos calc time = " + (endTime-startTime));
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
			calcPosFromCoordinates(line);
		}
		paintComponent(getGraphics());
		
	}

	public void removeOldRoute() {
		setSize(0, 0);
		setEnabled(false);
	}
}
