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

	private static List<Vertex> reachableVertex = new LinkedList<Vertex>();
	private static final String NOTSET =  "null";
	/**
	 * @param startVertexID
	 *            = id of the Startvertex
	 * @param endVertexID
	 *            = id of the Endvertex
	 * @param graph
	 *            - with all Vertex + Neighbors and calculated Neighbor-Distance
	 * @return StringBuffer which contains the console output
	 */

	public static StringBuffer calculate(Long startVertexID, Long endVertexID, TreeMap<Long, Vertex> graph) {
		init(graph);
		StringBuffer output = new StringBuffer();
		Vertex startVertex = graph.get(startVertexID);
		Vertex endVertex = graph.get(endVertexID);

		reachableVertex.add(startVertex);
		startVertex.setAsStart();

		try {
			while (!endVertex.isVisited()) {
				Vertex inuse = getVertexWithLowestTotalDistance(reachableVertex);
				System.out.println(inuse.getId());
				// nähsten Nachbarn auswählen
				if (inuse.hasNeighbors()) {
					for (Neighbor nextNeighbor : inuse.getNeighbors()) {
						Vertex nextVertex = graph.get(nextNeighbor.getName());
						if (!nextVertex.isVisited()) {
							int newDistance = inuse.getTotalDistance() + nextNeighbor.getDistance();
							if (newDistance < nextVertex.getTotalDistance()) {
								nextVertex.setTotalDistance(inuse.getTotalDistance() + nextNeighbor.getDistance());
								nextVertex.setPrevious(inuse.getId());
							}
							reachableVertex.add(nextVertex);
						}
					}
				}
				inuse.setVisited(true);
				reachableVertex.remove(inuse);

			}
			// Ausgabe nach Abschluss des Algorithmus
			output.append("Total Distance from " + startVertex.getName() + " to " + endVertex.getName() + " is: "
					+ (double) endVertex.getTotalDistance() / 1000 + " km \n \n" + "Way was:\n"
					+ getFullWayOutputString(endVertexID, graph));

		} catch (Exception exeption) {
			output.append("cant reach " + endVertex.getName() + "\n");
		}
		return output;
	}

	private static void init(TreeMap<Long, Vertex> graph) {
		for (Entry<Long, Vertex> entry : graph.entrySet()) {
			entry.getValue().setTotalDistance(Integer.MAX_VALUE);
			entry.getValue().setVisited(false);
		}

	}

	/**
	 * @param vertex
	 * @param graph
	 * @return Text that lists the rout vertex of the graphMap
	 */
	private static StringBuffer getFullWayOutputString(Long endVertexID, TreeMap<Long, Vertex> graph) {
		StringBuffer output = new StringBuffer();
		Stack<Vertex> fullWayAsStack = getfullWayAsStack(graph, endVertexID);
		while(!fullWayAsStack.isEmpty()) {
			String currentVertexName = fullWayAsStack.pop().getName();
			if (!currentVertexName.equals(NOTSET)){
				output.append(currentVertexName + "\n");
			}
		}
		return output;
	}

	/**
	 * @param reachableVertex
	 * @return id of the nearest open Vertex
	 */
	private static Vertex getVertexWithLowestTotalDistance(List<Vertex> reachableVertex) {
		Vertex out = null;
		int min = Integer.MAX_VALUE;
		for (Vertex vertex : reachableVertex) {
			if (vertex.getTotalDistance() < min) {
				min = vertex.getTotalDistance();
				out = vertex;
			}
		}
		reachableVertex.remove(out);
		return out;
	}

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
