package de.javamaps.route;

import java.math.BigDecimal;
import java.util.*;
import java.util.Map.Entry;

import de.javamaps.GlobalApplicationStorage;
import de.javamaps.items.Neighbor;
import de.javamaps.items.Vertex;

/**
 * @author Yannick
 * @version 1.03
 * @since 1.8.0_91
 */
public class Dijkstra {

	private static final List<Vertex> reachableVertexes = new ArrayList<>();
	private static final String NOT_SET = "null";

	/**
	 * Calculates the shortest way form startVertex to endVertex
	 * 
	 * @param startVertexID
	 *            id of the start vertex
	 * @param endVertexID
	 *            id of the end vertex
	 * @return StringBuffer which contains the console output
	 */

	public static StringBuilder calculate(String startVertexID, String endVertexID) {
		Map<String, Vertex> mapData = GlobalApplicationStorage.getGlobalStorage().getMapData();
		StringBuilder output = new StringBuilder();
		init(mapData);
		Vertex startVertex = mapData.get(startVertexID);
		Vertex endVertex = mapData.get(endVertexID);
		startVertex.setAsStart();
		reachableVertexes.add(startVertex);

		while (endVertex.isUnVisited()) {
			Vertex currentVertex = getVertexWithLowestTotalDistance();
			if (currentVertex.hasNeighbors()) {
				for (Neighbor nextNeighbor : currentVertex.getNeighbors()) {
					Vertex nextVertex = mapData.get(nextNeighbor.getID());
					if (nextVertex.isUnVisited()) {
						BigDecimal newDistance = currentVertex.getTotalDistance().add(nextNeighbor.getDistance());
						if (nextVertex.getTotalDistance() == null || newDistance.compareTo(nextVertex.getTotalDistance()) < 0) {
							nextVertex.setTotalDistance(currentVertex.getTotalDistance().add(nextNeighbor.getDistance()));
							nextVertex.setPrevious(currentVertex.getId());
						}
						reachableVertexes.add(nextVertex);
					}
				}
			}
			currentVertex.setVisited(true);
			reachableVertexes.remove(currentVertex);

		}
		//prepare text output containing route details
		output.append("Total Distance: ").append(endVertex.getTotalDistance().doubleValue() / 1000).append(" km \n \n").append("Way was:\n")
				.append(getFullWayAsOutputStringBuffer(endVertexID));

		return output;
	}

	/**
	 * Reinitialize all elements that need to be reinitialized for Dijkstra algorithm
	 */
	private static void init(Map<String, Vertex> graph) {
		for (Entry<String, Vertex> entry : graph.entrySet()) {
			Vertex vertex = entry.getValue();
			vertex.setTotalDistance(null);
			vertex.setVisited(false);
		}
		reachableVertexes.clear();
	}

	/**
	 * @param endVertexID
	 *            ID of the end Vertex

	 * @return Text that lists the route vertex of the graphMap
	 */
	private static StringBuffer getFullWayAsOutputStringBuffer(String endVertexID) {
		StringBuffer output = new StringBuffer();
		Stack<Vertex> fullWayAsStack = getFullWayAsStack(endVertexID);
		while (!fullWayAsStack.isEmpty()) {
			String currentVertexName = fullWayAsStack.pop().getName();
			if (currentVertexName != null) {
				output.append(currentVertexName).append("\n");
			}
		}
		return output;
	}

	/**
	 * Finds the vertex that should be used for the next step in Dijkstra and
	 * removes it from the reachableVertex List
	 *
	 * @return id of the nearest open Vertex
	 */
	private static Vertex getVertexWithLowestTotalDistance() {
		Vertex currentNearestVertex = null;
		int distanceToCurrentNearestVertex = Integer.MAX_VALUE;
		for (Vertex vertex : reachableVertexes) {
			if (vertex.getTotalDistance() != null && vertex.getTotalDistance().intValue() < distanceToCurrentNearestVertex) {
				distanceToCurrentNearestVertex = vertex.getTotalDistance().intValue();
				currentNearestVertex = vertex;
			}
		}
		reachableVertexes.remove(currentNearestVertex);
		return currentNearestVertex;
	}

	/**
	 * @param endVertexID
	 *            ID of the end Vertex
	 * @return Stack that contains all Vertexes from the startVertex to the
	 *         EndVertex in the right order (startVertex as TopElement)
	 */
	public static Stack<Vertex> getFullWayAsStack(String endVertexID) {
		Map<String, Vertex> mapData = GlobalApplicationStorage.getGlobalStorage().getMapData();
		Stack<Vertex> output = new Stack<>();
		Vertex currentVertex;
		String currentVertexId = endVertexID;
		do {
			currentVertex = mapData.get(currentVertexId);
			output.push(currentVertex);
			currentVertexId = currentVertex.getPrevious();
		} while (currentVertexId != null);
		return output;
	}
}
