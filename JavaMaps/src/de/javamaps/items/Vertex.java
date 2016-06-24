package de.javamaps.items;

import java.util.ArrayList;
import java.util.List;

public class Vertex {

	private final int id;
	private final float lat;
	private final float lon;
	private final String name;
	private String previous = null;
	private int way_dist = Integer.MAX_VALUE; /// ersatz für unendlich
												/// //bisheriger Weg zu diesem
												/// Knoten
	private boolean visited;
	private List<Neighbor> neighbors = new ArrayList<Neighbor>();

	public void setAsStart() {
		this.way_dist = 0;
	}

	public Vertex(String NameIn, int idIn, float latIn, float lonIn) {
		this.name = NameIn;
		this.lat = latIn;
		this.lon = lonIn;
		this.id = idIn;
		this.visited = false;
	}

	public String getPrevious() {
		return previous;
	}

	public float getLat() {
		return lat;
	}

	public float getLon() {
		return lon;
	}

	public void setPrevious(String previous) {
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
	
	public boolean hasNeighbors(){
		if (this.neighbors.isEmpty()){
			return false;
		}
		else{
			return true;
		}
	}
	public Neighbor nearestNeighbor(){
		int min = Integer.MAX_VALUE;
		Neighbor out = null;
		for(Neighbor n :this.neighbors){
			if (n.getDis() < min){
				out = n;
			}
		}
		return out;
	}

}
