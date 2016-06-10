package route.items;

import java.util.*;
import java.util.Map;

public class RouteMap {
	private final Map<String, Standpunkt> graph;

	public static class Standpunkt implements Comparable<Standpunkt> {
		public final String name;
		public Standpunkt previous = null;
		public int dist = Integer.MAX_VALUE; /// ersatz für unendlich
		public final Map<Standpunkt, Integer> adjacentCitys = new HashMap<>();

		public Standpunkt(String name) {
			this.name = name;
		}

		private StringBuffer createPath() {
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

		public int compareTo(Standpunkt other) {
			return Integer.compare(dist, other.dist);
		}
	}

	//Karte durch "Kanten" (Verbindungen) erstellen
	public RouteMap(Connection[] connection) {
		graph = new HashMap<>(connection.length);

		for (Connection e : connection) {
			if (!graph.containsKey(e.city1))
				graph.put(e.city1, new Standpunkt(e.city1));
			if (!graph.containsKey(e.city2))
				graph.put(e.city2, new Standpunkt(e.city2));
		}

		for (Connection e : connection) {
			graph.get(e.city1).adjacentCitys.put(graph.get(e.city2), e.distance);
		}
	}

	public void dijkstra(String startName) {
		if (!graph.containsKey(startName)) {
			System.err.printf("cant find start city", startName);
			return;
		}
		final Standpunkt source = graph.get(startName);
		NavigableSet<Standpunkt> navSet = new TreeSet<>();

		// set-up vertices
		for (Standpunkt point : graph.values()) {
			if(point == source){
				point.previous = source;
				point.dist = 0;
			}
			else {
				point.previous = null;
				point.dist = Integer.MAX_VALUE;
			}
		}

		Standpunkt point1, point2;
		while (!navSet.isEmpty()) {

			point1 = navSet.pollFirst(); // nächster standpunkt 
			if (point1.dist == Integer.MAX_VALUE) ///wenn nicht erreichbar
				break; //überspringen
			
			// distanz zu anliegenden städten überprüfen
			for (Map.Entry<Standpunkt, Integer> adjacentCitys : point1.adjacentCitys.entrySet()) {
				point2 = adjacentCitys.getKey(); 

				final int alternateDist = point1.dist + adjacentCitys.getValue();
				if (alternateDist < point2.dist) { //wenn es einen kürzeren Pfad gibt
					navSet.remove(point2);
					point2.dist = alternateDist;
					point2.previous = point1;
					navSet.add(point2);
				}
			}
		}
	}


	// alle Pfade ausgeben
	public void printAllPaths() {
		for (Standpunkt v : graph.values()) {
			System.out.print(v.createPath());
			System.out.println();
		}
	}

	// weg ausgeben
	public void printPath(String endName) {
		if (!graph.containsKey(endName)) {
			System.err.printf("Cant find end city \"%s\"\n", endName);
			return;
		}

		System.out.print(graph.get(endName).createPath());
		System.out.println();
	}
}
