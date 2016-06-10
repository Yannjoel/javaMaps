package de.javamaps.items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Graph {
	public Graph(ArrayList<Edge> edges) {
		HashMap<String, Vertex> graph = new HashMap<String, Vertex>(edges.size());

		for (Edge e : edges) {
			if (!graph.containsKey(e.getVertex1()))
				graph.put(e.getVertex1(), new Vertex(e.getVertex1()));
			if (!graph.containsKey(e.getVertex2()))
				graph.put(e.getVertex2(), new Vertex(e.getVertex2()));
		}
		Map<Vertex,Long> tempMap = new HashMap<Vertex,Long>();
		for (Edge e : edges) {
			tempMap.put(graph.get(e.getVertex2()), e.getDistance());
			graph.get(e.getVertex1()).setAdjacentVertex(tempMap);
		}
	}
}
