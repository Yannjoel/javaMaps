import java.io.*;
import javax.xml.stream.*;

public class StAX_Test {
	public static void main(String [ ] args) throws XMLStreamException, FileNotFoundException, UnsupportedEncodingException{
		System.out.println("Working Directory = " + System.getProperty("user.dir"));
		File file = new File("data/autobahn_bw.kml");
		InputStreamReader stream = new InputStreamReader(new FileInputStream(file),"ISO-8859-1");
		XMLInputFactory factory = XMLInputFactory.newInstance();
		XMLStreamReader parser = factory.createXMLStreamReader(stream);
		
		String spacer = "";
		
		while(parser.hasNext()){
			if(parser.getEventType() == XMLStreamConstants.START_ELEMENT){
				System.out.println(spacer+"<"+parser.getLocalName()+">");
			}
			else if(parser.getEventType() == XMLStreamConstants.END_ELEMENT){
				System.out.println(spacer+"</"+parser.getLocalName()+">");
			}
			parser.next();
		}
		System.out.println("Ende");
		
	}
}
