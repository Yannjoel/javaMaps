package de.javamaps.parser;

import java.math.BigDecimal;
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
	 * Connect all motorway ramps with the same name inside a Graph
	 */
	public static void connectMotorwayRampsWithSameNames(Map<String, Vertex> graph) {

		for (Entry<String, List<String>> entrySetOfAllMotorwayRamps : MotorwayRamp.getMotorwayRamps(graph).entrySet()) {
			for (String currentID : entrySetOfAllMotorwayRamps.getValue()) {
				Vertex actualMotorwayRampAsVertex = graph.get(currentID);
				for (String newNeighborID : entrySetOfAllMotorwayRamps.getValue()) {
					if (!newNeighborID.equals(currentID)) {
						if (DistanceCalc.distanceBetweenTwoVertexes(graph.get(newNeighborID),
								graph.get(currentID)).intValue() < 600) {
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
	public static Map<String, List<String>> filterOutDuplicateNames(Map<String, List<String>> graph) {
		Map<String, List<String>> filteredVertexNames = new HashMap<>();

		for (Entry<String, List<String>> entrySetOfGraph : graph.entrySet()) {
			String currentVertexName = entrySetOfGraph.getKey();

			if (!filteredVertexNames.containsKey(currentVertexName)) {
				filteredVertexNames.put(entrySetOfGraph.getKey(), new ArrayList<String>());
				filteredVertexNames.get(entrySetOfGraph.getKey()).add(entrySetOfGraph.getValue().get(0));
			}

		}
		return filteredVertexNames;
	}
}
