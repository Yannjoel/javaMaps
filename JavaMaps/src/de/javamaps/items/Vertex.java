package de.javamaps.items;

import java.util.HashMap;
import java.util.Map;



public class Vertex {
	private final String name;
	private Vertex previous = null;	
	private int dist = Integer.MAX_VALUE; /// ersatz für unendlich //bisheriger Weg zu diesem Knoten
	private Map<Vertex, Integer> adjacentVertex = new HashMap<>();

	public Vertex(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Vertex getPrevious() {
		return previous;
	}

	public void setPrevious(Vertex previous) {
		this.previous = previous;
	}

	public int getDist() {
		return dist;
	}

	public void setDist(int dist) {
		this.dist = dist;
	}

	public Map<Vertex, Integer> getAdjacentVertex() {
		return adjacentVertex;
	}

	public void setAdjacentVertex(Map<Vertex, Integer> adjacentVertex) {
		this.adjacentVertex = adjacentVertex;
	}
}
