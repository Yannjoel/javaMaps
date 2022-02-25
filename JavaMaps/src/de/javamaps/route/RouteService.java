package de.javamaps.route;

import de.javamaps.Dijkstra;
import de.javamaps.XmlReader;
import de.javamaps.items.Vertex;

import java.util.Stack;

public class RouteService {

    public String calculateRoute(long startVertexID, long endVertexID){
        long startTime = System.currentTimeMillis();
        StringBuffer output = Dijkstra.calculate(startVertexID, endVertexID, XmlReader.graphFromXmlFile);
        Stack<Vertex> routeList = (Dijkstra.getfullWayAsStack(XmlReader.graphFromXmlFile, endVertexID));
//        window.drawRoute(routeList);

        long endTime = System.currentTimeMillis();
        System.out.println("total route calculation time = " + (endTime-startTime));

        return output.toString();
    }

}
