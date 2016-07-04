package de.javamaps.items;

public class Node {
	public long id;
	long neighbor1 = 0;
	long neighbor2 = 0;
	float distance1;
	float distance2;
	
	public Node(long id, long neighbor){
		this.id = id;
		if(this.neighbor1 == 0){
			neighbor1 = neighbor;
		}
		else{
			neighbor2 = neighbor;
		}
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
