package de.javamaps.parser;


import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.Map;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;

import de.javamaps.items.Node;

public class XmlParser {

	private static String name;
	private static boolean isJunction;
	private static boolean isMotorway;
	private static String source = "data/deutschland.osm.bz2"; 
	private static String target = "data/deutschland.xml";
	private static List<Long> ndList = new ArrayList<>();
	//private static List<Long> nodes = new LinkedList<Long>();
	private static Map<Long, Node> nodes = new HashMap<>();
	
	
	public static void main(String[] args) throws XMLStreamException, IOException {
		FileInputStream fin = new FileInputStream(source);
		BufferedInputStream in = new BufferedInputStream(fin);
		BZip2CompressorInputStream datastream;
		try {
			datastream = new BZip2CompressorInputStream(in);
		} catch (IOException e) {
			throw e;
		}
		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLStreamReader parser = factory.createXMLStreamReader(datastream);
		
		XMLOutputFactory outfactory = XMLOutputFactory.newInstance();
		XMLStreamWriter writer = outfactory.createXMLStreamWriter(new FileOutputStream(target), "UTF-8");

		long id;
		double lon;
		double lat;
		
		Node temp;

//---------------WAYS-------------------------------------------------
// holt relevante Nodes (alle moterways)
		while (parser.hasNext()) {

			if (parser.getEventType() == XMLStreamReader.START_ELEMENT) {				
				if(Objects.equals(parser.getLocalName(), "way")){	// Element ist way
					isMotorway = false;
					ndList.clear();
					getChildElements(parser);
					if(isMotorway){
						for(int i = 0; i < ndList.size()-1; i++){
							id = ndList.get(i);
							if(nodes.get(id) == null){
								nodes.put(id, new Node(id,ndList.get(i+1)));
							}
							else{
								nodes.get(id).addNeighbor(ndList.get(i+1));
							}
						}
						id = ndList.get(ndList.size()-1);
						if(nodes.get(ndList.get(ndList.size()-1)) == null){
							nodes.put(id, new Node(id));
						}
						
					}
				}

			}
			parser.next();
		}

//----------------RESTART STREAMS----------------------------------------------------------------------
// informationen zu nodes
		fin.close();
		in.close();
		datastream.close();		
		parser.close();
		
		fin = new FileInputStream(source);
		in = new BufferedInputStream(fin);
		try {
			datastream = new BZip2CompressorInputStream(in);
		} catch (IOException e) {
			throw e;
		}
		parser = factory.createXMLStreamReader(datastream);
		
//----------------NODES------------------------------------------------------------------------------
//Schreibt neue xml-Datei
		writer.writeStartDocument("UTF-8", "1.0");
		writer.writeCharacters(System.getProperty("line.separator"));
		writer.writeStartElement("data");

	    
		while(parser.hasNext()){
			name = "null";
			if (parser.getEventType() == XMLStreamReader.START_ELEMENT){
				if (Objects.equals(parser.getLocalName(), "node")) {	// Element is node
					id = Long.parseLong(parser.getAttributeValue(0));
					
					if((temp = nodes.get(id)) != null){
						setJunction(false);
						lat = Double.parseDouble(parser.getAttributeValue(1));
						lon = Double.parseDouble(parser.getAttributeValue(2));
						getChildElements(parser);
						//System.out.println(id +" "+ name +" "+ lon +" "+ lat +" "+ temp.id);
						writer.writeCharacters(System.getProperty("line.separator"));
						writer.writeStartElement("node");
					    writer.writeAttribute("id", String.valueOf(id));
					    writer.writeAttribute("lat", String.valueOf(lat));
					    writer.writeAttribute("lon", String.valueOf(lon));
					    writer.writeAttribute("nb1", String.valueOf(temp.neighbor1));
					    writer.writeAttribute("nb2", String.valueOf(temp.neighbor2));
					    writer.writeAttribute("dis1", String.valueOf(temp.distance1));
					    writer.writeAttribute("dis2", String.valueOf(temp.distance2));
					    writer.writeAttribute("name", name);
					    writer.writeEndElement();
					}	
				}
			}
			parser.next();
		}
		writer.writeEndElement();
		writer.writeEndDocument();
	    writer.flush();
	    writer.close();
	}
		
//----------------DEBUG------------------------------------------------------------------------------
/*		Set<Long> set = nodes.keySet();
		Iterator<Long> iterator = set.iterator();
		
		while(iterator.hasNext()){
			System.out.println(nodes.get(iterator.next()).id);
		}
		
		System.out.println(nodes.size());

	}
*/
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
							setJunction(true);
						}

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

	public static boolean isJunction() {
		return isJunction;
	}

	public static void setJunction(boolean isJunction) {
		XmlParser.isJunction = isJunction;
	}
}

