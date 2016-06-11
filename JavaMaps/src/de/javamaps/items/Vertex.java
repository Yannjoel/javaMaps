package de.javamaps.items;

import java.util.HashMap;
import java.util.Map;

public class Vertex {
	private final int id;
	private final float Lat;
	private final float Lon;
	private Vertex previous = null;
	private int way_dist = Integer.MAX_VALUE; /// ersatz für unendlich
												/// //bisheriger Weg zu diesem
												/// Knoten
	private Map<Vertex, Long> adjacentVertex = new HashMap<>();

	public Vertex(int idIn, float Latitude, float Longitude, Vertex previousVertex, int cumulativ_distance, Map<Vertex, Long> neighbourVertex) {
		this.id = idIn;
		this.Lon = Longitude;
		this.Lat = Latitude;
		this.previous = previousVertex;
		this.setWay_dist(cumulativ_distance);
		this.adjacentVertex = neighbourVertex;
	}

	public int getId() {
		return id;
	}

	public Vertex getPrevious() {
		return previous;
	}

	public void setPrevious(Vertex previous) {
		this.previous = previous;
	}

	public Map<Vertex, Long> getAdjacentVertex() {
		return adjacentVertex;
	}

	public void setAdjacentVertex(Map<Vertex, Long> adjacentVertex) {
		this.adjacentVertex = adjacentVertex;
	}

	public float getLon() {
		return Lon;
	}

	public float getLat() {
		return Lat;
	}

	public int getWay_dist() {
		return way_dist;
	}

	public void setWay_dist(int way_dist) {
		this.way_dist = way_dist;
	}
}
