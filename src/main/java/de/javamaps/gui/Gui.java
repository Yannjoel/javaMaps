package de.javamaps.gui;

import java.awt.Insets;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import de.javamaps.items.DropDownEntry;
import de.javamaps.items.MotorwayRamp;
import de.javamaps.items.Neighbor;
import de.javamaps.items.Vertex;
import de.javamaps.route.Dijkstra;
import de.javamaps.GlobalApplicationStorage;
import de.javamaps.parser.GraphOptimizer;

import javax.swing.JComboBox;
import java.awt.Color;

import javax.swing.JButton;

public class Gui {
    private JFrame frame;
    /**
     * Create the application.
     */
    public Gui() {
        initialize();
    }

    JComboBox<DropDownEntry> startLocationSelectBox;
    JComboBox<DropDownEntry> targetLocationSelectBox;
    JTextArea routeTextArea;

    private MapArea allStreets;
    private MapArea routeMap;

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {

        //General frame
        frame = new JFrame();
        frame.setBounds(100, 100, 1250, 898);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.addComponentListener(new FrameEvent());

        //start location select box
        startLocationSelectBox = new JComboBox<>();
        startLocationSelectBox.setBounds(10, 11, 240, 20);
        frame.getContentPane().add(startLocationSelectBox);

        //target location select box
        targetLocationSelectBox = new JComboBox<>();
        targetLocationSelectBox.setBounds(10, 42, 240, 20);
        frame.getContentPane().add(targetLocationSelectBox);

        //start search button
        JButton startButton = new JButton("Find Route");
        startButton.addActionListener(arg0 -> findRoute());
        startButton.setBounds(10, 90, 240, 33);
        frame.getContentPane().add(startButton);

        allStreets = new MapArea();
        routeMap = new MapArea();

        addMapArea(allStreets);
        addMapArea(routeMap);

        routeTextArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(routeTextArea);
        scrollPane.setBounds(10, 134, 240, 716);
        frame.getContentPane().add(scrollPane);
        frame.validate();
    }

    private void addMapArea(MapArea map) {
        map.setBounds(260, 11, 974, 839);
        frame.getContentPane().add(map);
    }

    private void findRoute() {
        if(startLocationSelectBox.getSelectedItem() == null || targetLocationSelectBox.getSelectedItem() == null){
            return;
        }
        routeMap.removeOldRoute();
        routeMap = new MapArea();
        addMapArea(routeMap);
        long startVertexID = ((DropDownEntry) startLocationSelectBox.getSelectedItem()).getId();
        long endVertexID = ((DropDownEntry) targetLocationSelectBox.getSelectedItem()).getId();
        
        StringBuilder dijkstraResult = Dijkstra.calculate(startVertexID, endVertexID);
        routeTextArea.setMargin(new Insets(3, 3, 3, 3));
        routeTextArea.setEditable(false);
        routeTextArea.setText(dijkstraResult.toString());

        Stack<Vertex> routeStack = Dijkstra.getFullWayAsStack(endVertexID);
        drawRoute(routeStack);
    }

    public void initializeMap() {
        for (Vertex vertex: GlobalApplicationStorage.getGlobalStorage().getMapData().values()) {
            for(Neighbor neighbor : vertex.getNeighbors()){
                addLine(vertex, GlobalApplicationStorage.getGlobalStorage().getMapData().get(neighbor.getName()), allStreets);
            }
        }
        allStreets.drawLines();
    }

    public void initializeFilters() {
        Map<String, List<Long>> allMotorwayRamps = MotorwayRamp.getMotorwayRamps(GlobalApplicationStorage.getGlobalStorage().getMapData());
        addLocations(GraphOptimizer.filterOutDuplicateNames(allMotorwayRamps));
    }

    private class FrameEvent implements ComponentListener {

        public void componentResized(ComponentEvent arg0) {

            allStreets.setSize(frame.getWidth() - 220, frame.getHeight() - 61);
            allStreets.resized();

            routeMap.setSize(frame.getWidth() - 220, frame.getHeight() - 61);
            routeMap.resized();

        }

        @Override
        public void componentHidden(ComponentEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void componentMoved(ComponentEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public void componentShown(ComponentEvent e) {
            // TODO Auto-generated method stub

        }

    }

    public void addLine(Vertex x1, Vertex x2, MapArea map) {
        map.addLine(x1.getLongitude(), x1.getLatitude(), x2.getLongitude(), x2.getLatitude());
    }

    public void addLine(Vertex x1, Vertex x2, Color color, MapArea map) {
        map.addLine(x1.getLongitude(), x1.getLatitude(), x2.getLongitude(), x2.getLatitude(), color);
    }

    public void drawRoute(Stack<Vertex> routeStack) {
              Vertex lastPoint = null;
        for (Vertex point : routeStack) {
            if (lastPoint != null) {
                addLine(lastPoint, point, Color.red, routeMap);
            }
            lastPoint = point;
        }
        routeMap.drawLines();
    }

    public void setVisible(boolean b) {
        frame.setVisible(b);
    }

    public void addLocations(Map<String, List<Long>> positions) {
        positions.keySet().stream().sorted().forEach(locationName -> addSelectBox(locationName, positions.get(locationName)));
    }

    private void addSelectBox(String locationName, List<Long> locationIds) {
        for (long id : locationIds) {
            startLocationSelectBox.addItem(new DropDownEntry(id, locationName));
            targetLocationSelectBox.addItem(new DropDownEntry(id, locationName));
        }
    }
}
