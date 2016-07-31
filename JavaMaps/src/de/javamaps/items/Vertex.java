package de.javamaps.items;

import java.util.LinkedList;
import java.util.List;

public class Vertex {

	private final long id;
	private final double latitude;
	private final double longitude;
	private final String name;
	private Long previous = null;
	private int totalDistance = Integer.MAX_VALUE; 
	private boolean visited;
	private List<Neighbor> neighbors = new LinkedList<Neighbor>();

	public void setAsStart() {
		this.totalDistance = 0;
	}

	public Vertex(String NameIn, long idIn, double latitudeIn, double longitudeIn) {
		this.name = NameIn;
		this.latitude = latitudeIn;
		this.longitude = longitudeIn;
		this.id = idIn;
		this.visited = false;
	}

	public Long getPrevious() {
		return previous;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setPrevious(Long previous) {
		this.previous = previous;
	}

	public int getTotalDistance() {
		return totalDistance;
	}

	public void setTotalDistance(int totalDistance) {
		this.totalDistance = totalDistance;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public long getId() {
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
		this.neighbors.add(newNeighbor);
	}

	public void addNeighbor(long id) {
		this.neighbors.add(new Neighbor(id, Integer.MAX_VALUE));
	}

	public boolean hasNeighbors() {
		if (this.neighbors.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
}
