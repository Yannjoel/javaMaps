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
						if (DistanceCalc.distanceBetweenTwoVertexs(graph.get(newNeighborID), graph.get(currentID)) < 600) {
							actualMotorwayRampAsVertex.addNeighbor(newNeighborID);
						}
					}
				}
			}
		}
	}
}
