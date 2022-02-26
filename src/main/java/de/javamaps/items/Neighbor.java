package de.javamaps.items;

public class Neighbor {
	final private long id;
	private int distance;

	public Neighbor(long idIn, int disIn) {
		this.id = idIn;
		this.distance = disIn;
	}
	
	public long getName(){
		return this.id;
	}
	public int getDistance(){
		return this.distance;
	}
	public void setDistance(int disIn){
		this.distance = disIn;
	}
}