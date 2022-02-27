package de.javamaps.parser.xml;

import java.io.*;
import javax.xml.stream.*;

import de.javamaps.items.Vertex;

import java.math.BigDecimal;
import java.util.*;

public class XmlReader {

	public static Map<Long, Vertex> parseMapFromXmlFile() throws XMLStreamException, IOException {
		Map<Long, Vertex> mapGraph = new HashMap<>();
		InputStream mapDataFileAsStream = XmlReader.class.getResourceAsStream("/data/deutschland.xml");
		BufferedInputStream in = new BufferedInputStream(mapDataFileAsStream);

		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLStreamReader parser = factory.createXMLStreamReader(in);
		while (parser.hasNext()) {
			if (parser.getEventType() != XMLStreamReader.START_ELEMENT || parser.getLocalName() != "node") {
				parser.next();
				continue;
			}
			Vertex vertex = new Vertex(parser.getAttributeValue(7), parser.getAttributeValue(0),
					BigDecimal.valueOf(Double.parseDouble(parser.getAttributeValue(1))),
							BigDecimal.valueOf(Double.parseDouble(parser.getAttributeValue(2))));
			String neighbour1 = parser.getAttributeValue(3);
			String neighbour2 = parser.getAttributeValue(4);
			if(!neighbour1.equals("0")){
				vertex.addNeighbor(neighbour1);
				if(!neighbour2.equals(0)){
					vertex.addNeighbor(neighbour2);
				}
			}
			mapGraph.put(Long.parseLong(parser.getAttributeValue(0)), vertex);
			parser.next();
		}
		return mapGraph;
	}
}
