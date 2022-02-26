package de.javamaps.test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import de.javamaps.items.Neighbor;
import de.javamaps.items.Vertex;
import de.javamaps.route.Dijkstra;
import de.javamaps.GlobalApplicationStorage;
import org.junit.Before;
import org.junit.Test;

public class DijkstraTest {

	@Before
	private void initGlobalTestData(){
		Map<Long, Vertex> testData = new HashMap<>();
		// Testgraph
		testData.put((long) 1, new Vertex("Stadt1", 1, 0, 0));
		testData.put((long) 2, new Vertex("Stadt2", 2, 0, 0));
		testData.put((long) 3, new Vertex("Stadt3", 3, 0, 0));
		testData.get((long) 1).addNeighbor(new Neighbor(2, 1000));
		testData.get((long) 1).addNeighbor(new Neighbor(3, 5000));
		testData.get((long) 2).addNeighbor(new Neighbor(3, 1000));
		GlobalApplicationStorage.getGlobalStorage().setMapData(testData);
	}

	@Test
	public void testAll() {
		StringBuilder shouldBe = new StringBuilder("Total Distance: 2.0 km \n \nWay was:\nStadt1\nStadt2\nStadt3\n");
		StringBuilder is = new StringBuilder(Dijkstra.calculate((long)1,(long) 3));
		assertEquals(shouldBe.toString(),is.toString());
	}
}
