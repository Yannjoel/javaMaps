package de.javamaps.items;

public class Neighbor {
	final private String name;
	final private int dis;

	public Neighbor(String nameIn, int disIn) {
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