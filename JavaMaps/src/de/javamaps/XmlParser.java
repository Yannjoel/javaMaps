package de.javamaps;


import java.io.*;
import javax.xml.stream.*;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.utils.IOUtils;

import java.util.*;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;

import de.javamaps.items.*;

public class XmlParser {

	private static String name;
	private static boolean isJunction;
	private static boolean isMotorway;
	private static List<Long> ndList = new LinkedList<Long>();
	//private static List<Long> nodes = new LinkedList<Long>();
	private static Map<Long, Node> nodes = new TreeMap<Long, Node>();
	
	
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

		long id;
		double lon;
		double lat;
		
		Node temp;
		
//---------------WAYS-------------------------------------------------
		
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

		fin.close();
		in.close();
		datastream.close();		
		parser.close();
		
		fin = new FileInputStream("data/saarland.osm.bz2");
		in = new BufferedInputStream(fin);
		try {
			datastream = new BZip2CompressorInputStream(in);
		} catch (IOException e) {
			throw e;
		}
		parser = factory.createXMLStreamReader(datastream);
		
//----------------NODES------------------------------------------------------------------------------
		
		while(parser.hasNext()){
			name = null;
			if (parser.getEventType() == XMLStreamReader.START_ELEMENT){
				if (Objects.equals(parser.getLocalName(), "node")) {	// Element is node
					id = Long.parseLong(parser.getAttributeValue(0));
					
					if((temp = nodes.get(id)) != null){
						isJunction = false;
						lon = Double.parseDouble(parser.getAttributeValue(1));
						lat = Double.parseDouble(parser.getAttributeValue(2));
						getChildElements(parser);
						System.out.println(id +" "+ name +" "+ lon +" "+ lat +" "+ temp.id);
					}	
				}
			}
			parser.next();
		}
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
							isJunction = true;
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
}

