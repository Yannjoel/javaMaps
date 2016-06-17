package de.javamaps.items;

import java.util.*;

public class DijkstraHelpingList {
	private List<Vertex> list = new ArrayList<Vertex>();

	public DijkstraHelpingList() {
	}

	public void addVertex(Vertex v) {
		this.list.add(v);
	}

	public void sort() {
		Collections.sort(list, new Comparator<Vertex>() {
			@Override
			public int compare(Vertex vertex1, Vertex vertex2) {
				return (vertex1.getWay_dist()) - ((vertex2.getWay_dist()));
			}
		});
	}

	public Boolean visitedAll() {
		for (Vertex v : list) {
			if (!v.isVisited()) {
				return false;
			}
		}
		return true;
	}
	
	public Vertex next(){
		for (Vertex v:list){
			if (!v.isVisited()){
				return v;
			}
		}
		return list.get(0);
	}
}
