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
	// Genauigkeit: 1m; Returnwert: int;
	/**
	 * @param graph
	 * @return graph with calculated neighbours for each neighbour
	 */
	public static TreeMap<Long, Vertex> distanceCalculation(TreeMap<Long, Vertex> graph) {

		double distance = 0;
		double dx = 0;
		double dy = 0;
		double lat = 0;
		double lat1 = 0;
		double lat2 = 0;
		double lon1 = 0;
		double lon2 = 0;
		
		for (Entry<Long, Vertex> e : graph.entrySet()) {
			Vertex vertex = e.getValue(); // Vertex
			for (Neighbor neighbor : vertex.getNeighbors()) {
				Vertex nvertex = graph.get(neighbor.getName());
				lon1 = vertex.getLon();
				lon2 = nvertex.getLon();
				lat1 = vertex.getLat();
				lat2 = nvertex.getLat();
				lat = Math.toRadians((lat1 + lat2) / 2);
				dx = 111.3 * Math.cos(lat) * (lon1 - lon2);
				dy = 111.3 * (lat1 - lat2); 
				distance = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
				distance = distance * 1000;
				neighbor.setDis((int) distance);
			}
		}
		return graph;

	}
}
