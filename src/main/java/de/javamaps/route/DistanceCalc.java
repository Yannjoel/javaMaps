package de.javamaps.route;

import de.javamaps.items.Neighbor;
import de.javamaps.items.Vertex;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Tim
 * @version 1.01
 * @since 1.8.0_91
 */
public class DistanceCalc {

    /**
     * Calculates the distances of all Vertexes to their neighbors with an accuracy of 1m
     *
     * @param graph Map that contains all Vertexes with their Neighbours but without the right distances between the neighbors
     * @return graph Map that contains all Vertexes with their Neighbours including the right distances between the neighbors
     */
    public static Map<String, Vertex> calculateAllDistancesOfGraph(Map<String, Vertex> graph) {
        for (Entry<String, Vertex> entry : graph.entrySet()) {
            Vertex currentVertex = entry.getValue(); // Vertex
            for (Neighbor neighbor : currentVertex.getNeighbors()) {
                Vertex nextVertex = graph.get(neighbor.getID());
                BigDecimal distance = distanceBetweenTwoVertexes(currentVertex, nextVertex);
                neighbor.setDistance(distance);
            }
        }
        return graph;

    }

    /**
     * Calculates the distance between two Vertexes
     */
    public static BigDecimal distanceBetweenTwoVertexes(Vertex vertex1, Vertex vertex2) {
        BigDecimal longitude1 = vertex1.getLongitude();
        BigDecimal longitude2 = vertex2.getLongitude();
        BigDecimal latitude1 = vertex1.getLatitude();
        BigDecimal latitude2 = vertex2.getLatitude();
        double approximateLatitude = Math.toRadians(latitude1.add(latitude2).doubleValue() / 2);
        double differenceXAxis = 111.3 * Math.cos(approximateLatitude) * (longitude1.subtract(longitude2).doubleValue());
        double differenceYAxis = 111.3 * (latitude1.subtract(latitude2).doubleValue());
        double distance = (Math.sqrt(Math.pow(differenceXAxis, 2) + Math.pow(differenceYAxis, 2)) * 1000);
        return new BigDecimal(distance);
    }
}
