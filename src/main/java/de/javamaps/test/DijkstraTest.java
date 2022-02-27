package de.javamaps.test;

import static org.junit.Assert.*;

import java.math.BigDecimal;
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
		Map<String, Vertex> testData = new HashMap<>();
		// Testgraph
		testData.put("1", new Vertex("Stadt1", "1", BigDecimal.ZERO, BigDecimal.ZERO));
		testData.put("2", new Vertex("Stadt2", "2", BigDecimal.ZERO, BigDecimal.ZERO));
		testData.put("3", new Vertex("Stadt3", "3", BigDecimal.ZERO, BigDecimal.ZERO));
		testData.get("1").addNeighbor(new Neighbor("2", BigDecimal.valueOf(1000)));
		testData.get("1").addNeighbor(new Neighbor("3", BigDecimal.valueOf(5000)));
		testData.get("2").addNeighbor(new Neighbor("3", BigDecimal.valueOf(1000)));
		GlobalApplicationStorage.getGlobalStorage().setMapData(testData);
	}

	@Test
	public void testAll() {
		StringBuilder shouldBe = new StringBuilder("Total Distance: 2.0 km \n \nWay was:\nStadt1\nStadt2\nStadt3\n");
		StringBuilder is = new StringBuilder(Dijkstra.calculate("1", "3"));
		assertEquals(shouldBe.toString(),is.toString());
	}
}
