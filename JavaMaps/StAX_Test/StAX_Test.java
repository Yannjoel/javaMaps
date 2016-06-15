import java.io.*;
import javax.xml.stream.*;
import java.util.*;

public class StAX_Test {
	public static void main(String [ ] args) throws XMLStreamException, FileNotFoundException, UnsupportedEncodingException{
		System.out.println("Working Directory = " + System.getProperty("user.dir"));
		File file = new File("data/autobahn_bw.kml");
		InputStreamReader stream = new InputStreamReader(new FileInputStream(file),"ISO-8859-1");
		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLStreamReader parser = factory.createXMLStreamReader(stream);
		
		List elements = new ArrayList();
		
		
		while(parser.hasNext()){
			if(parser.getEventType() == XMLStreamConstants.START_ELEMENT){
				System.out.println(parser.getLocalName());
				if(parser.getLocalName() == "Placemark"){
					while(parser.getLocalName() != "Placemark"){
						
					}
				}
			}

			parser.next();
		}
		System.out.println("Ende");
		
	}
}
