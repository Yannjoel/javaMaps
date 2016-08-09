package de.javamaps;

import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import de.javamaps.items.Vertex;

public class MotorwayRamp {
	/**
	 * Creates a Treemap that contains all Motorway ramps with theire names as
	 * keys
	 */
	public static TreeMap<String, List<Long>> getMotorwayRamps(TreeMap<Long, Vertex> graph) {
		TreeMap<String, List<Long>> allMotorwayRamps = new TreeMap<String, List<Long>>();
		String currentVertexName = null;
		Long currentVertexID = null;

		// Über den komplette Graphen iterieren
		for (Entry<Long, Vertex> entrySetOfGraph : graph.entrySet()) {
			// Hat die Vertex einen Namen, so wird der Knoten als möglicher
			// Startpunkt gewertet.
			if (!entrySetOfGraph.getValue().getName().equals("null")) {
				currentVertexName = entrySetOfGraph.getValue().getName();
				currentVertexID = entrySetOfGraph.getKey();

				// Ist schon eine Auffahrt mit diesem Namen vorhanden, so soll
				// die neue ID angehängt werden.
				if (allMotorwayRamps.containsKey(currentVertexName)) {
					allMotorwayRamps.get(currentVertexName).add(currentVertexID);
				}
				// Ist noch keine Auffahrt mit diesem Namen vorhanden, so
				// wird ein neuer Eintrag erstellt
				else {
					List<Long> temp = new LinkedList<Long>();
					temp.add(currentVertexID);
					allMotorwayRamps.put(currentVertexName, temp);
				}
				// Für die nächste Iteration wieder die names und IDs
				// zurücksetzen
				currentVertexName = null;
				currentVertexID = null;
			}
		}
		return allMotorwayRamps;
	}
}
