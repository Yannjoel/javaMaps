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

public class Main {
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
			XmlReader.initialize(window);
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		GraphOptimizer.connectMotorwayRampsWithSameNames(XmlReader.graphFromXmlFile);
		DistanceCalc.calculatAllDintancesOfGraph(XmlReader.graphFromXmlFile);
		GraphOptimizer.uniteVertexs(XmlReader.graphFromXmlFile);

		window.addLocations(filter(MotorwayRamp.getMotorwayRamps(XmlReader.graphFromXmlFile)));
	}

	private static TreeMap<String, List<Long>> filter(TreeMap<String, List<Long>> graph) {
		TreeMap<String, List<Long>> out = new TreeMap<String, List<Long>>();
		String name = null;

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
		}

		return out;
	}

	public static String calcRoute(long startVertexID, long endVertexID) {
		StringBuffer output = Dijkstra.calculate(startVertexID, endVertexID, XmlReader.graphFromXmlFile);
		System.out.println(output);

		Stack<Vertex> routeList = (Dijkstra.getfullWayAsStack(XmlReader.graphFromXmlFile, endVertexID));
		window.drawRoute(routeList);
		return output.toString();
	}

}
