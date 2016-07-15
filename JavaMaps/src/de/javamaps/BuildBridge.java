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
						actual.addNeighbor(idNewN);
					}
				}
			}

		}
	}
}
