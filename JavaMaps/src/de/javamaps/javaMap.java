package de.javamaps;

import java.awt.EventQueue;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.stream.XMLStreamException;

import de.javamaps.items.Neighbor;
import de.javamaps.items.Vertex;

public class javaMap {
	public static HashMap<Long, Vertex> getTestgraph() {

		HashMap<Long, Vertex> testData = new HashMap<Long, Vertex>();
		// Testgraph
		testData.put((long) 1, new Vertex("Stadt1", 1, 0, 0));
		testData.put((long) 2, new Vertex("Stadt2", 2, 0, 0));
		testData.put((long) 3, new Vertex("Stadt3", 3, 0, 0));
		testData.put((long) 4, new Vertex("d", 0, 0, 0));
		testData.put((long) 5, new Vertex("e", 0, 0, 0));
		testData.put((long) 6, new Vertex("f", 0, 0, 0));
		testData.put((long) 7, new Vertex("g", 0, 0, 0));
		testData.put((long) 8, new Vertex("h", 0, 0, 0));
		testData.put((long) 9, new Vertex("i", 0, 0, 0));
		testData.put((long) 10, new Vertex("j", 0, 0, 0));
		testData.put((long) 11, new Vertex("k", 0, 0, 0));

		testData.get((long) 1).addNeighbor(new Neighbor(2, 1));
		testData.get((long) 1).addNeighbor(new Neighbor(3, 5));
		testData.get((long) 2).addNeighbor(new Neighbor(3, 1));

		return testData;

	}
	static Gui window;
	public static void main(String[] args) {
		System.out.println(Dijkstra.getshortestWay((long) 1,(long) 3,getTestgraph()));
		window = new Gui();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	
		try {
			XmlReader.XmlReader(window);
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
