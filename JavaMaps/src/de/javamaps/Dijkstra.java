package de.javamaps;

import java.util.HashMap;
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

	/**
	 * @param start
	 *            = id of the Startvertex
	 * @param end
	 *            = id of the Endvertex
	 * @param treeMap
	 *            - with all Vertex + Neighbors and calculated Neighbor-Distance
	 * @return StringBuffer which contains the console output
	 */
	public static StringBuffer getshortestWay(Long start, Long end, TreeMap<Long, Vertex> treeMap) {
		StringBuffer output = new StringBuffer();
		if (treeMap.containsKey(start)) {
			if (treeMap.containsKey(end)) {
				Vertex startV = treeMap.get(start);
				Vertex endV = treeMap.get(end);
				HashMap<Long, Vertex> reachableVertex = new HashMap<Long, Vertex>();
				reachableVertex.put(start, startV);
				startV.setAsStart();

				try {
					// solange der der end-Punkt noch nicht "besucht" wurde
					// Funktion für
					// Knoten mit der Kürzesten way_dist auswählen
					long i = 0; // für Number of Steps
					while (!endV.isVisited()) {
						i++;
						Vertex inuse = getNext(reachableVertex);
						// nähsten Nachbarn auswählen
						if (inuse.hasNeighbors()) {
							for (Neighbor nextN : inuse.getNeighbors()) {
								Vertex nextV = treeMap.get(nextN.getName());
								if (!nextV.isVisited()) {
									int newDis = inuse.getWay_dist() + nextN.getDis();
									if (newDis < nextV.getWay_dist()) {
										nextV.setWay_dist(inuse.getWay_dist() + nextN.getDis());
										nextV.setPrevious(inuse.getId());
									}
									reachableVertex.put(nextN.getName(), nextV);
								}
							}
						}
						inuse.setVisited(true);
						reachableVertex.remove(inuse);

					}
					System.out.println("Number of Steps: " + i);
					// Ausgabe nach Abschluss des Algorithmus
					output.append("Total Distance from ");
					output.append(startV.getName());
					output.append(" to ");
					output.append(endV.getName());
					output.append(" is: ");
					output.append((double) endV.getWay_dist() / 1000);
					output.append(" km \n \n");
					output.append("Way was:\n");
					try {
						output.append(getFullWay(endV, treeMap));
					} catch (Exception e) {
						output.append(e);
					}

				} catch (Exception e) { // wenn der Algorithmus nicht
										// determiniert
					output.append("cant reach ");
					output.append(end);
					output.append("\n");
				}
			} else {
				output.append("Cant find ");
				output.append(end);
				output.append(" in our map");
				output.append("\n");
			}
		} else {
			output.append("Cant find ");
			output.append(start);
			output.append(" in our map");
			output.append("\n");
		}
		return output;
	}

	/**
	 * @param vertex
	 * @param treeMap
	 * @return Stringbuffer that list the way from a vertex back to the start
	 *         vertex of the graphMap
	 */
	private static StringBuffer getFullWay(Vertex vertex, TreeMap<Long, Vertex> treeMap) {
		StringBuffer output = new StringBuffer();
		if (vertex.getPrevious() != null) {
			Vertex previous = treeMap.get(vertex.getPrevious());
			output.append(getFullWay(previous, treeMap));
		}
		if (vertex.getName().equals("null") == false) {
			output.append(vertex.getId());
			output.append(" (name = ");
			output.append(vertex.getName());
			output.append(")");
			output.append("\n");
		}
		return output;
	}

	/**
	 * @param reachableVertex
	 * @return id of the nearest open Vertex
	 */
	private static Vertex getNext(HashMap<Long, Vertex> reachableVertex) {
		Vertex out = null;
		int min = Integer.MAX_VALUE;
		for (Entry<Long, Vertex> e : reachableVertex.entrySet()) {
			/// Wenn noch offene Knoten bestehen kleinsten weg ermitteln
			if (!e.getValue().isVisited() && e.getValue().getWay_dist() < min) {
				min = e.getValue().getWay_dist();
				out = e.getValue();
			}
		}
		return out;
	}

	public static Stack<Vertex> getfullWayStack(TreeMap<Long, Vertex> map, Long end) {
		Stack<Vertex> output = new Stack<Vertex>();
		Vertex v;
		Long id = end;
		do {
			v = map.get(id);
			output.push(v);
			id = v.getPrevious();
		} while (id != null);
		return output;
	}
}
