package de.javamaps;

import de.javamaps.items.*;

import java.util.*;
import java.util.Map.Entry;

public class DistanceCalc {
	// Genauigkeit: 1m; Returnwert: int;
	public static HashMap distanceCalculation(HashMap<Long, Vertex> graph) {

		double distance = 0;
		for (Entry<Long, Vertex> e : graph.entrySet()) {
			Vertex vertex = e.getValue(); // Vertex
			for (Neighbor neighbor : vertex.getNeighbors()) {
				Vertex nvertex = graph.get(neighbor.getName());
				distance = Math.sqrt(Math.pow(71.5 * (vertex.getLon() - nvertex.getLon()), 2)
						+ Math.pow(111.3 * (vertex.getLat() - nvertex.getLat()), 2)) * 1000;
				neighbor.setDis((int) distance);
			}
		}
		return graph;

	}
}
