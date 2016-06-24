package de.javamaps;

import java.util.ArrayList;
import java.util.HashMap;

import de.javamaps.items.Edge;
import de.javamaps.items.Neighbor;
import de.javamaps.items.Vertex;

public class javaMap {
	public static HashMap<String, Vertex> getTestData() {

		HashMap<String, Vertex> testData = new HashMap<String, Vertex>();
		// Testdaten
		testData.put("a", new Vertex("A", 0, 0, 0));
		testData.put("b", new Vertex("A", 0, 0, 0));
		testData.put("c", new Vertex("A", 0, 0, 0));
		testData.put("d", new Vertex("A", 0, 0, 0));
		testData.put("e", new Vertex("A", 0, 0, 0));
		testData.put("f", new Vertex("A", 0, 0, 0));
		testData.put("g", new Vertex("A", 0, 0, 0));
		testData.put("h", new Vertex("A", 0, 0, 0));
		testData.put("i", new Vertex("A", 0, 0, 0));
		testData.put("j", new Vertex("A", 0, 0, 0));
		testData.put("k", new Vertex("A", 0, 0, 0));

		testData.get("a").addNeighbor(new Neighbor("b", 1));
		testData.get("a").addNeighbor(new Neighbor("c", 5));
		testData.get("b").addNeighbor(new Neighbor("c", 1));

		return testData;

	}
	private static final ArrayList<Edge> edgeList = new ArrayList<Edge>();

	public static void main(String[] args) {
		Dijkstra.getshortestWay("A","B", getTestData());
	}

}
