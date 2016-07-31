package de.javamaps.items;

import java.util.ArrayList;
import java.util.List;

public class Neighbor {
	final private long id;	// long ID anstatt String Name
	private int dis;
	
	public List<Long> onWay = new ArrayList<Long>();

	public Neighbor(long idIn, int disIn) {
		this.id = idIn;
		this.dis = disIn;
	}
	
	public long getName(){
		return this.id;
	}
	public int getDis(){
		return this.dis;
	}
	public void setDis(int disIn){
		this.dis = disIn;
	}
}