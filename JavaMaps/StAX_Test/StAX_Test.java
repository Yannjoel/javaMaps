import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;

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
