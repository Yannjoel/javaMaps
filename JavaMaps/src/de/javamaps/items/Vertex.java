package de.javamaps.items;

import java.util.ArrayList;
import java.util.List;

public class Vertex {
	
	class Neighbor {
		final private String name;
		final private int dis;

		Neighbor(String nameIn, int disIn) {
			this.name = nameIn;
			this.dis = disIn;
		}
		
		public String getName(){
			return this.name;
		}
		public int getDis(){
			return this.dis;
		}
	}

	private final int id;
	private final int lat;
	private final int lon;
	private final String name;
	private Vertex previous = null;
	private int way_dist = Integer.MAX_VALUE; /// ersatz für unendlich
												/// //bisheriger Weg zu diesem
												/// Knoten
	private boolean visited;
	private List<Neighbor> neighbors = new ArrayList<Neighbor>();

	public void setAsStart() {
		this.way_dist = 0;
	}

	public Vertex(String NameIn, int idIn, int lonIn, int latIn) {
		this.name = NameIn;
		this.lat = latIn;
		this.lon = lonIn;
		this.id = idIn;
		this.visited = false;
	}

	public Vertex getPrevious() {
		return previous;
	}

	public int getLat() {
		return lat;
	}

	public int getLon() {
		return lon;
	}

	public void setPrevious(Vertex previous) {
		this.previous = previous;
	}

	public int getWay_dist() {
		return way_dist;
	}

	public void setWay_dist(int way_dist) {
		this.way_dist = way_dist;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<Neighbor> getNeighbors() {
		return neighbors;
	}

	public void setNeighbors(List<Neighbor> neighbors) {
		this.neighbors = neighbors;
	}
	
	public void addNeighbor(Neighbor newNeighbor) {
		this.neighbors.add(newNeighbor);;
	}

}
