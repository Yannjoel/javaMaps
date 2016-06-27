import java.io.*;
import javax.xml.stream.*;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.utils.IOUtils;

import java.util.*;

public class StAX_Test {
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
			switch(parser.getEventType()){
				case XMLStreamReader.START_ELEMENT:
					System.out.println(parser.getLocalName());
				break;
				
				case XMLStreamReader.END_ELEMENT:

				break;
				
				case XMLStreamReader.END_DOCUMENT:
					
				break;
				
				default:
					
				break;
			}
			parser.next();
		}
		
	}
}
