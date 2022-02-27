package de.javamaps;

import de.javamaps.gui.Gui;
import de.javamaps.items.Vertex;

import java.util.Map;

public class GlobalApplicationStorage {

    //static instance for singleton pattern
    protected static GlobalApplicationStorage globalStorage;

    //global attributes
    protected final Gui gui;
    protected Map<String, Vertex> mapData;

    /**
     * Singleton initialization factory
     */
    public static synchronized GlobalApplicationStorage getGlobalStorage(){
        if (globalStorage == null){
            globalStorage = new GlobalApplicationStorage();
        }
        return globalStorage;
    }

    private GlobalApplicationStorage(){
        gui = new Gui();
    }

    public Map<String, Vertex> getMapData() {
        return mapData;
    }

    public Gui getGui() {
        return gui;
    }

    public void setMapData(Map<String, Vertex> mapData) {
        this.mapData = mapData;
    }
}
