package de.javamaps.route;
import de.javamaps.items.Neighbor;
import de.javamaps.items.Vertex;

import java.util.*;
import java.util.Map.Entry;

/**
 * @author Tim
 * @version 1.01
 * @since 1.8.0_91
 */
public class DistanceCalc {

	/**
	 * Calculates the distances of all Vertexes to their neighbors with an accuracy of 1m
	 * @param graph Map that contains all Vertexes with their Neighbours but without the right distances between the neighbors
	 * @return graph Map that contains all Vertexes with their Neighbours including the right distances between the neighbors
	 */
	public static Map<Long, Vertex> calculateAllDistancesOfGraph(Map<Long, Vertex> graph) {
		for (Entry<Long, Vertex> entry : graph.entrySet()) {
			Vertex currentVertex = entry.getValue(); // Vertex
			for (Neighbor neighbor : currentVertex.getNeighbors()) {
				Vertex nextVertex = graph.get(neighbor.getName());
				long distance = distanceBetweenTwoVertexes(currentVertex,nextVertex);
				neighbor.setDistance((int) distance);
			}
		}
		return graph;

	}
	/**
	 * Calculates the distance between two Vertexes
	 */
	public static Long distanceBetweenTwoVertexes(Vertex vertex1, Vertex vertex2){
		double longitude1 = vertex1.getLongitude();
		double longitude2 = vertex2.getLongitude();
		double latitude1 = vertex1.getLatitude();
		double latitude2 = vertex2.getLatitude();
		double approximateLatitude = Math.toRadians((latitude1 + latitude2) / 2);
		double differenceXaxis = 111.3 * Math.cos(approximateLatitude) * (longitude1 - longitude2); // *111.3 f�r Erdkr�mmung
		double differenceYaxis = 111.3 * (latitude1 - latitude2); 
		double distance = (Math.sqrt(Math.pow(differenceXaxis, 2) + Math.pow(differenceYaxis, 2))*1000); // vgl. Satz des Pythagoras
		return (long) distance;
	}
}
