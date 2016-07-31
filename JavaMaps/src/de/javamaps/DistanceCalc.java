package de.javamaps;
import de.javamaps.items.*;

import java.util.*;
import java.util.Map.Entry;

/**
 * @author Tim
 * @version 1.01
 * @since 1.8.0_91
 */
public class DistanceCalc {
	// Genauigkeit: 1m; Returnwert: kompletter Graph mit berechneten längen;
	/**
	 * @param graph
	 * @return graph with calculated neighbours for each neighbour
	 */
	public static TreeMap<Long, Vertex> calculatAllDintancesOfGraph(TreeMap<Long, Vertex> graph) {

	
		for (Entry<Long, Vertex> entry : graph.entrySet()) {
			Vertex currentVertex = entry.getValue(); // Vertex
			for (Neighbor neighbor : currentVertex.getNeighbors()) {
				Vertex nextVertex = graph.get(neighbor.getName());
				long distance = distanceBetweenTwoVertexs(currentVertex,nextVertex);
				neighbor.setDistance((int) distance);
			}
		}
		return graph; //mit berechneter Länge

	}
	public static Long distanceBetweenTwoVertexs(Vertex vertex1, Vertex vertex2){
		double longitude1 = vertex1.getLongitude();
		double longitude2 = vertex2.getLongitude();
		double latitude1 = vertex1.getLatitude();
		double latitude2 = vertex2.getLatitude();
		double lat = Math.toRadians((latitude1 + latitude2) / 2);
		double differenceXaxis = 111.3 * Math.cos(lat) * (longitude1 - longitude2); // *111.3 für Erdkrümmung
		double differenceYaxis = 111.3 * (latitude1 - latitude2); 
		double distance = (Math.sqrt(Math.pow(differenceXaxis, 2) + Math.pow(differenceYaxis, 2))*1000); // vgl. Satz des Pythagoras
		return (long) distance;
	}
}
