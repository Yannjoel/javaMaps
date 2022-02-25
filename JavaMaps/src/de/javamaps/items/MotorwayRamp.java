package de.javamaps.items;

import java.util.*;
import java.util.Map.Entry;

import de.javamaps.items.Vertex;

public class MotorwayRamp {
	/**
	 * Creates a map that contains all ramps with their names as
	 * keys
	 */
	public static Map<String, List<Long>> getMotorwayRamps(Map<Long, Vertex> graph) {
		Map<String, List<Long>> allMotorwayRamps = new HashMap<>();

		for (Entry<Long, Vertex> entrySetOfGraph : graph.entrySet()) {
			String currentVertexName = null;
			Long currentVertexID = null;

			// Only vertexes with a name are used as potential start vertexes
			if (entrySetOfGraph.getValue().getName().equals("null")) {
				continue;
			}
			currentVertexName = entrySetOfGraph.getValue().getName();
			currentVertexID = entrySetOfGraph.getKey();

			if (allMotorwayRamps.containsKey(currentVertexName)) {
				allMotorwayRamps.get(currentVertexName).add(currentVertexID);
			}
			else {
				List<Long> temp = new LinkedList<Long>();
				temp.add(currentVertexID);
				allMotorwayRamps.put(currentVertexName, temp);
			}

		}
		return allMotorwayRamps;
	}
}
