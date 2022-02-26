package de.javamaps.items;

public class Node {
	public long id;
	public long neighbor1 = 0;
	public long neighbor2 = 0;
	public float distance1 = 1;
	public float distance2 = 1;
	
	public Node(long id, long neighbor){
		this.id = id;
		neighbor1 = neighbor;

	}
	
	public Node(long id){
		this.id = id;
	}
	
	public void addNeighbor(long neighbor){
		if(this.neighbor1 == 0){
			neighbor1 = neighbor;
		}
		else{
			neighbor2 = neighbor;
		}
	}
}
