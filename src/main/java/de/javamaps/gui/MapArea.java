package de.javamaps.gui;

import de.javamaps.items.Line;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

class MapArea extends JPanel {

	List<Line> lines = new ArrayList<>();
	int height;
	int width;
	double minLon = 5.864417;
	double minLat = 47.26543;
	double maxLon = 15.05078;
	double maxLat = 55.14777;
	double longitudeSpan;
	double latitudeSpan;
	double ratio;
	
	double scaleLon;
	double scaleLat;

	public MapArea() {
		setBorder(BorderFactory.createLineBorder(Color.black));
		
		height = this.getHeight();
		width = this.getWidth();

		calcSizes();
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
			g2d.setColor(line.getColor());
			Line2D line2D = new Line2D.Double(line.getX1(), line.getY1(), line.getX2(), line.getY2());
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
		calcSizes();
		System.out.println(lines.size());
		lines.parallelStream().forEach(this::calcPosFromCoordinates);
		paintComponent(getGraphics());
	}
	
	
	public Line calcPosFromCoordinates(Line line){
		if(line.getPosX1() != 0 && line.getPosX2() != 0 && line.getPosY1() != 0 && line.getPosY2() != 0){
			line.setX1((int) (scaleLon * (line.getPosX1() - minLon)));
			line.setY1((int) Math.abs((scaleLat * (line.getPosY1() - minLat) - height)));
			line.setX2((int) (scaleLon * (line.getPosX2() - minLon)));
			line.setY2((int) Math.abs((scaleLat * (line.getPosY2() - minLat) - height)));
		}
		return line;
	}
	
	public void calcSizes(){
		height = this.getHeight();
		width = this.getWidth();

		longitudeSpan = maxLon - minLon;		//Map width
		latitudeSpan = maxLat - minLat;		//Map height
		ratio = longitudeSpan / latitudeSpan;	//Aspect ratio
		scaleLon = height / longitudeSpan;	//Pixel per longitude
		scaleLat = width / latitudeSpan;	//Pixel per latitude

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
