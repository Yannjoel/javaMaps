package de.javamaps;

import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import de.javamaps.items.ComboItem;
import de.javamaps.items.Vertex;

import javax.swing.JComboBox;
import java.awt.Color;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Gui {

	private JFrame frame;


	/**
	 * Create the application.
	 */
	public Gui() {
		
		initialize();
		
	}
	JComboBox<ComboItem> chooseBox_start;
	JComboBox<ComboItem> chooseBox_target;
	private MapArea streetmap;
	Graphics g_map;
	JTextArea textArea_route;
	private JScrollPane sp_route;
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame();
		frame.setBounds(100, 100, 1250, 898);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.addComponentListener(new FrameEvent());
		
		chooseBox_start = new JComboBox<ComboItem>();
		chooseBox_start.setBounds(10, 11, 240, 20);
		frame.getContentPane().add(chooseBox_start);
		
		chooseBox_target = new JComboBox<ComboItem>();
		chooseBox_target.setBounds(10, 42, 240, 20);
		frame.getContentPane().add(chooseBox_target);
					    
		JButton startButton = new JButton("Find Route");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 streetmap.removeRoute();
				 long startVertexID = ((ComboItem) chooseBox_start.getSelectedItem()).getId();
				 long endVertexID = ((ComboItem) chooseBox_target.getSelectedItem()).getId();
				 String dijkstraResult = Main.calcRouteWithDijkstra(startVertexID, endVertexID);
				 textArea_route.setMargin(new Insets(3, 3, 3, 3));
				 textArea_route.setEditable(false);
				 textArea_route.setText(dijkstraResult);
			}
		});
		
		startButton.setBounds(10, 90, 240, 33);
		frame.getContentPane().add(startButton);
		
		streetmap = new MapArea();
		streetmap.setBounds(260, 11, 974, 839);
		frame.getContentPane().add(streetmap);
		
		textArea_route = new JTextArea();
		sp_route = new JScrollPane(textArea_route);
		sp_route.setBounds(10, 134, 240, 716);
		frame.getContentPane().add(sp_route);
		

		
		
		
	}
	 private class FrameEvent implements ComponentListener{
	        
	        public void componentResized(ComponentEvent arg0) {
	        	
	        	streetmap.setSize(frame.getWidth() - 220, frame.getHeight()- 61);
	        	streetmap.resized();

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
		streetmap.addLine(x1.getLongitude(), x1.getLatitude(),x2.getLongitude(), x2.getLatitude());
	}
	public void addLine(Vertex x1, Vertex x2, Color color){
		streetmap.addLine(x1.getLongitude(), x1.getLatitude(),x2.getLongitude(), x2.getLatitude(), color);
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
		streetmap.drawLines();
	}
	public void setVisible(boolean b) {
		frame.setVisible(b);
		
	}
	
	public void addLocations(TreeMap<String, List<Long>> positions){
		for (Map.Entry<String, List<Long>> entry : positions.entrySet()) {
			for(long id : entry.getValue()){
				chooseBox_start.addItem(new ComboItem(id,entry.getKey()));
				chooseBox_target.addItem(new ComboItem(id,entry.getKey()));
			}		
		}
	}
}
