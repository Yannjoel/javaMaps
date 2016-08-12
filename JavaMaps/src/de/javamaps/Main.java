package de.javamaps;


import java.awt.EventQueue;
import java.io.IOException;
import java.util.List;
import java.util.Stack;
import java.util.TreeMap;


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
		window.addLocations(GraphOptimizer.filterOutDublicateNames(allMotorwayRamps));
	}


	public static String calcRouteWithDijkstra(long startVertexID, long endVertexID) {
		StringBuffer output = Dijkstra.calculate(startVertexID, endVertexID, XmlReader.graphFromXmlFile);
		Stack<Vertex> routeList = (Dijkstra.getfullWayAsStack(XmlReader.graphFromXmlFile, endVertexID));
		window.drawRoute(routeList);
		return output.toString();
	}

}
