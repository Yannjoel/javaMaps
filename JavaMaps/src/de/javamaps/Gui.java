package de.javamaps;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import de.javamaps.items.ComboItem;
import de.javamaps.items.Vertex;

import javax.swing.JComboBox;
import java.awt.Canvas;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Gui {

	private JFrame frame;
	private javaMap main;


	/**
	 * Create the application.
	 */
	public Gui() {
		
		initialize();
		
	}
	JComboBox cb_start;
	JComboBox cb_target;
	private Map_Area map;
	Graphics g_map;
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1200, 900);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.addComponentListener(new FrameEvent());
		
		cb_start = new JComboBox();
		cb_start.setBounds(10, 11, 180, 20);
		frame.getContentPane().add(cb_start);
		
		cb_target = new JComboBox();
		cb_target.setBounds(10, 42, 180, 20);
		frame.getContentPane().add(cb_target);
		
		JButton btn_start = new JButton("Find Route");
		btn_start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 map.removeRoute();
				 JOptionPane.showMessageDialog(null, javaMap.calcRoute(((ComboItem) cb_start.getSelectedItem()).getId(), ((ComboItem) cb_target.getSelectedItem()).getId()));
			}
		});
		
		btn_start.setBounds(10, 90, 180, 33);
		frame.getContentPane().add(btn_start);
		
		map = new Map_Area();
		map.setBounds(200, 11, 974, 839);
		frame.getContentPane().add(map);
		
		
		
	}
	 private class FrameEvent implements ComponentListener{
	        
	        public void componentResized(ComponentEvent arg0) {
	        	
	        	map.setSize(frame.getWidth() - 220, frame.getHeight()- 61);
	        	map.resized();

	        }

			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
	        
	    }
	
	
	public void addLine(Vertex x1, Vertex x2){
		map.addLine(x1.getLon(), x1.getLat(),x2.getLon(), x2.getLat());
	}
	public void addLine(Vertex x1, Vertex x2, Color color){
		map.addLine(x1.getLon(), x1.getLat(),x2.getLon(), x2.getLat(), color);
	}
	public void drawRoute(Stack<Vertex> routeStack){
		Vertex lastPoint = null;
		for(Vertex point : routeStack){
			if(lastPoint != null){
				addLine(lastPoint, point ,Color.red);
			}
			lastPoint = point;
		}
		drawLines();
	}
	public void drawLines(){
		map.drawLines();
	}
	public void setVisible(boolean b) {
		frame.setVisible(b);
		
	}
	
	public void addLocations(TreeMap<String, List<Long>> positions){
		for (Map.Entry<String, List<Long>> entry : positions.entrySet()) {
			for(long id : entry.getValue()){
				cb_start.addItem(new ComboItem(id,entry.getKey()));
				cb_target.addItem(new ComboItem(id,entry.getKey()));
			}
		
		
		}
	}
}
