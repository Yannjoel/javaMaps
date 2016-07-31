package de.javamaps;

import java.io.*;
import javax.xml.stream.*;

import de.javamaps.items.Neighbor;
import de.javamaps.items.Vertex;

import java.util.*;

public class XmlReader {

	public static TreeMap<Long, Vertex> graphFromXmlFile = new TreeMap<Long, Vertex>();

	public static void initialize(Gui gui) throws XMLStreamException, IOException {
		FileInputStream fin = new FileInputStream("data/deutschland.xml");
		BufferedInputStream in = new BufferedInputStream(fin);

		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLStreamReader parser = factory.createXMLStreamReader(in);

		Vertex ver;
		Long nb1;
		Long nb2;
		while (parser.hasNext()) {
			if (parser.getEventType() == XMLStreamReader.START_ELEMENT) {
				if (parser.getLocalName() == "node") {
					ver = new Vertex(parser.getAttributeValue(7), Long.parseLong(parser.getAttributeValue(0)),
							Double.parseDouble(parser.getAttributeValue(1)),
							Double.parseDouble(parser.getAttributeValue(2)));
					nb1 = Long.parseLong(parser.getAttributeValue(3));
					nb2 = Long.parseLong(parser.getAttributeValue(4));
					if(nb1 != 0){
						ver.addNeighbor(nb1);
						if(nb2 != 0){
							ver.addNeighbor(nb2);
						}
					}
					graphFromXmlFile.put(Long.parseLong(parser.getAttributeValue(0)), ver);
					

				} else if (parser.getLocalName() == "bounds") {

				}
			}
			parser.next();
		}

		Set<Long> set = graphFromXmlFile.keySet();
		Iterator<Long> iterator = set.iterator();

		while (iterator.hasNext()) {
			Vertex tmp = graphFromXmlFile.get(iterator.next());
			for(Neighbor neighbor : tmp.getNeighbors()){
				gui.addLine(tmp, graphFromXmlFile.get(neighbor.getName()));
			}
		}
		gui.drawLines();
	}
}
