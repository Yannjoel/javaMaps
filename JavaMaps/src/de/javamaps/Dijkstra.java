package de.javamaps;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;

import de.javamaps.items.*;

public class Dijkstra {

	public static String getshortestWay(String start, String end, HashMap<String, Vertex> graphMap) {
		StringBuffer output = new StringBuffer();
		graphMap.get(start).setAsStart();
		while(!graphMap.get(end).isVisited()){
			
		}

		return output.toString();
	}
	private String getNext(HashMap<String, Vertex> graphMap){
		String out = null;
		int min= Integer.MAX_VALUE;
		for (Entry<String, Vertex> e : graphMap.entrySet()){
			if (!e.getValue().isVisited() && e.getValue().getWay_dist()< min){
				min = e.getValue().getWay_dist();
				out = e.getKey();
			}
		}
		
		return out;
	}
}
