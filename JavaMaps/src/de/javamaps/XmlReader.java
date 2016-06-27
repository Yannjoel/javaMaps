package de.javamaps;

import java.io.*;
import javax.xml.stream.*;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.utils.IOUtils;

import java.util.*;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;

public class XmlReader {
	public static void main(String [ ] args) throws XMLStreamException, IOException{
		FileInputStream fin = new FileInputStream("data/saarland.osm.bz2");
		BufferedInputStream in = new BufferedInputStream(fin);
		BZip2CompressorInputStream datastream;
		try {
			datastream = new BZip2CompressorInputStream(in);
		} catch (IOException e) {
			throw e;
		}
		System.out.println("Start");
		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLStreamReader parser = factory.createXMLStreamReader(datastream);
		
		List<List<String>> elements = new ArrayList<List<String>>();
		String text;
		
		while(parser.hasNext()){
			if(parser.getEventType() == XMLStreamReader.START_ELEMENT){
				if(parser.getLocalName() == "node"){
					//System.out.println(parser.getLocalName()+" "+parser.getAttributeValue(0)+" "+parser.getAttributeValue(1)+" "+parser.getAttributeValue(2));
					parser.next();
					while(parser.getEventType() != XMLStreamReader.END_ELEMENT){
						if(parser.getEventType() == XMLStreamReader.START_ELEMENT){
							if(parser.getLocalName() == "tag"){
								System.out.println(parser.getLocalName()+" "+parser.getAttributeValue(0)+" "+parser.getAttributeValue(1));
							}
						}
						parser.next();
					}
				}
				/*else if(parser.getElementText() == "way"){
					
				}
				else if(parser.getElementText() == "nd"){
					
				}
				else if(parser.getElementText() == "tag"){
	
				}*/
			}
			parser.next();
			
		}
		
	}
}
