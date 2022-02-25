package de.javamaps.parser;

import java.io.*;
import javax.xml.stream.*;

import de.javamaps.items.Vertex;

import java.util.*;

public class XmlReader {

	public static Map<Long, Vertex> parseMapFromXmlFile() throws XMLStreamException, IOException {
		Map<Long, Vertex> mapGraph = new HashMap<>();
		//System.out.println("Working Directory = " + System.getProperty("user.dir"));
		FileInputStream fin = new FileInputStream("data/deutschland.xml");
		BufferedInputStream in = new BufferedInputStream(fin);

		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLStreamReader parser = factory.createXMLStreamReader(in);
		while (parser.hasNext()) {
			if (parser.getEventType() != XMLStreamReader.START_ELEMENT || parser.getLocalName() != "node") {
				parser.next();
				continue;
			}
			Vertex vertex = new Vertex(parser.getAttributeValue(7), Long.parseLong(parser.getAttributeValue(0)),
					Double.parseDouble(parser.getAttributeValue(1)),
					Double.parseDouble(parser.getAttributeValue(2)));
			Long neighbour1 = Long.parseLong(parser.getAttributeValue(3));
			Long neighbour2 = Long.parseLong(parser.getAttributeValue(4));
			if(neighbour1 != 0){
				vertex.addNeighbor(neighbour1);
				if(neighbour2 != 0){
					vertex.addNeighbor(neighbour2);
				}
			}
			mapGraph.put(Long.parseLong(parser.getAttributeValue(0)), vertex);
			parser.next();
		}
		return mapGraph;
	}
}
