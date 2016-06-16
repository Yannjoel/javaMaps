import java.io.*;
import javax.xml.stream.*;
import java.util.*;

public class StAX_Test {
	public static void main(String [ ] args) throws XMLStreamException, FileNotFoundException, UnsupportedEncodingException{
		File file = new File("data/autobahn_bw.kml");
		InputStreamReader stream = new InputStreamReader(new FileInputStream(file),"ISO-8859-1");
		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLStreamReader parser = factory.createXMLStreamReader(stream);
		
		List<List<String>> elements = new ArrayList<List<String>>();
		String text;
		
		
		while(parser.hasNext()){
			switch(parser.getEventType()){
				case XMLStreamReader.START_ELEMENT:
					if(parser.getLocalName() != "Placemark"){
						ArrayList<String> singleElement = new ArrayList<String>();
						singleElement.add(parser.getLocalName());
						singleElement.add(parser.getAttributeLocalName(0));						
						singleElement.add(parser.getAttributeValue(0));
						/*try {
							text = parser.getElementText();
				          
				        } catch (XMLStreamException e) {
				        	text = null;
				        }
						singleElement.add(text);*/
						elements.add(singleElement);
					}
				break;
				
				case XMLStreamReader.END_ELEMENT:
					if(parser.getLocalName() == "Placemark"){
						System.out.println("");
						for(int i = 0; i < elements.size(); i++){
							System.out.println(elements.get(i).toString());
						}
						elements.clear();
					}
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
