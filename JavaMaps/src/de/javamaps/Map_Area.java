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
    int height = 774;
    int width = 739;
    double minLon = 6.35017;
    double minLat = 49.10868;
   	double maxLon = 7.40979;
   	double maxLat = 49.64072;
   	double lon_diff = maxLon - minLon;
   	double lat_diff = maxLat - minLat;
   	double scaleLon = height/lon_diff;
	double scaleLat = width/lat_diff;
    
	public void addLine(double lon, double lat, double lon2, double lat2) {
		
		int x1 = (int)(scaleLon*(lon - minLon));
		int y1 = (int) Math.abs((scaleLat*(lat - minLat)-height));
		int x2 = (int)(scaleLon*(lon2 - minLon));
		int y2 = (int)Math.abs((scaleLat*(lat2 - minLat)-height));
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

