package de.javamaps;

import java.util.Map.Entry;
import java.util.TreeMap;

import de.javamaps.items.*;

/**
 * @author Yannick
 * @version 1.03
 * @since 1.8.0_91
 */
public class Dijkstra {

	/**
	 * @param start = id of the Startvertex
	 * @param end = id of the Endvertex
	 * @param treeMap - with all Vertex + Neighbors and calculated Neighbor-Distance
	 * @return StringBuffer which contains the console output
	 */
	public static StringBuffer getshortestWay(Long start, Long end, TreeMap<Long, Vertex> treeMap) {
		StringBuffer output = new StringBuffer();
		try {
			/// start setzten - überprüft durch try catch auch automatisch on
			/// der Punkt start überhaupt existiert
			treeMap.get(start).setAsStart();

			try {
				// überprüfung ob end in der Map überhaupt existiert
				treeMap.get(end).getName();

				try {
					// solange der der end-Punkt noch nicht "besucht" wurde
					// Funktion für
					// Knoten mit der Kürzesten way_dist auswählen
					long i = 0;
					while (!treeMap.get(end).isVisited()) {
						i++;
						Vertex inuse = treeMap.get(getNext(treeMap));
						// nähsten Nachbarn auswählen
						if (inuse.hasNeighbors()) {
							Neighbor nextN = inuse.nearestNeighbor();
							Vertex nextV = treeMap.get(nextN.getName());
							if (!nextV.isVisited()) {
								int newDis = inuse.getWay_dist() + nextN.getDis();
								if (newDis < nextV.getWay_dist()) {
									nextV.setWay_dist(inuse.getWay_dist() + nextN.getDis());
									nextV.setPrevious(inuse.getId());
								}
							}
						} else {
							// sobald der Knoten abgearbeitet ist (d.h. er hat
							// keine
							// offenen Nachbarn mehr) wird er als besucht
							// gesetzt
							inuse.setVisited(true);
						}
					}
					System.out.println("Number of Steps: " + i);
					// Ausgabe nach Abschluss des Algorithmus
					output.append("Total Distance from ");
					output.append(treeMap.get(start).getName());
					output.append(" to ");
					output.append(treeMap.get(end).getName());
					output.append(" is: ");
					output.append((double) treeMap.get(end).getWay_dist() / 1000);
					output.append(" km \n \n");
					output.append("Way was:\n");
					try {
						output.append(getFullWay(treeMap.get(end), treeMap));
					} catch (Exception e) {
						output.append(e);
					}

				} catch (Exception e) { // wenn der Algorithmus nicht
										// determiniert
					output.append("cant reach ");
					output.append(end);
					output.append("\n");
				}
			} catch (Exception e) {
				output.append("Cant find ");
				output.append(end);
				output.append(" in our map");
				output.append("\n");
			}
		} catch (Exception e) {
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
	 * @return Stringbuffer that list the way from a vertex back to the start vertex of the graphMap
	 */
	private static StringBuffer getFullWay(Vertex vertex, TreeMap<Long, Vertex> treeMap) {
		StringBuffer output = new StringBuffer();
		if (vertex.getPrevious() != null) {
			Vertex previous = treeMap.get(vertex.getPrevious());
			output.append(getFullWay(previous, treeMap));
		}
		if(vertex.getName().equals("null") == false){
			output.append(vertex.getId());
			output.append(" (name = ");
			output.append(vertex.getName());
			output.append(")");
			output.append("\n");
		}
		return output;
	}

	/**
	 * @param treeMap
	 * @return id of the nearest open Vertex
	 */
	private static Long getNext(TreeMap<Long, Vertex> treeMap) {
		Long out = null;
		int min = Integer.MAX_VALUE;
		for (Entry<Long, Vertex> e : treeMap.entrySet()) {
			/// Wenn noch offene Knoten bestehen kleinsten weg ermitteln
			if (!e.getValue().isVisited() && e.getValue().getWay_dist() < min) {
				min = e.getValue().getWay_dist();
				out = e.getKey();
			}
		}
		return out;
	}
}
