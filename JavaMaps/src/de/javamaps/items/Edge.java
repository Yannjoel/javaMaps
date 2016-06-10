package de.javamaps.items;

public class Edge{
	      public final String vertex1;
	      public final String vertex2;
	      public final long distance;
	      public Edge(String v1, String v2, int distanceIn) {
	         this.vertex1 = v1;
	         this.vertex2 = v2;
	         this.distance = distanceIn;
	      }
}
	
