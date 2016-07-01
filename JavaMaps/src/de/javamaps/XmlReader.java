package de.javamaps;

import java.io.*;
import javax.xml.stream.*;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.utils.IOUtils;

import de.javamaps.items.Neighbor;
import de.javamaps.items.Vertex;

import java.util.*;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;

public class XmlReader {

	private static long id;
	private static float lon;
	private static float lat;
	private static String name = null;
	private static boolean isJunction;
	private static boolean isMotorway;
	
	public static HashMap<Long, Vertex> vertexMap = new HashMap<Long, Vertex>();
	public static HashMap<Long, Vertex> junctionMap = new HashMap<Long, Vertex>();
	
	private static List<Long> ndList = new ArrayList<Long>();

	public static void XmlReader(Gui gui) throws XMLStreamException, IOException {
		FileInputStream fin = new FileInputStream("C:/Users/Jan-Laptop/Downloads/germany-latest.osm.bz2");
		BufferedInputStream in = new BufferedInputStream(fin);
		BZip2CompressorInputStream datastream;
		try {
			datastream = new BZip2CompressorInputStream(in);
		} catch (IOException e) {
			throw e;
		}
		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLStreamReader parser = factory.createXMLStreamReader(datastream);
		
		HashMap<Long, Vertex> inputMap = new HashMap<Long, Vertex>();

		while (parser.hasNext()) {
			name = null;
			if (parser.getEventType() == XMLStreamReader.START_ELEMENT) {
				if (Objects.equals(parser.getLocalName(), "node")) {	// Element is node
					isJunction = false;
					id = Long.parseLong(parser.getAttributeValue(0));
					lon = Float.parseFloat(parser.getAttributeValue(1));
					lat = Float.parseFloat(parser.getAttributeValue(2));
					getChildElements(parser);
					inputMap.put(id, new Vertex(name, id, lon, lat));
					if (isJunction == true && name != null) {
						junctionMap.put(id, new Vertex(name, id, lon, lat));
					}
				}
				
				else if(Objects.equals(parser.getLocalName(), "way")){	// Element ist way
					ndList.clear();
					isMotorway = false;
					getChildElements(parser);
					
					if(isMotorway){
						for(int i = 0; i < ndList.size(); i++){
							vertexMap.put(ndList.get(i), inputMap.get(ndList.get(i)));
							if(i < ndList.size()-1){
								vertexMap.get(ndList.get(i)).addNeighbor(new Neighbor(ndList.get(i+1),1));
								gui.addLine(vertexMap.get(ndList.get(i)), vertexMap.get(ndList.get(i)));

							}
						}
					}
					
				}

			}
			parser.next();
		}
		gui.drawLines();
		System.out.println(inputMap.size());
		
		fin.close();
		in.close();
		datastream.close();
		
		parser.close();
		
		
		Set<Long> set = junctionMap.keySet();
		Iterator<Long> iterator = set.iterator();
		System.out.println(junctionMap.size());
		
		while(iterator.hasNext()){
			Long id = iterator.next();
			if(vertexMap.get(id) != null){
				System.out.println(id +" "+ junctionMap.get(id).getName() +" "+ vertexMap.get(id).getLat() +" "+ vertexMap.get(id).getLon() +" "+ vertexMap.get(id).getNeighbors());
				
			}
			else{
				iterator.remove();	// etfernen von Junctions, die nicht auf einer Autobahn liegen.
			}
		}
		System.out.println("Input Map:" + inputMap.size());
		System.out.println("Vertexes: " + vertexMap.size());
		System.out.println("Junctions: " + junctionMap.size());
		inputMap.clear();
		inputMap = null;
	}

//-------------------------------------------------------------------------------------------------	
	
	private static void getChildElements(XMLStreamReader parser) throws XMLStreamException {
		int stop = 0;
		if (Objects.equals(parser.getLocalName(), "node")) {	// Übergeordnetes Element ist node
			
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
		} 
		
		
		else if (Objects.equals(parser.getLocalName(), "way")) {	//Übergeordnetes Element ist way
			while (parser.hasNext() && stop == 0) {
				switch (parser.getEventType()) {

				case XMLStreamReader.START_ELEMENT:

					switch (parser.getLocalName()) {

					case "tag":
						
						if (Objects.equals(parser.getAttributeValue(1), "motorway") || Objects.equals(parser.getAttributeValue(1), "motorway_link")) {
							isMotorway = true;
						}

						break;
					case "nd":
						
						ndList.add(Long.parseLong(parser.getAttributeValue(0)));
						
						break;
					}
					parser.next();

					break;

				case XMLStreamReader.END_ELEMENT:
					if (parser.getLocalName() == "way") {
						return;
					}
					parser.next();
					break;

				default:
					parser.next();
					break;
				} // switch ende
			} // while ende
		}
	}
}

