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

	public static Map<Long, Vertex>  vertexMap = new TreeMap<Long, Vertex>();
	
	public static void XmlReader(Gui gui) throws XMLStreamException, IOException {
		FileInputStream fin = new FileInputStream("data/saarland.xml");
		BufferedInputStream in = new BufferedInputStream(fin);

		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLStreamReader parser = factory.createXMLStreamReader(in);
		
		
		while(parser.hasNext()){
			if(parser.getEventType() == XMLStreamReader.START_ELEMENT){
				if(parser.getLocalName() == "node"){
					vertexMap.put(Long.parseLong(parser.getAttributeValue(0)),
							new Vertex(parser.getAttributeValue(7),
									Long.parseLong(parser.getAttributeValue(0)),
									Double.parseDouble(parser.getAttributeValue(1)),
									Double.parseDouble(parser.getAttributeValue(2))));
					
				}
				else if(parser.getLocalName() == "bounds"){
					
				}
			}
			parser.next();
		}
		
		Set<Long> set = vertexMap.keySet();
		Iterator<Long> iterator = set.iterator();
		
		while(iterator.hasNext()){
			Vertex tmp = vertexMap.get(iterator.next());
			System.out.println(tmp.getId() +" "+ tmp.getLon() +" "+ tmp.getLat());
		}
		gui.drawLines();
		
		
		System.out.println(vertexMap.size());
	}
}
