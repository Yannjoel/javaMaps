package de.javamaps.items;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Vertex implements Serializable {

    //default serialVersion id
    private static final long serialVersionUID = 1L;

    private final String id;
    private final BigDecimal latitude;
    private final BigDecimal longitude;
    private final String name;
    private final List<Neighbor> neighbors = new ArrayList<>();
    private String previous;
    private BigDecimal totalDistance = null;
    private boolean visited;

    public Vertex(String name, String id, BigDecimal latitude, BigDecimal longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.id = id;
        this.visited = false;
    }

    public void setAsStart() {
        this.setTotalDistance(BigDecimal.ZERO);
        this.setPrevious(null);
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public BigDecimal getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(BigDecimal totalDistance) {
        this.totalDistance = totalDistance;
    }

    public boolean isUnVisited() {
        return !visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Neighbor> getNeighbors() {
        return neighbors;
    }

    public void addNeighbor(Neighbor newNeighbor) {
        this.neighbors.add(newNeighbor);
    }

    public void addNeighbor(String id) {
        this.neighbors.add(new Neighbor(id));
    }

    public boolean hasNeighbors() {
        return !this.neighbors.isEmpty();
    }


}
