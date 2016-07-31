package de.javamaps.test;

import static org.junit.Assert.*;

import java.util.TreeMap;

import org.junit.Test;

import de.javamaps.Dijkstra;
import de.javamaps.items.Neighbor;
import de.javamaps.items.Vertex;

public class DijkstraTest {

	private TreeMap<Long, Vertex> testData(){
		TreeMap<Long, Vertex> testData = new TreeMap<Long, Vertex>();
	// Testgraph
	testData.put((long) 1, new Vertex("Stadt1", 1, 0, 0));
	testData.put((long) 2, new Vertex("Stadt2", 2, 0, 0));
	testData.put((long) 3, new Vertex("Stadt3", 3, 0, 0));
	testData.get((long) 1).addNeighbor(new Neighbor(2, 1000));
	testData.get((long) 1).addNeighbor(new Neighbor(3, 5000));
	testData.get((long) 2).addNeighbor(new Neighbor(3, 1000));
	return testData;
	}
	@Test
	public void testAll() {
		StringBuffer shouldBe = new StringBuffer("Total Distance from Stadt1 to Stadt3 is: 2.0 km \n \nWay was:\nStadt1\nStadt2\nStadt3\n");
		StringBuffer is = new StringBuffer(Dijkstra.calculate((long)1,(long) 3,testData()));
		assertEquals(shouldBe.toString(),is.toString());
		assertEquals("Cant find 4 in our map\n",Dijkstra.calculate((long)4,(long) 3,testData()).toString());
		assertEquals("Cant find 4 in our map\n",Dijkstra.calculate((long)1,(long) 4,testData()).toString());
	}
}
