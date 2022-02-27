package de.javamaps.items;

import java.io.Serializable;
import java.math.BigDecimal;

public class Neighbor implements Serializable {

	//default serialVersion id
	private static final long serialVersionUID = 1L;

	final private String id;
	private BigDecimal distance;
	private transient Vertex vertex;

	public Neighbor(String idIn, BigDecimal distance) {
		this.id = idIn;
		this.distance = distance;
	}

	public Neighbor(String id){
		this.id = id;
	}
	
	public String getID(){
		return this.id;
	}
	public BigDecimal getDistance(){
		return this.distance;
	}
	public void setDistance(BigDecimal disIn){
		this.distance = disIn;
	}

	public Vertex getVertex() {
		return vertex;
	}

	public void setVertex(Vertex vertex) {
		this.vertex = vertex;
	}
}