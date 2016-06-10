package de.javamaps.items;

import java.util.HashMap;
import java.util.Map;



public class Vertex implements Comparable<Vertex> {
	public final String name;
	public Vertex previous = null;
	public int dist = Integer.MAX_VALUE; /// ersatz für unendlich
	public final Map<Vertex, Integer> adjacentCitys = new HashMap<>();

	public Vertex(String name) {
		this.name = name;
	}

	public StringBuffer createPath() {
		StringBuffer forReturn = new StringBuffer(); 
		if (this == this.previous) {
			forReturn.append(this.name);
		} else if (this.previous == null) {
			forReturn.append("(unreached)" + this.name);
		} else {
			forReturn.append(this.previous.createPath());
			forReturn.append(" -> " + this.name.toString() + "(" + this.dist + ")");
		}
		return forReturn;
	}

	public int compareTo(Vertex other) {
		return Integer.compare(dist, other.dist);
	}
}
