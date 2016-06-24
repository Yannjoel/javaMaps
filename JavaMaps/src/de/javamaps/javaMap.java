package de.javamaps;

import java.util.ArrayList;
import java.util.HashMap;
import de.javamaps.items.Neighbor;
import de.javamaps.items.Vertex;

public class javaMap {
	public static HashMap<String, Vertex> getTestgraph() {

		HashMap<String, Vertex> testData = new HashMap<String, Vertex>();
		// Testgraph
		testData.put("a", new Vertex("a", 0, 0, 0));
		testData.put("b", new Vertex("b", 0, 0, 0));
		testData.put("c", new Vertex("c", 0, 0, 0));
		testData.put("d", new Vertex("d", 0, 0, 0));
		testData.put("e", new Vertex("e", 0, 0, 0));
		testData.put("f", new Vertex("f", 0, 0, 0));
		testData.put("g", new Vertex("g", 0, 0, 0));
		testData.put("h", new Vertex("h", 0, 0, 0));
		testData.put("i", new Vertex("i", 0, 0, 0));
		testData.put("j", new Vertex("j", 0, 0, 0));
		testData.put("k", new Vertex("k", 0, 0, 0));

		testData.get("a").addNeighbor(new Neighbor("b", 1));
		testData.get("a").addNeighbor(new Neighbor("c", 5));
		testData.get("b").addNeighbor(new Neighbor("c", 1));

		return testData;

	}

	public static void main(String[] args) {
		Dijkstra.getshortestWay("a","c", getTestgraph());
	}

}
