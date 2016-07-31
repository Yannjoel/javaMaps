package de.javamaps;

import java.util.TreeMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map.Entry;
import de.javamaps.items.Neighbor;
import de.javamaps.items.Vertex;

public class GraphOptimizer {

	private static final String STARTMESSAGE =  "Karte wird optimiert - Dies kann einige Minuten dauern";
	
	public static void uniteVertexs(TreeMap<Long, Vertex> graph) {
		System.out.println(STARTMESSAGE);
		TreeMap<Long, Vertex> importantVertexs = new TreeMap<Long, Vertex>();
		for (Entry<Long, Vertex> entryOfGraph : graph.entrySet()){
			if (!entryOfGraph.getValue().getName().equals("null") || entryOfGraph.getValue().getNeighbors().size() > 1) {
				Vertex currentVertex = entryOfGraph.getValue();
				importantVertexs.put(entryOfGraph.getKey(), currentVertex);
				List<Neighbor> newNeighbors = new ArrayList<Neighbor>();
				for (Neighbor neighborVertex : currentVertex.getNeighbors()) {
					reinitVisitedVertexes();
					Vertex nextVertex = graph.get(neighborVertex.getName());
					int dis = neighborVertex.getDis();
					while (nextVertex.getName().equals("null") && nextVertex.getNeighbors().size() == 1 && !visitedVertexes.contains(nextVertex)) {
						addToVisitedVertexes(nextVertex);
						neighborVertex = nextVertex.getNeighbors().get(0);
						dis += neighborVertex.getDis();
						nextVertex = graph.get(neighborVertex.getName());
					}
					newNeighbors.add(new Neighbor(neighborVertex.getName(), dis));
				}
				currentVertex.setNeighbors(newNeighbors);
			}
			if (!entryOfGraph.getValue().getName().equals("null") || entryOfGraph.getValue().getNeighbors().size() == 0) {
				Vertex v = entryOfGraph.getValue();
				importantVertexs.put(entryOfGraph.getKey(), v);
			}

		}
		System.out.println("done");
	}

	private static List<Vertex> visitedVertexes = new ArrayList<Vertex>();
	private static void reinitVisitedVertexes(){
		visitedVertexes.clear();
	}
	private static void addToVisitedVertexes(Vertex vertex){
		visitedVertexes.add(vertex);
	}
}
