package de.javamaps;

import java.util.TreeMap;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map.Entry;
import de.javamaps.items.Neighbor;
import de.javamaps.items.Vertex;

public class GraphOptimizer {

	private static final String NOTSET = "null";
	private static List<Vertex> visitedVertexes = new LinkedList<Vertex>();

	/**
	 * Sums up all Vertexes that are in a row
	 */
	public static void uniteVertexs(TreeMap<Long, Vertex> graph) {
		for (Entry<Long, Vertex> entryOfGraph : graph.entrySet()) {
			if (!entryOfGraph.getValue().getName().equals(NOTSET)
					|| entryOfGraph.getValue().getNeighbors().size() > 1) {
				Vertex currentVertex = entryOfGraph.getValue();
				List<Neighbor> newNeighbors = new ArrayList<Neighbor>();
				for (Neighbor neighborVertex : currentVertex.getNeighbors()) {
					visitedVertexes.clear();
					Vertex nextVertex = graph.get(neighborVertex.getName());
					int distance = neighborVertex.getDistance();
					while (nextVertex.getName().equals(NOTSET) && nextVertex.getNeighbors().size() == 1
							&& !visitedVertexes.contains(nextVertex)) {
						visitedVertexes.add(nextVertex);
						neighborVertex = nextVertex.getNeighbors().get(0);
						distance += neighborVertex.getDistance();
						nextVertex = graph.get(neighborVertex.getName());
					}
					newNeighbors.add(new Neighbor(neighborVertex.getName(), distance));
				}
				currentVertex.setNeighbors(newNeighbors);
			}
		}
	}

	/**
	 * Connect all motorwayramps with the same name inside of a Graph
	 */
	public static void connectMotorwayRampsWithSameNames(TreeMap<Long, Vertex> graph) {

		for (Entry<String, List<Long>> entrySetOfAllMotorwayRamps : MotorwayRamp.getMotorwayRamps(graph).entrySet()) {
			for (Long currentID : entrySetOfAllMotorwayRamps.getValue()) {
				Vertex actualMotorwayRampAsVertex = graph.get(currentID);
				for (Long newNeighborID : entrySetOfAllMotorwayRamps.getValue()) {
					if (newNeighborID != currentID) {
						if (DistanceCalc.distanceBetweenTwoVertexs(graph.get(newNeighborID),
								graph.get(currentID)) < 600) {
							actualMotorwayRampAsVertex.addNeighbor(newNeighborID);
						}
					}
				}
			}
		}
	}
	

	/**
	 * Creates a TreeMap that contains all Names that are in a graph and the VertexIDs that are linked to this names and that is used as Namelist later 
	 */
	public static TreeMap<String, List<Long>> filterOutDublicateNames(TreeMap<String, List<Long>> graph) {
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
}
