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
	/**
	 * Calculates the distanceses of all Vertexes to there neighbors with an accurity of 1m
	 * @param graph TreeMap that contains all Vertexes with there Neighbours but without the right distances between the Neighbors
	 * @return graph TreeMap that contains all Vertexes with there Neighbours including the right distances between the Neighbors
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
	/**
	 * Calculates the distance between two Vertexes
	 */
	public static Long distanceBetweenTwoVertexs(Vertex vertex1, Vertex vertex2){
		double longitude1 = vertex1.getLongitude();
		double longitude2 = vertex2.getLongitude();
		double latitude1 = vertex1.getLatitude();
		double latitude2 = vertex2.getLatitude();
		double approximatelatitude = Math.toRadians((latitude1 + latitude2) / 2);
		double differenceXaxis = 111.3 * Math.cos(approximatelatitude) * (longitude1 - longitude2); // *111.3 für Erdkrümmung
		double differenceYaxis = 111.3 * (latitude1 - latitude2); 
		double distance = (Math.sqrt(Math.pow(differenceXaxis, 2) + Math.pow(differenceYaxis, 2))*1000); // vgl. Satz des Pythagoras
		return (long) distance;
	}
}
