package de.javamaps.items;

public class Edge{
	      private final String vertex1;
	      private final String vertex2;
	      private final long distance;
	      public Edge(String v1, String v2, int distanceIn) {
	         this.vertex1 = v1;
	         this.vertex2 = v2;
	         this.distance = distanceIn;
	      }
		public String getVertex1() {
			return vertex1;
		}
		public String getVertex2() {
			return vertex2;
		}
		public long getDistance() {
			return distance;
		}
}
	
