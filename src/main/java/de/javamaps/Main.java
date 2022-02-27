package de.javamaps;


import de.javamaps.exceptions.ApplicationInitializationException;
import de.javamaps.exceptions.DataParsingException;
import de.javamaps.exceptions.MapInitializationException;
import de.javamaps.gui.Gui;
import de.javamaps.items.Vertex;
import de.javamaps.parser.json.JsonParser;
import de.javamaps.parser.GraphOptimizer;
import de.javamaps.route.DistanceCalc;

import java.awt.*;
import java.util.Map;

public class Main {
    static GlobalApplicationStorage globalStorage = GlobalApplicationStorage.getGlobalStorage();
    static Gui gui = globalStorage.getGui();

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> gui.setVisible(true));

        try {
            initializeMap();
            gui.initializeMap();
            gui.initializeFilters();
        } catch (ApplicationInitializationException e) {
            //TODO Implement real Logging
            System.out.println(e.getMessage());
        }
    }

    /**
     * Initialize the data of the street map shown in the application form its xml file
     *
     * @throws MapInitializationException if the data couldn't be loaded
     */
    private static void initializeMap() throws MapInitializationException {
        try {
            JsonParser jsonParser = new JsonParser();
            Map<String, Vertex> mapData = jsonParser.getAllMapVertexes();
            GraphOptimizer.connectMotorwayRampsWithSameNames(mapData);
            DistanceCalc.calculateAllDistancesOfGraph(mapData);
            globalStorage.setMapData(mapData);
        } catch (DataParsingException e) {
            throw new MapInitializationException(e);
        }
    }
}
