package de.javamaps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import de.javamaps.items.Vertex;

public class MotorwayRamp {
	public static TreeMap<String, List<Long>> getMotorwayRamps(TreeMap<Long, Vertex> graph) {
		TreeMap<String, List<Long>> out = new TreeMap<String, List<Long>>();
		String name = null;
		Long singleId = null;
		
		// Über den komplette Graphen iterieren
		for (Entry<Long, Vertex> e : graph.entrySet()) {

			// Hat die Vertex einen Namen, so wird der Knoten als möglicher
			// Startpunkt gewertet.
			if (!e.getValue().getName().equals("null")) {
				name = e.getValue().getName();
				singleId = e.getKey();

				// Ist schon eine Auffahrt mit diesem Namen vorhanden, so soll
				// die neue ID angehängt werden.
				if (out.containsKey(name)) {
					out.get(name).add(singleId);
				}
				// Ist noch keine Auffahrt mit diesem Namen vorhanden, so
				// wird ein neuer Eintrag erstellt
				else {
					List<Long> temp = new ArrayList<Long>();
					temp.add(singleId);
					out.put(name, temp);
				}
				//Für die nächste Iteration wieder die names und IDs zurücksetzen
				name = null;
				singleId = null;
			}
		}
		System.out.println("Namenliste erzeugt....");
		return out;
	}
}
