package de.javamaps.items;

public class Vertex {
	

	private final int locCode;
	private final String name;
	private Vertex previous = null;
	private int way_dist = Integer.MAX_VALUE; /// ersatz für unendlich
												/// //bisheriger Weg zu diesem
												/// Knoten
	private boolean visited;
	
	public void setAsStart(){
		this.way_dist = 0;
	}
	
	public Vertex(String NameIn, int locCodeIn) {
		this.name = NameIn;
		this.locCode = locCodeIn;
	}
	public Vertex getPrevious() {
		return previous;
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

	public int getLocCode() {
		return locCode;
	}

	public String getName() {
		return name;
	}
	
}

	