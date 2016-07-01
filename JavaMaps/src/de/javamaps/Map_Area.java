package de.javamaps;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

class Map_Area extends JPanel {

	List<Line> lines = new ArrayList<Line>();
    public Map_Area() {
        setBorder(BorderFactory.createLineBorder(Color.black));
    }

    public Dimension getPreferredSize() {
        return new Dimension(250,200);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);       
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(
        		RenderingHints.KEY_ANTIALIASING,
        		RenderingHints.VALUE_ANTIALIAS_ON);
        
        
        
        for(Line line : lines){
        	g2d.setColor(line.color);
        	g2d.drawLine(line.x1, line.y1, line.x2, line.y2);
        }
        
        
        
        
    }
    public void addLine(int x1, int y1, int x2, int y2){
    	lines.add(new Line(x1,y1,x2,y2));
    	
    }
    public void addLine(int x1, int y1, int x2, int y2,Color color){
    	lines.add(new Line(x1,y1,x2,y2, color));
    	
    }
    int height = 728;
    int width = 574;
    float minLon = 5.8667f;
   	float minLat = 	47.2703f;
   	float lon_diff = 15.0419f - minLon;
   	float lat_diff = 55.0585f - minLat;
	float scaleLon = height/lon_diff;
	float scaleLat = width/lat_diff;
    
	public void addLine(float lon, float lat, float lon2, float lat2) {
		
		int x1 = (int)(scaleLon*(lon - minLon));
		int y1 = (int)(scaleLat*(lat - minLat));
		int x2 = (int)(scaleLon*(lon2 - minLon));
		int y2 = (int)(scaleLat*(lat2 - minLat));
		lines.add(new Line(x1,y1,x2,y2, Color.blue));
		
	}
	
	public void drawLines(){
		repaint();
	}
}


class Line{
	public int x1, x2, y1,y2;
	Color color;
	public Line(int x1, int y1, int x2, int y2){
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.color = Color.BLACK;
	
	}
	public Line(int x1, int y1, int x2, int y2, Color color){
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.color = color;
	}
	
	
}

