package de.javamaps;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.util.Stack;

import javax.swing.JFrame;

import de.javamaps.items.Vertex;

import javax.swing.JComboBox;
import java.awt.Canvas;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Gui {

	private JFrame frame;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui window = new Gui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
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
		frame.setBounds(100, 100, 1000, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		cb_start = new JComboBox();
		cb_start.setBounds(10, 11, 180, 20);
		frame.getContentPane().add(cb_start);
		
		cb_target = new JComboBox();
		cb_target.setBounds(10, 42, 180, 20);
		frame.getContentPane().add(cb_target);
		
		JButton btn_start = new JButton("Find Route");
		btn_start.setBounds(10, 90, 180, 33);
		frame.getContentPane().add(btn_start);
		
		map = new Map_Area();
		map.setBounds(200, 11, 774, 739);
		frame.getContentPane().add(map);
		
		
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
}
