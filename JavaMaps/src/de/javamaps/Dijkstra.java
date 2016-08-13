package de.javamaps;

import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Stack;
import java.util.TreeMap;
import de.javamaps.items.*;

/**
 * @author Yannick
 * @version 1.03
 * @since 1.8.0_91
 */
public class Dijkstra {

	private static List<Vertex> reachableVertexs = new LinkedList<Vertex>();
	private static final String NOTSET = "null";

	/**
	 * Calculats the shortest way form startVertex to endVertex
	 * 
	 * @param startVertexID
	 *            id of the Startvertex
	 * @param endVertexID
	 *            id of the Endvertex
	 * @param graph
	 *            Treemap that contains all Vertexes with there Neighbors and
	 *            calculated Neighbor-Distance
	 * @return StringBuffer which contains the console output
	 */

	public static StringBuffer calculate(Long startVertexID, Long endVertexID, TreeMap<Long, Vertex> graph) {

		StringBuffer output = new StringBuffer();
		try {
			init(graph);
			Vertex startVertex = graph.get(startVertexID);
			Vertex endVertex = graph.get(endVertexID);
			startVertex.setAsStart();
			reachableVertexs.add(startVertex);

			while (!endVertex.isVisited()) {
				Vertex inuse = getVertexWithLowestTotalDistance(reachableVertexs);
				if (inuse.hasNeighbors()) {
					for (Neighbor nextNeighbor : inuse.getNeighbors()) {
						Vertex nextVertex = graph.get(nextNeighbor.getName());
						if (!nextVertex.isVisited()) {
							int newDistance = inuse.getTotalDistance() + nextNeighbor.getDistance();
							if (newDistance < nextVertex.getTotalDistance()) {
								nextVertex.setTotalDistance(inuse.getTotalDistance() + nextNeighbor.getDistance());
								nextVertex.setPrevious(inuse.getId());
							}
							reachableVertexs.add(nextVertex);
						}
					}
				}
				inuse.setVisited(true);
				reachableVertexs.remove(inuse);

			}
			// Ausgabe nach Abschluss des Algorithmus
			output.append("Total Distance: " + (double) endVertex.getTotalDistance() / 1000 + " km \n \n" + "Way was:\n"
					+ getFullWayAsOutputStringBuffer(endVertexID, graph));

		} catch (Exception exeption) {
			output.append("Error 404- Way not found");
		}
		return output;
	}

	/**
	 * Reinitilitzes all Elemntes that need to be reinitialitzed for Dijkstra
	 */
	private static void init(TreeMap<Long, Vertex> graph) {
		for (Entry<Long, Vertex> entry : graph.entrySet()) {
			Vertex vertex = entry.getValue();
			vertex.setTotalDistance(Integer.MAX_VALUE);
			vertex.setVisited(false);
		}
		reachableVertexs.clear();
	}

	/**
	 * @param endVertexID
	 *            Id of the end Vertex
	 * @param graph
	 *            Treemap that contains all Vertexes with there Neighbors and
	 *            calculated Neighbor-Distance
	 * @return Text that lists the route vertex of the graphMap
	 */
	private static StringBuffer getFullWayAsOutputStringBuffer(Long endVertexID, TreeMap<Long, Vertex> graph) {
		StringBuffer output = new StringBuffer();
		Stack<Vertex> fullWayAsStack = getfullWayAsStack(graph, endVertexID);
		while (!fullWayAsStack.isEmpty()) {
			String currentVertexName = fullWayAsStack.pop().getName();
			if (!currentVertexName.equals(NOTSET)) {
				output.append(currentVertexName + "\n");
			}
		}
		return output;
	}

	/**
	 * Finds the vertex that should be used for the next step in Dijkstra and
	 * remooves it from the reachableVertex List
	 * 
	 * @param reachableVertexs
	 *            List that contains all Vertexes that are reachable in the
	 *            current step of the Dijkstra algorithm
	 * @return id of the nearest open Vertex
	 */
	private static Vertex getVertexWithLowestTotalDistance(List<Vertex> reachableVertexs) {
		Vertex out = null;
		int min = Integer.MAX_VALUE;
		for (Vertex vertex : reachableVertexs) {
			if (vertex.getTotalDistance() < min) {
				min = vertex.getTotalDistance();
				out = vertex;
			}
		}
		reachableVertexs.remove(out);
		return out;
	}

	/**
	 * @param endVertexID
	 *            Id of the end Vertex
	 * @param graph
	 *            Treemap that contains all Vertexes with there Neighbors and
	 *            calculated Neighbor-Distance
	 * @return Stack that contains all Vertexes from the startVertex to the
	 *         EndVertex in the right order (startVertex as TopElement)
	 */
	public static Stack<Vertex> getfullWayAsStack(TreeMap<Long, Vertex> graph, Long endVertexID) {
		Stack<Vertex> output = new Stack<Vertex>();
		Vertex currentVertex;
		Long currentVertexId = endVertexID;
		do {
			currentVertex = graph.get(currentVertexId);
			output.push(currentVertex);
			currentVertexId = currentVertex.getPrevious();
		} while (currentVertexId != null);
		return output;
	}
}
