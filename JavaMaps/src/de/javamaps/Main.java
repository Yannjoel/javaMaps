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

		TreeMap<String, List<Long>> allMotorwayRamps = MotorwayRamp.getMotorwayRamps(XmlReader.graphFromXmlFile);
		window.addLocations(filterOutDublicateNames(allMotorwayRamps));
	}

	/**
	 * Creates a TreeMap that contains all Names that are in a graph and the VertexIDs that are linked to this names and that is used as Namelist later 
	 */
	private static TreeMap<String, List<Long>> filterOutDublicateNames(TreeMap<String, List<Long>> graph) {
		TreeMap<String, List<Long>> filteredVertexNames = new TreeMap<String, List<Long>>();
		String currentVertexName = null;

		// Über den komplette Graphen iterieren
		for (Entry<String, List<Long>> entrySetOfGraph : graph.entrySet()) {
			currentVertexName = entrySetOfGraph.getKey();
			// Ist schon eine Auffahrt mit diesem Namen vorhanden, so soll
			// die neue ID angehängt werden.
			if (!filteredVertexNames.containsKey(currentVertexName)) {
				filteredVertexNames.put(entrySetOfGraph.getKey(), new ArrayList<Long>());
				filteredVertexNames.get(entrySetOfGraph.getKey()).add(entrySetOfGraph.getValue().get(0));
			}
			// Für die nächste Iteration wieder die names und IDs zurücksetzen
			currentVertexName = null;
		}
		return filteredVertexNames;
	}

	public static String calcRouteWithDijkstra(long startVertexID, long endVertexID) {
		StringBuffer output = Dijkstra.calculate(startVertexID, endVertexID, XmlReader.graphFromXmlFile);
		Stack<Vertex> routeList = (Dijkstra.getfullWayAsStack(XmlReader.graphFromXmlFile, endVertexID));
		window.drawRoute(routeList);
		return output.toString();
	}

}
