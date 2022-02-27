package de.javamaps.items;

import java.util.*;
import java.util.Map.Entry;

public class MotorwayRamp {
	/**
	 * Creates a map that contains all ramps with their names as
	 * keys
	 */
	public static Map<String, List<String>> getMotorwayRamps(Map<String, Vertex> graph) {
		Map<String, List<String>> allMotorwayRamps = new HashMap<>();

		for (Entry<String, Vertex> entrySetOfGraph : graph.entrySet()) {
			String currentVertexName = null;
			String currentVertexID = null;

			// Only vertexes with a name are used as potential start vertexes
			if (entrySetOfGraph.getValue().getName() == null) {
				continue;
			}
			currentVertexName = entrySetOfGraph.getValue().getName();
			currentVertexID = entrySetOfGraph.getKey();

			if (allMotorwayRamps.containsKey(currentVertexName)) {
				allMotorwayRamps.get(currentVertexName).add(currentVertexID);
			}
			else {
				List<String> moterwayIdsWithSameName = new ArrayList<>();
				moterwayIdsWithSameName.add(currentVertexID);
				allMotorwayRamps.put(currentVertexName, moterwayIdsWithSameName);
			}

		}
		return allMotorwayRamps;
	}
}
