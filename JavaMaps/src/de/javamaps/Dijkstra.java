package de.javamaps;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;

import de.javamaps.items.*;

public class Dijkstra {

	public static void getshortestWay(Long start, Long end, HashMap<Long, Vertex> graphMap) {
		StringBuffer output = new StringBuffer();
		try {
			graphMap.get(start).setAsStart();
		} catch (Exception e) {
			output.append("Cant find ");
			output.append(start);
			output.append(" in out map");
			output.append("\n");
		}
		try {
			graphMap.get(end).getName();
		} catch (Exception e) {
			output.append("Cant find ");
			output.append(end);
			output.append(" in out map");
			output.append("\n");
		}
		try {
			while (!graphMap.get(end).isVisited()) {
				Vertex inuse = graphMap.get(getNext(graphMap));
				if (inuse.hasNeighbors()) {
					Neighbor nextN = inuse.nearestNeighbor();
					Vertex nextV = graphMap.get(nextN.getName());
					if (!nextV.isVisited()) {
						int newDis = inuse.getWay_dist() + nextN.getDis();
						if (newDis < nextV.getWay_dist()) {
							nextV.setWay_dist(inuse.getWay_dist() + nextN.getDis());
							nextV.setPrevious(inuse.getPrevious()+" - " + inuse.getName());
						}
					}
				}
				else{
					inuse.setVisited(true);
				}
			}
			output.append("Total Distance from ");
			output.append(graphMap.get(start).getName());
			output.append(" to ");
			output.append(graphMap.get(end).getName());
			output.append(" is: ");
			output.append(graphMap.get(end).getWay_dist());
			output.append("\n");
			output.append("Way was");
			output.append(graphMap.get(end).getPrevious());
			output.append(" - "+(graphMap.get(end).getName()));

		} catch (Exception e) {
			output.append("cant reach ");
			output.append(end);
			output.append("\n");
		}
		System.out.println(output);
	}



	private static Long getNext(HashMap<Long, Vertex> graphMap) {
		Long out = null;
		int min = Integer.MAX_VALUE;
		for (Entry<Long, Vertex> e : graphMap.entrySet()) {
			/// Wenn noch offene Knoten bestehen kleinsten weg ermitteln
			if (!e.getValue().isVisited() && e.getValue().getWay_dist() < min) {
				min = e.getValue().getWay_dist();
				out = e.getKey();
			}
		}
		return out;
	}
}
