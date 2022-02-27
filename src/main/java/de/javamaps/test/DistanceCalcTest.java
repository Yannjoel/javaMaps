package de.javamaps.test;

import de.javamaps.route.DistanceCalc;

import java.math.BigDecimal;
import java.util.*;
import static org.junit.Assert.*;

import de.javamaps.items.Neighbor;
import de.javamaps.items.Vertex;
import org.junit.Test;

/**
 * @author Tim
 * @version 1.01
 * @since 1.8.0_91
 */
public class DistanceCalcTest {
	@Test
	public void test() {

		Map<String, Vertex> testGraph = new HashMap<>();
		testGraph.put( "0", new Vertex("C",  "0", BigDecimal.valueOf(49.236444), BigDecimal.valueOf(6.9870489)));
		testGraph.put( "1", new Vertex("A",  "1", BigDecimal.valueOf(49.2363241), BigDecimal.valueOf(6.9859112)));
		testGraph.put( "2", new Vertex("D",  "2", BigDecimal.valueOf(49.2362993), BigDecimal.valueOf(6.985448)));
		testGraph.get( 0).addNeighbor(new Neighbor("1"));
		testGraph.get( 1).addNeighbor(new Neighbor("2"));
		Vertex vsolution;

		vsolution = (Vertex) DistanceCalc.calculateAllDistancesOfGraph(testGraph).get( 0);
		BigDecimal solution = vsolution.getNeighbors().get(0).getDistance();
		assertEquals(83, solution.intValue());

		vsolution = (Vertex) DistanceCalc.calculateAllDistancesOfGraph(testGraph).get( 1);
		solution = vsolution.getNeighbors().get(0).getDistance();
		assertEquals(33, solution.intValue());
	}
}
