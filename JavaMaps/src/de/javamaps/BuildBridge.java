package de.javamaps;

import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;
import de.javamaps.items.*;

public class BuildBridge{
	public static void createBridge(TreeMap<Long, Vertex> graph){
		
		for (Entry<String, List<Long>> e: MotorwayRamp.getMotorwayRamps(graph).entrySet()){
			for(Long id:e.getValue()){
				Vertex actual = graph.get(id);
				for(Long idNewN:e.getValue()){
					if (idNewN != id){
						if(distance(graph.get(idNewN),graph.get(id))<600){
						actual.addNeighbor(idNewN);
						}
					}
				}
			}

		}
	}
	public static Long distance(Vertex vertex, Vertex nvertex){
		double distance = 0;
		double dx = 0;
		double dy = 0;
		double lat = 0;
		double lat1 = 0;
		double lat2 = 0;
		double lon1 = 0;
		double lon2 = 0;
		

		lon1 = vertex.getLon();
		lon2 = nvertex.getLon();
		lat1 = vertex.getLat();
		lat2 = nvertex.getLat();
		lat = Math.toRadians((lat1 + lat2) / 2);
		dx = 111.3 * Math.cos(lat) * (lon1 - lon2); // *111.3 für Erdkrümmung
		dy = 111.3 * (lat1 - lat2); 
		distance = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2)); // vgl. Satz des Pythagoras
		distance = distance * 1000;
		
		return (long) distance;
	}
}
