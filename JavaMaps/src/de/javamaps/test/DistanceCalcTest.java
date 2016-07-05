package de.javamaps.test;

import de.javamaps.DistanceCalc;
import de.javamaps.items.*;
import java.util.*;
import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Tim
 * @version 1.01
 * @since 1.8.0_91
 */
public class DistanceCalcTest {
	@Test
	public void test() {

		HashMap<Long, Vertex> testmap = new HashMap<Long, Vertex>();
		testmap.put((long) 0, new Vertex("C", (long) 0, 49.236444, 6.9870489, 1, 0));
		testmap.put((long) 1, new Vertex("A", (long) 1, 49.2363241, 6.9859112, 2, 0));
		testmap.put((long) 2, new Vertex("D", (long) 2, 49.2362993, 6.985448, 0, 0));
		testmap.get((long) 0).addNeighbor(new Neighbor(1, 0));
		testmap.get((long) 1).addNeighbor(new Neighbor(2, 0));
		Vertex vsolution;

		// Überprüfung 1
		vsolution = (Vertex) DistanceCalc.distanceCalculation(testmap).get((long) 0);
		int solution = vsolution.getNeighbors().get(0).getDis();
		assertEquals(83, solution);

		// Überprüfung 2, andere Testdaten
		vsolution = (Vertex) DistanceCalc.distanceCalculation(testmap).get((long) 1);
		solution = vsolution.getNeighbors().get(0).getDis();
		assertEquals(33, solution);

	}

}
