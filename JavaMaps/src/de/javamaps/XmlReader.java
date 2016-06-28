package de.javamaps;

import java.io.*;
import javax.xml.stream.*;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.utils.IOUtils;

import de.javamaps.items.Vertex;

import java.util.*;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;

public class XmlReader {

	private static long id;
	private static float lon;
	private static float lat;
	private static String name = "-";
	private static boolean isJunction;

	public static void main(String[] args) throws XMLStreamException, IOException {
		FileInputStream fin = new FileInputStream("data/saarland.osm.bz2");
		BufferedInputStream in = new BufferedInputStream(fin);
		BZip2CompressorInputStream datastream;
		try {
			datastream = new BZip2CompressorInputStream(in);
		} catch (IOException e) {
			throw e;
		}
		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLStreamReader parser = factory.createXMLStreamReader(datastream);

		HashMap<Long, Vertex> vertexMap = new HashMap<Long, Vertex>();

		Set set = vertexMap.keySet();
		Iterator iterator = set.iterator();

		while (parser.hasNext()) {
			name = "-";
			if (parser.getEventType() == XMLStreamReader.START_ELEMENT) {
				if (parser.getLocalName() == "node") {
					isJunction = true;
					id = Long.parseLong(parser.getAttributeValue(0));
					lon = Float.parseFloat(parser.getAttributeValue(1));
					lat = Float.parseFloat(parser.getAttributeValue(2));
					getChildElements(parser);
					if (isJunction == true) {
						//vertexList.add(new Vertex(name, id, lon, lat));
						vertexMap.put(id, new Vertex(name, id, lon, lat));
					}
				}

			}
			parser.next();
		}
		System.out.println(vertexMap.size());
		while(iterator.hasNext()){
			System.out.println(vertexMap.get(iterator.next()).getName());
		}
		
/*		for (int i = 0; i < vertexList.size(); i++) {
			System.out.println(vertexList.get(i).getName());
		}
*/
	}

	private static void getChildElements(XMLStreamReader parser) throws XMLStreamException {
		int stop = 0;
		if (parser.getLocalName() == "node") {
			
			while (parser.hasNext() && stop == 0) {
				switch (parser.getEventType()) {

				case XMLStreamReader.START_ELEMENT:

					switch (parser.getLocalName()) {

					case "tag":
						
						if (Objects.equals(parser.getAttributeValue(0), "name")) {
							name = parser.getAttributeValue(1);
						}
						else if (Objects.equals(parser.getAttributeValue(0), "highway") && Objects.equals(parser.getAttributeValue(1), "motorway_junction")) {
							isJunction = true;
						}

						break;
					case "nd":

						break;
					}
					parser.next();

					break;

				case XMLStreamReader.END_ELEMENT:
					if (parser.getLocalName() == "node" || parser.getLocalName() == "way") {
						return;
					}
					parser.next();
					break;

				default:
					parser.next();
					break;
				} // switch ende
			}
		} else if (parser.getLocalName() == "way") {

		}
	}
}
