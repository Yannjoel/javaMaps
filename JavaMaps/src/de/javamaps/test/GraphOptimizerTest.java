package de.javamaps.test;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import org.junit.Test;

import de.javamaps.GraphOptimizer;
import de.javamaps.items.Neighbor;
import de.javamaps.items.Vertex;

public class GraphOptimizerTest {

	private TreeMap<Long, Vertex> testData(){
		TreeMap<Long, Vertex> testData = new TreeMap<Long, Vertex>();
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
		TreeMap<Long, Vertex> graph = new TreeMap<Long, Vertex>(testData());
		GraphOptimizer.uniteVertexs(graph);
		List<Neighbor> is = graph.get(1L).getNeighbors();
		int iterator = 0;
		for (Neighbor neighbor : is){
			assertEquals(neighbor.getName(), shouldBe.get(iterator).getName());
			assertEquals(neighbor.getDistance(), shouldBe.get(iterator).getDistance());
			iterator++;
		}
		
	}
}
