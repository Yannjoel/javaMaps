package de.javamaps.parser;

import java.util.*;
import java.util.Map.Entry;

import de.javamaps.items.MotorwayRamp;
import de.javamaps.items.Neighbor;
import de.javamaps.items.Vertex;
import de.javamaps.route.DistanceCalc;

public class GraphOptimizer {

	private static final String NOT_SET = "null";
	private static List<Vertex> visitedVertexes = new ArrayList<>();

	/**
	 * Sums up all Vertexes that are in a row
	 */
	public static void uniteVertexes(Map<Long, Vertex> graph) {
		for (Entry<Long, Vertex> entryOfGraph : graph.entrySet()) {
			if (!entryOfGraph.getValue().getName().equals(NOT_SET)
					|| entryOfGraph.getValue().getNeighbors().size() > 1) {
				Vertex currentVertex = entryOfGraph.getValue();
				List<Neighbor> newNeighbors = new ArrayList<Neighbor>();
				for (Neighbor neighborVertex : currentVertex.getNeighbors()) {
					visitedVertexes.clear();
					Vertex nextVertex = graph.get(neighborVertex.getName());
					int distance = neighborVertex.getDistance();
					while (nextVertex.getName().equals(NOT_SET) && nextVertex.getNeighbors().size() == 1
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
	 * Connect all motorway ramps with the same name inside a Graph
	 */
	public static void connectMotorwayRampsWithSameNames(Map<Long, Vertex> graph) {

		for (Entry<String, List<Long>> entrySetOfAllMotorwayRamps : MotorwayRamp.getMotorwayRamps(graph).entrySet()) {
			for (Long currentID : entrySetOfAllMotorwayRamps.getValue()) {
				Vertex actualMotorwayRampAsVertex = graph.get(currentID);
				for (Long newNeighborID : entrySetOfAllMotorwayRamps.getValue()) {
					if (!newNeighborID.equals(currentID)) {
						if (DistanceCalc.distanceBetweenTwoVertexes(graph.get(newNeighborID),
								graph.get(currentID)) < 600) {
							actualMotorwayRampAsVertex.addNeighbor(newNeighborID);
						}
					}
				}
			}
		}
	}
	

	/**
	 * Creates a Map that contains all Names that are in a graph and the VertexIDs that are linked to this name and that is used as name list later
	 */
	public static Map<String, List<Long>> filterOutDuplicateNames(Map<String, List<Long>> graph) {
		Map<String, List<Long>> filteredVertexNames = new HashMap<>();

		for (Entry<String, List<Long>> entrySetOfGraph : graph.entrySet()) {
			String currentVertexName = entrySetOfGraph.getKey();

			if (!filteredVertexNames.containsKey(currentVertexName)) {
				filteredVertexNames.put(entrySetOfGraph.getKey(), new ArrayList<Long>());
				filteredVertexNames.get(entrySetOfGraph.getKey()).add(entrySetOfGraph.getValue().get(0));
			}

		}
		return filteredVertexNames;
	}
}
