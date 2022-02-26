package de.javamaps;


import java.awt.EventQueue;
import java.io.IOException;
import java.util.Map;

import javax.xml.stream.XMLStreamException;

import de.javamaps.items.Vertex;
import de.javamaps.parser.XmlReader;
import de.javamaps.exceptions.ApplicationInitializationException;
import de.javamaps.exceptions.MapInitializationException;
import de.javamaps.gui.Gui;
import de.javamaps.route.DistanceCalc;
import de.javamaps.parser.GraphOptimizer;

public class Main {
	static GlobalApplicationStorage globalStorage = GlobalApplicationStorage.getGlobalStorage();
	static Gui gui = globalStorage.getGui();

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> gui.setVisible(true));

		try {
			initializeMap();
			gui.initializeMap();
			gui.initializeFilters();
		}
		catch (ApplicationInitializationException e){
			//TODO Implement real Logging
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Initialize the data of the street map shown in the application form its xml file
	 * @throws MapInitializationException if the data couldn't be loaded
	 */
	private static void initializeMap() throws MapInitializationException {
		try {
			Map<Long, Vertex> mapData = XmlReader.parseMapFromXmlFile();
			GraphOptimizer.connectMotorwayRampsWithSameNames(mapData);
			DistanceCalc.calculateAllDistancesOfGraph(mapData);
			GraphOptimizer.uniteVertexes(mapData);
			globalStorage.setMapData(mapData);
		} catch (XMLStreamException | IOException e ) {
			throw new MapInitializationException(e);
		}
	}
}
