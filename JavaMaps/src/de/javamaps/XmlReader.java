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
	private static String name;
	private static int nope;
	
	public static void main(String [ ] args) throws XMLStreamException, IOException{
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
		
		List<Vertex> vertexList = new ArrayList<Vertex>();
		
		
		while(parser.hasNext()){
			if(parser.getEventType() == XMLStreamReader.START_ELEMENT){
				if(parser.getLocalName() == "node"){	//Nodes
					//System.out.println(parser.getLocalName()+" "+parser.getAttributeValue(0)+" "+parser.getAttributeValue(1)+" "+parser.getAttributeValue(2));
					id = Long.parseLong(parser.getAttributeValue(0));
					lon = Float.parseFloat(parser.getAttributeValue(1));
					lat = Float.parseFloat(parser.getAttributeValue(2));
					getChildElements(parser);
					if(nope == 0){
						vertexList.add(new Vertex(name,id,lon,lat));
					}
				}
				
			}
			parser.next();
		}
		for(int i = 0; i < vertexList.size();i++){
			System.out.println(vertexList.get(i).getName());
		}
		
	}
	
	private static void getChildElements(XMLStreamReader parser) throws XMLStreamException{ //Unterelemente auslesen
		int stop = 0;
		if(parser.getLocalName() == "node"){
			nope  = 1;
			while(parser.hasNext() && stop == 0){
				switch(parser.getEventType()){
					case XMLStreamReader.START_ELEMENT:
						switch(parser.getLocalName()){
						case "tag":
							//System.out.println("	tag: "+parser.getLocalName()+" k: "+parser.getAttributeValue(0)+" v: "+parser.getAttributeValue(1));
							switch(parser.getAttributeValue(0)){
							case "name":
								name = parser.getAttributeValue(1);
							break;
							case "highway":
								if(parser.getAttributeValue(1)== "motorway_junction"){
									nope = 0;
								}
							break;
							}
						break;
						case "nd":

						break;
						}
					break;
					
					case XMLStreamReader.END_ELEMENT:
						if(parser.getLocalName() == "node" || parser.getLocalName() == "way"){	//return bei ende von Übergeordnetem Element
							return;
						}
					break;
				}	//switch ende
				parser.next();
			}
		}
		else if(parser.getLocalName() == "way"){
			
		}
	}
}
