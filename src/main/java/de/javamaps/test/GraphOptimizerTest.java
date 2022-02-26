package de.javamaps.test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.javamaps.items.Neighbor;
import de.javamaps.items.Vertex;
import org.junit.Test;

import de.javamaps.parser.GraphOptimizer;

import static org.junit.Assert.assertEquals;

public class GraphOptimizerTest {

	private Map<Long, Vertex> testData(){
		Map<Long, Vertex> testData = new HashMap<>();
	// Testgraph
	testData.put((long) 1, new Vertex("Stadt1", 1, 0, 0));
	testData.put((long) 2, new Vertex("null", 2, 0, 0));
	testData.put((long) 3, new Vertex("Stadt3", 3, 0, 0));
	testData.get((long) 1).addNeighbor(new Neighbor(2, 1000));
	testData.get((long) 1).addNeighbor(new Neighbor(3, 5000));
	testData.get((long) 2).addNeighbor(new Neighbor(3, 1000));
	return testData;
	}
	@Test
	public void testAll() {
		List<Neighbor> shouldBe = new LinkedList<Neighbor>();
		shouldBe.add(new Neighbor(3, 2000));
		shouldBe.add(new Neighbor(3, 5000));
		Map<Long, Vertex> graph = new HashMap<>(testData());
		GraphOptimizer.uniteVertexes(graph);
		List<Neighbor> is = graph.get(1L).getNeighbors();
		int iterator = 0;
		for (Neighbor neighbor : is){
			assertEquals(neighbor.getName(), shouldBe.get(iterator).getName());
			assertEquals(neighbor.getDistance(), shouldBe.get(iterator).getDistance());
			iterator++;
		}
		
	}
}
