package de.javamaps;


import java.awt.EventQueue;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.TreeMap;
import java.util.Map.Entry;


import javax.xml.stream.XMLStreamException;

import de.javamaps.items.Vertex;

public class javaMap {
	static Gui window;

	public static void main(String[] args) {
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
		BuildBridge.createBridge(XmlReader.vertexMap);
		DistanceCalc.distanceCalculation(XmlReader.vertexMap);
		GraphOptimizer.uniteVertexs(XmlReader.vertexMap);

		/*
		 * for(Vertex v : XmlReader.vertexMap){
		 * System.out.println(v.getNeighbors()); }
		 */
		window.addLocations(filter(MotorwayRamp.getMotorwayRamps(XmlReader.vertexMap))); // Auswahlpunkte

		// StringBuffer output = Dijkstra.getshortestWay(start, end,
		// XmlReader.vertexMap);
		// System.out.println(output);
		// Stack<Vertex> routeStack =
		// (Dijkstra.getfullWayStack(XmlReader.vertexMap, end));
		// window.drawRoute(routeStack);
	}

	private static TreeMap<String, List<Long>> filter(TreeMap<String, List<Long>> graph) {
		TreeMap<String, List<Long>> out = new TreeMap<String, List<Long>>();
		String name = null;
		Long singleId = null;

		// Über den komplette Graphen iterieren
		for (Entry<String, List<Long>> e : graph.entrySet()) {
			name = e.getKey();
			// Ist schon eine Auffahrt mit diesem Namen vorhanden, so soll
			// die neue ID angehängt werden.
			if (!out.containsKey(name)) {
				out.put(e.getKey(), new ArrayList<Long>());
				out.get(e.getKey()).add(e.getValue().get(0));
			}
			// Für die nächste Iteration wieder die names und IDs zurücksetzen
			name = null;
			singleId = null;
		}

		return out;
	}

	public static String calcRoute(long start, long end) {
		StringBuffer output = Dijkstra.getshortestWay(start, end, XmlReader.vertexMap);
		System.out.println(output);

		Stack<Vertex> routeStack = (Dijkstra.getfullWayStack(XmlReader.vertexMap, end));
		window.drawRoute(routeStack);
		return output.toString();
	}

}
